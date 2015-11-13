package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tableModel.DocenteTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Docente extends InterfacesTabela {

	/**
	 * 
	 */
	public Docente(){		
		super(new DocenteTableModel(), "Inserir Docente");
		
		// gera os acontecimentos ao se clicar no botão inserir
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Docente docente = new timetable.Docente();
				((DocenteTableModel)tableModel).addDocente(docente);
			}
		});
	}
	
	public void updateTable() {
		((DocenteTableModel)tableModel).updateDataRows();
		((DocenteTableModel)tableModel).fireTableDataChanged();		
	}
}