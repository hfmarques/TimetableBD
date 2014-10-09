package interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class ResultProfessor extends AbstractTableModel {

	private String[] columnNames = { "Código da Diciplina",
			"Nome da Diciplina", "Número de Créditos" };

	private ArrayList<ArrayList<Object>> data;

	public ResultProfessor() {
		data = new ArrayList<ArrayList<Object>>(); // row
		// col

		List<?> lista = HibernateUtil.findAll(timetable.Curso.class);

		for (int i = 0; i < lista.size(); i++) {

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(((timetable.Curso) lista.get(i)).getNome());
			row.add(((timetable.Curso) lista.get(i)).getCodigo());
			row.add(((timetable.Curso) lista.get(i)).getTurno());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	@SuppressWarnings("unused")
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
