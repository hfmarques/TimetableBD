package tabelasInternas;

import interfaces.CellRenderer;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import timetable.GenericsVagas;

@SuppressWarnings("serial")
public class TotalVagasInternalTable extends JPanel{
	private JTable table;
	private AbstractTableModel tableModel;
	
	public TotalVagasInternalTable(List<? extends GenericsVagas> linhas, ArrayList<Color> coresLinhas){
		super(new GridLayout(1, 0));
		
		tableModel = new TotalVagasInternalTableModel(linhas);
		table  = new JTable(tableModel);
		
		if(!coresLinhas.isEmpty()){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}

	public JTable getTable() {
		return table;
	}

	public AbstractTableModel getTableModel() {
		return tableModel;
	}
}
