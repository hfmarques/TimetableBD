package interfaces;

import java.awt.Color;
import java.awt.Component;
 
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

//renderiza uma tabela dentro de outra tabela, é usado em conjunto com CustInnerTable
public class CustInnerRenderer extends JTextField implements TableCellRenderer {
 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JTextField field = new JTextField();
         
        if (value instanceof Cell)
            field.setText(value.toString());
 
        if (value instanceof String)
            field.setText(value.toString());
        
        field.setBackground(Color.WHITE);
//        field.setOpaque(true);
 
        if (hasFocus) {
            if (value instanceof String) {
                field.setBackground(new Color(216, 236, 242));
            }
        }
         
        if (value instanceof CustomInnerTable) {
        	CustomInnerTable s = (CustomInnerTable)value;
            return s.table;
        }
         
        return field;
    }
}
