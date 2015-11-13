package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tableModel.CursoTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Curso extends InterfacesTabela {
	
	/**
	 *
	 */
	public Curso() {
		super(new CursoTableModel(), "Inserir Novo Curso");
		
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
}
