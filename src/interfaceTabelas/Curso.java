package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import tableModel.CursoTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Curso extends InterfacesTabela implements ActionListener{
	
	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Curso() {
		super(new CursoTableModel(), "Inserir Novo Curso");
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Curso");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Curso curso = new timetable.Curso();
				((CursoTableModel)tableModel).addCurso(curso);
			}
		});
	}
	
	public void updateTable() {
		((CursoTableModel)tableModel).updateDataRows();
		((CursoTableModel)tableModel).fireTableDataChanged();
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((CursoTableModel)tableModel).removeCurso(table.getSelectedRow());  
        }
    }
}
