package interfaces;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CustomInnerTable extends JTable{
	boolean editable;
	public JTable table;
    public DefaultTableModel tableModel;
    
    
    public CustomInnerTable(int rows, String[] dados, boolean editable){
    	this.editable = editable;
    	TableColumn coluna = new TableColumn(0);
    	coluna.setHeaderValue(40);
    	coluna.setCellEditor(new CustInnerCellEditor());
    	
    	tableModel = new DefaultTableModel();
    	DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
    	
    	columnModel.addColumn(coluna);
    	
    	table = new JTable(tableModel, columnModel);
    	
    	table.setPreferredScrollableViewportSize(new Dimension(700, 200));
        table.setDefaultRenderer(Object.class, new CustInnerRenderer());
        table.setRowHeight(20);
        
        tableModel.setColumnIdentifiers(new Object[] {coluna});
        tableModel.setNumRows(rows);
        
        for(int i=0;i<rows;i++){
        	tableModel.setValueAt(dados[i], i, 0);
        }
        
        table.setGridColor(Color.blue);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
    }	
    
}
