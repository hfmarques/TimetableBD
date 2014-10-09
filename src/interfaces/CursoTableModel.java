package interfaces;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import hibernate.HibernateUtil;

/**
 *
 * @author H�ber
 */
@SuppressWarnings("serial")
public class CursoTableModel extends JPanel /* extends AbstractTableModel */{
	private final boolean DEBUG = false;
	private JTable table;

	public CursoTableModel() {
		super(new GridLayout(1, 0));

		table = new JTable(new MyTableModel());
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

		private String[] columnNames = { "Nome", "C�digo", "Turno",
				"Calouros Primeiro Semestre", "Calouros Segundo Semestre" };

		private ArrayList<ArrayList<Object>> data;

		public MyTableModel() {
			data = new ArrayList<ArrayList<Object>>(); // row
			// col

			List<?> lista = HibernateUtil.findAll(timetable.Curso.class);

			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(((timetable.Curso) lista.get(i)).getNome());
				row.add(((timetable.Curso) lista.get(i)).getCodigo());
				row.add(((timetable.Curso) lista.get(i)).getTurno());
				row.add(Integer.toString(((timetable.Calouros) HibernateUtil
						.find(timetable.Calouros.class,
								((timetable.Curso) lista.get(i)).getCalouros()
										.get(0).getIdCalouro())).getNumVagas()));
				row.add(Integer.toString(((timetable.Calouros) HibernateUtil
						.find(timetable.Calouros.class,
								((timetable.Curso) lista.get(i)).getCalouros()
										.get(1).getIdCalouro())).getNumVagas()));
				data.add(row);
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
}