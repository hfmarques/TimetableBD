package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CorLinhaCellRenderer extends DefaultTableCellRenderer{
	
	ArrayList<ArrayList<Object>> posicoesCores = new ArrayList<ArrayList<Object>>();
	
	public CorLinhaCellRenderer(ArrayList<ArrayList<Object>> posicoesCores) {
		super();
		this.posicoesCores = posicoesCores;
	}



	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){  
  
       Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
       if(!posicoesCores.isEmpty()){  
        	for(Iterator<?> itPosCores = posicoesCores.iterator(); itPosCores.hasNext();){
        		ArrayList<Object> linha = (ArrayList<Object>) itPosCores.next();
                int rowToPaint = (int) linha.get(0);
                if(rowToPaint == row)
                	this.setBackground((Color) linha.get(1));
          	}
        }
        
        return component;
	}
	
}
