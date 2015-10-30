package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tableModel.TurmaTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Turma extends InterfacesTabela {

	/**
	 * 
	 */
	public Turma(){		
		super(new TurmaTableModel(), "Inserir Turma");
		
		// gera os acontecimentos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Turma turma = new timetable.Turma();
				((TurmaTableModel)tableModel).addTurma(turma);
				
			}
		});	
	}
	
	public void updateTable() {
		((TurmaTableModel)tableModel).updateDataRows();
		((TurmaTableModel)tableModel).fireTableDataChanged();		
	}
	
}
