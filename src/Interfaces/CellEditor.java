package Interfaces;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 * Editor de celulas da tabela
 */
public class CellEditor extends AbstractCellEditor implements TableCellEditor {

    JComponent component = new JTextField();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
            int rowIndex, int vColIndex) {

        ((JTextField) component).setText((String) value);

        return component;
    }

    @Override
    public Object getCellEditorValue() {
        return ((JTextField) component).getText();
    }
}
