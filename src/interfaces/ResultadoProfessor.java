package interfaces;

import interfaces.ResultadosProfessorTableModel.MyTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import org.hibernate.HibernateException;

import timetable.Docente;
import timetable.Turma;
import hibernate.HibernateUtil;
import hibernate.TurmaDAO;

/**
 *
 * @author Héber
 */
public class ResultadoProfessor extends InterfacesTabela{
	
	TurmaDAO turmaDAO;

	public ResultadoProfessor() {
		super(new ResultadosProfessorTableModel(), "Gerar Relatório");
		turmaDAO = new TurmaDAO();
		// inicializa as turmas dos professores da tabela
		inicializaTurmas();
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileWriter arq = null;
				try {
					arq = new FileWriter("relatório.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedWriter escrita = new BufferedWriter(arq);
				
				try {
					escrita.write("Semestre,Código da Disciplina,Disciplina,Turma,Horário,Créditos,Docente,Vagas");
					escrita.newLine();
					
					ArrayList<timetable.Turma> turma = null;
					try {
						turma = (ArrayList<timetable.Turma>) turmaDAO.procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					turma.sort(new Comparator<timetable.Turma>() {
						@Override
						public int compare(Turma o1, Turma o2) {
							if(o1.getDisciplina().getPerfil().equals(o2.getDisciplina().getPerfil())){
								if(o1.getDisciplina().getNome().equals(o2.getDisciplina().getNome())){
									return o1.getCodigo().compareTo(o2.getCodigo());
								}
								return o1.getDisciplina().getNome().compareTo(o2.getDisciplina().getNome());						
							}						
							return o1.getDisciplina().getPerfil().compareTo(o2.getDisciplina().getPerfil());
						}
					});
					
					for(Iterator<?> it = turma.iterator(); it.hasNext();){
						timetable.Turma tur = ((timetable.Turma)it.next());
						String docente = "";
						if(tur.getDocente().size() != 0){
							for(Iterator<?> it1 = tur.getDocente().iterator(); it1.hasNext();){
								timetable.Docente doc = (Docente) it1.next();
								docente = doc.getNomeCompleto();
								docente = docente + "; ";
							}
						}
						escrita.write("Semestre,"+ tur.getDisciplina().getCodigo()+ "," + 
						tur.getDisciplina().getNome() + "," + tur.getCodigo() + "," +
						"Horário," + tur.getDisciplina().getCreditos() + "," + docente + "," + "Vagas");
						escrita.newLine();
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					escrita.close();
					arq.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				

			}
		});
	}

	// metodo para se inicializar os dados de todas as turmas na tabela em
	// conjunto com seu professor
	public void inicializaTurmas(){
		List<Turma> turma = null;
		try {
			turma = turmaDAO.procuraTodos();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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
		try {
			turma = turmaDAO.encontraTurmasSemProf();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
