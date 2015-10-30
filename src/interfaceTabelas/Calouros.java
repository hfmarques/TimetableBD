package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tableModel.CalourosTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Calouros extends InterfacesTabela {

	/**
	 * 
	 */
	public Calouros(){		
		super(new CalourosTableModel(), "Inserir Calouro");		
		// gera os acontecimentos ao se clicar no botão inserir
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Calouros calouro = new timetable.Calouros();
				((CalourosTableModel)tableModel).addCalouro(calouro);
			}
		});
	}

	public void updateTable() {
		((CalourosTableModel)tableModel).updateDataRows();
		((CalourosTableModel)tableModel).fireTableDataChanged();
	}
}
