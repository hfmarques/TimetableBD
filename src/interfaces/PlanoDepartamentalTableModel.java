package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

import hibernate.HibernateUtil;
import interfaces.ResultadosProfessorTableModel.MyTableModel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import timetable.Docente;
import timetable.Turma;

@SuppressWarnings("serial")
public class PlanoDepartamentalTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	public PlanoDepartamentalTableModel() {
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

		private String[] columnNames = {"Semestre", "Código Disciplina", "Disciplina", "Turma", "Horário", "Créditos", "Docente", "Vaga"};

		private ArrayList<ArrayList<Object>> data;

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			data = new ArrayList<ArrayList<Object>>(); // row
			
			
			ArrayList<timetable.Turma> turma = (ArrayList<timetable.Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);
			turma.sort(new Comparator<timetable.Turma>() {
				@Override
				public int compare(Turma o1, Turma o2) {
					if(o1.getDisciplina().getPerfil().equals(o2.getDisciplina().getPerfil())){
						if(o1.getDisciplina().getNome().equals(o2.getDisciplina().getNome())){
							return o1.getCodigo().compareTo(o2.getCodigo());
						}
						return o1.getDisciplina().getNome().compareTo(o2.getDisciplina().getNome());						
					}						
					return o1.getDisciplina().getPerfil().compareTo(o2.getDisciplina().getPerfil());
				}
			});
			for(Iterator<?> it = turma.iterator(); it.hasNext();){
				ArrayList<Object>line = new ArrayList<Object>();
				timetable.Turma tur = ((timetable.Turma)it.next());
				line.add("Primeiro");
				line.add(tur.getDisciplina().getCodigo());
				line.add(tur.getDisciplina().getNome());
				line.add(tur.getCodigo());
				line.add("Horário");
				line.add(tur.getDisciplina().getCreditos());
				line.add("Clique para escolher o Docente");
				line.add("Vaga");
				data.add(line);
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
