package interfaces;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class InnerTableModel extends InterfaceTableModel{
	
	private Object[] passed;

	public InnerTableModel(Object[] passed, boolean DEBUG, int column) {
		super(DEBUG, column);
		this.passed = passed;
	}
	
	public int getColumnCount() {
		return 1;
	}

	public int getRowCount() {
		return passed.length;
	}

	public Object getValueAt(int rowIndex,	int columnIndex) {
		return passed[rowIndex];
	}

	public boolean isCellEditable(int row, int col) {
		if (col < 0) {
			return false;
		} else {
			return true;
		}
	}	
}