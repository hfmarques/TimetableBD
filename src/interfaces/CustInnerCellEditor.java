package interfaces;

import java.awt.Color;
import java.awt.Component;
 
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

//edita os valores de uma tabela dentro de outra tabela, trabalha em conjunto com CustInnerTable
public class CustInnerCellEditor extends DefaultCellEditor {
    JComponent component = new JTextField(); 
 
    public CustInnerCellEditor() {
        super(new JTextField());
    }
     
    // chamado quando uma celula é editada
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        if (isSelected) {
        }
 
        if (value instanceof Cell) {
            ((JTextField) component).setText((String) value.toString());
            component.setBackground(Color.WHITE);
            component.setOpaque(true);
            return component;
        } else if (value instanceof CustomInnerTable) {
        	CustomInnerTable s = (CustomInnerTable) value;
            return s.table;
        } else {
            ((JTextField)component).setText((String)value);
        }
        return component;
    }
 
    // chamado quando a edição é completa
    public Object getCellEditorValue() {
        return ((JTextField)component).getText(); 
    }
}