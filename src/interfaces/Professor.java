package interfaces;

import hibernate.HibernateUtil;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Professor extends InterfacesTabela{
	private JButton botaoInserir;
	private int professoresAdicionados;

	public Professor() {
		super(new DisciplinaTableModel(), "Salvar");
		
		botaoInserir = new JButton("Inserir Docente");
		painelBotao.add(botaoInserir);
		
		// seta a posi��o do bot�o inserir
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 1120, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		btnGridBag.setConstraints(botaoInserir, constraints);

		// gera os acontecimentos ao se clicar no bot�o inserir
		botaoInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Object> linha = new ArrayList<Object>(); // arraylist para
															// armazenar a nova
															// linha da tabela
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				ProfessorTableModel.MyTableModel model = (ProfessorTableModel.MyTableModel) ((DisciplinaTableModel) tabela)
						.getTable().getModel();

				// adiciona ao arry list campos em branco para mais a frente
				// serem editados
				for (int i = 0; i < model.getData().get(0).size(); i++) {
					linha.add("");
				}

				// adiciona a linha ao modelo
				model.addRow(linha);
				for (int i = 0; i < model.getData().get(0).size(); i++) { // atualiza
																			// a
																			// nova
																			// linha
																			// para
																			// ser
																			// exibida
																			// na
																			// tabela
					if (model.getData().size() - 1 < 0) {
						model.fireTableCellUpdated(0, i);
					} else {
						model.fireTableCellUpdated(model.getData().size() - 1,
								i);
					}
				}
				professoresAdicionados++; // armazena mais uma linha para ser salva
										// posteriormente
			}
		});

		// gera os acontecimentos ao se clicar no bot�o salvar
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				ProfessorTableModel.MyTableModel model = (ProfessorTableModel.MyTableModel) ((DisciplinaTableModel) tabela)
						.getTable().getModel();

				for (int i = 0; i < professoresAdicionados; i++) { 
					// para todos os novos dados inseridos � inserido um a um no banco de dados
					timetable.Docente prof; // cria um novo curso
					String nome = ((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),1).toString();
					try {
						nome = nome.substring(0,nome.indexOf(" "));
					} catch (java.lang.StringIndexOutOfBoundsException e1) {
						JOptionPane.showMessageDialog(null,
								"O nome n�o est� completo", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					// captura os dados inseridos na tabela e os insere no curso
					prof = new timetable.Docente(((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),0).toString(), 
								nome, 
								((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),1).toString(), 
								Integer.parseInt(((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),2).toString()));
					
					// insere este novo curso no banco de dados
					HibernateUtil.saveOrUpdate(prof);
				}
				professoresAdicionados = 0; // zera a quantidade de docentes necess�rios a serem adicionados
			}
		});
	}
}
