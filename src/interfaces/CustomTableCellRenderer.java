package interfaces;


import interfaces.PedidosCoordenadoresTableModel.MyTableModel;

import java.awt.Component;
import java.awt.event.MouseAdapter;

import javax.swing.table.TableCellRenderer;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;

public class CustomTableCellRenderer extends JPanel implements TableCellRenderer {
	private boolean DEBUG;
	private int column;
	public CustomTableCellRenderer(boolean DEBUG, int column) {
		this.DEBUG = DEBUG;
		this.column = column;
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
					System.out.println("Click");
				}
			});

			setPreferredSize(embedded.getPreferredSize());
			if (getPreferredSize().height != table
					.getRowHeight(row)) {
				table.setRowHeight(row, getPreferredSize().height);
			}
			
			embedded.setCellEditor(new CellEditor());

			return embedded;
		}
	}
}
