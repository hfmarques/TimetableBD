package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorMultTableCellRenderer extends JPanel implements TableCellRenderer {

	private boolean DEBUG;
	private int column;
	ArrayList<ArrayList<Object>> posicoesCores = new ArrayList<ArrayList<Object>>();
	
	public ColorMultTableCellRenderer(boolean DEBUG, int column, ArrayList<ArrayList<Object>> posicoesCores) {
		this.DEBUG = DEBUG;
		this.column = column;
		this.posicoesCores = posicoesCores;
	}
	
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		/*
		 * se o que está sendo mostrado não for um vetor é retornado o valor original
		 */
		if (!value.getClass().isArray()) {
			return table.getDefaultRenderer(value.getClass())
					.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
		} else {
			final Object[] passed = (Object[]) value;
			/*
			 * é criado uma tabela pra mostrar os campos com varios valores
			 */
			JTable embedded = new JTable(new InnerTableModel((Object[]) value, DEBUG, column));
			
			if (isSelected) {
				embedded.setBackground(table.getSelectionBackground());
				embedded.setForeground(table.getSelectionForeground());
			}
			if (hasFocus) {
				embedded.setRowSelectionInterval(0, 1);
			}
			embedded.addMouseListener(new MouseAdapter() {
				public void mouseClicked(
						java.awt.event.MouseEvent evt) {
					System.out.println("PEPE");
				}
			});

			setPreferredSize(embedded.getPreferredSize());
			if (getPreferredSize().height != table
					.getRowHeight(row)) {
				table.setRowHeight(row, getPreferredSize().height);
			}
			
			if(!posicoesCores.isEmpty()){  
	        	for(Iterator<?> itPosCores = posicoesCores.iterator(); itPosCores.hasNext();){
	        		ArrayList<Object> linha = (ArrayList<Object>) itPosCores.next();
	                int rowToPaint = (int) linha.get(0);
	                if(rowToPaint == row)
	                	this.setBackground((Color) linha.get(1));
	          	}
	        }
			
			embedded.setCellEditor(new CellEditor());

			return embedded;
		}
	}

}
