package interfaces;

import interfaces.PlanoDepartamentalTableModel.MyTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import timetable.Turma;

public class PedidosCoordenadoresTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	public PedidosCoordenadoresTableModel() {
		super(new GridLayout(1, 0));

		tableModel = new MyTableModel();
		table = new JTable(tableModel);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);

	}
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	class MyTableModel extends AbstractTableModel {

		private String[] columnNames = {"Codigo do Curso", "Nome do Curso", "Semestre", "Codigo da Disciplina", "Disciplina", "Turma", "Horário", "Turnos", "Observações", "Vagas Periotizados", "Vagas Desperiotizados"};

		private ArrayList<ArrayList<Object>> data;

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			data = new ArrayList<ArrayList<Object>>(); // row
			
			
			ArrayList<timetable.Turma> _turma = (ArrayList<timetable.Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);
			ArrayList<timetable.Curso> _curso = (ArrayList<timetable.Curso>) hibernate.HibernateUtil.findAll(timetable.Curso.class);
			for(Iterator<?> itCurso = _curso.iterator(); itCurso.hasNext();){
				timetable.Curso curso = ((timetable.Curso)itCurso.next());
				for(Iterator<?> itTurma = _turma.iterator(); itTurma.hasNext();){					
					timetable.Turma turma = ((timetable.Turma)itTurma.next());
					ArrayList<Object>line = new ArrayList<Object>();
					line.add(curso.getCodigo());
					line.add(curso.getNome());
					line.add("Semestre");
					line.add(turma.getDisciplina().getCodigo());
					line.add(turma.getDisciplina().getNome());
					line.add(turma.getCodigo());
					line.add("Horário");
					line.add(curso.getTurno());
					line.add("");
					line.add("");
					line.add("");
					data.add(line);
				}
			}			
		}


		public String[] getColumnNames() {
			return columnNames;
		}

		public void setColumnNames(String[] columnNames) {
			this.columnNames = columnNames;
		}

		public ArrayList<ArrayList<Object>> getData() {
			return data;
		}

		public void addRow(ArrayList<Object> row) {
			data.add(row);
		}

		public void setData(ArrayList<ArrayList<Object>> data) {
			this.data = data;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data.get(row).get(col);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value + " (an instance of "
						+ value.getClass() + ")");
			}

			data.get(row).set(col, value);
			fireTableCellUpdated(row, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + data.get(i).get(j));
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	
}
