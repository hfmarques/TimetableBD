package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tableModel.DisciplinaTableModel;

/**
*
* @author Héber
*/
@SuppressWarnings("serial")
public class Disciplina extends InterfacesTabela{
	
	/**
	 * 
	 */
	public Disciplina() {
		super(new DisciplinaTableModel(), "Inserir Disciplina");
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Disciplina disciplina = new timetable.Disciplina();
				((DisciplinaTableModel)tableModel).addDisciplina(disciplina);
			}
		});
	}

	@Override
	public void updateTable() {
		((DisciplinaTableModel)tableModel).updateDataRows();
		((DisciplinaTableModel)tableModel).fireTableDataChanged();		
	}
	
	
}
