package interfaces;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public abstract class InterfaceTableModel extends AbstractTableModel{
	
	private boolean DEBUG;
	
	protected String[] columnNames;

	protected ArrayList<ArrayList<Object>> data;	

	public InterfaceTableModel(boolean dEBUG, int column) {
		super();
		DEBUG = dEBUG;
		this.columnNames = new String[column];
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
