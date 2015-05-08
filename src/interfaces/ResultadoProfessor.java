package interfaces;

import interfaces.ResultadosProfessorTableModel.MyTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import org.hibernate.HibernateException;

import timetable.Docente;
import hibernate.HibernateUtil;
import hibernate.TurmaDAO;

/**
 *
 * @author Héber
 */
public class ResultadoProfessor extends InterfacesTabela{

	public ResultadoProfessor() {
		super(new ResultadosProfessorTableModel(), "Atualizar Planilha");
		
		// inicializa as turmas dos professores da tabela
		try {
			inicializaTurmas();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((ResultadosProfessorTableModel) tabela).loadTableValues();
				try {
					inicializaTurmas();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				((ResultadosProfessorTableModel) tabela).loadDataTable();							

			}
		});
	}

	// metodo para se inicializar os dados de todas as turmas na tabela em
	// conjunto com seu professor
	public void inicializaTurmas() throws HibernateException, Exception {
		List<?> turma = TurmaDAO.encontraTurmas();


		for (int i = 0; i < ((ResultadosProfessorTableModel) tabela).getTable().getModel().getRowCount(); i++) { // procura em todas as linhas da tabela
			if (((ResultadosProfessorTableModel) tabela).getTable().getModel().getValueAt(i, 0).equals("-")) { // caso o checkbox esteja marcado insere os dados extras dos professores

				ArrayList<String> disc = new ArrayList<String>();
				ArrayList<String> cod = new ArrayList<String>();
				ArrayList<Integer> cred = new ArrayList<Integer>();

				for (int j = 0; j < turma.size(); j++) { // para todas as turmas
					for (int k = 0; k < ((timetable.Turma) turma.get(j)).getDocente().size(); k++) { 
						// para todos os professores se existe doscente busca se o professore referente a 
						// linha atual da tabela possui o mesmo codigo que o professor que está no loop, se sim adiciona sua turma
						if (!((timetable.Turma) turma.get(j)).getDocente().isEmpty() && 
								((ResultadosProfessorTableModel) tabela).getTable().getModel().getValueAt(i, 1).equals(((timetable.Turma) turma.get(j)).getDocente().get(k).getCodigo())) {
							disc.add(((timetable.Turma) turma.get(j)).getDisciplina().getNome());
							cod.add(((timetable.Turma) turma.get(j)).getDisciplina().getNome()
									+ " - "
									+ ((timetable.Turma) turma.get(j)).getCodigo());
							cred.add(((timetable.Turma) turma.get(j)).getDisciplina().getCreditos());
						}
					}

				}

				if (!disc.isEmpty() || !cod.isEmpty() || !cred.isEmpty()) { // se houver dados no array de disciplina, codigo e creditação
																			// para aquela turma
					// adiciona a tabela os dados encontrados
					String[] sDisc = new String[disc.size()];
					for (int j = 0; j < disc.size(); j++) {
						sDisc[j] = disc.get(j);
					}

					String[] sCod = new String[cod.size()];
					for (int j = 0; j < cod.size(); j++) {
						sCod[j] = cod.get(j);
					}

					String[] sCred = new String[cred.size()];
					for (int j = 0; j < cred.size(); j++) {
						sCred[j] = cred.get(j).toString();
					}

					((ResultadosProfessorTableModel) tabela).getTable().getModel().setValueAt(sDisc, i, 3);
					((ResultadosProfessorTableModel) tabela).getTable().getModel().setValueAt(sCod, i, 4);
					((ResultadosProfessorTableModel) tabela).getTable().getModel().setValueAt(sCred, i, 5);

					disc.clear();
					cod.clear();
					cred.clear();
				}
			}
		}
		
		turma.clear();
		turma = TurmaDAO.encontraTurmasSemProf();
		MyTableModel model = ((ResultadosProfessorTableModel) tabela).getTableModel();
		for (int j = 0; j < turma.size(); j++) { // para todas as turmas
			if (((timetable.Turma) turma.get(j)).getDocente().isEmpty()) {
				ArrayList<Object> row = new ArrayList<Object>();

				row.add("+");
				row.add("");
				row.add("");
				row.add(((timetable.Turma) turma.get(j)).getDisciplina()
						.getNome()); // disciplina
				row.add(((timetable.Turma) turma.get(j)).getDisciplina()
						.getNome()
						+ " - "
						+ ((timetable.Turma) turma.get(j)).getCodigo()); // codigo
				row.add(Integer.toString(((timetable.Turma) turma.get(j)).getDisciplina().getCreditos())); // credito da turma
				row.add("");
				model.getData().add(row);
			}
		}
	}
}
