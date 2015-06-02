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

public class Disciplina extends InterfacesTabela{
	private JButton botaoInserir;
	private int disciplinasAdicionadas;

	public Disciplina() {
		super(new DisciplinaTableModel(), "Salvar");
		
		botaoInserir = new JButton("Inserir Disciplina");
		painelBotao.add(botaoInserir);
		
		// seta a posi��o do bot�o inserir
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 1105, 0, 0);
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
				// altera��es no mesmo
				DisciplinaTableModel.MyTableModel model = (DisciplinaTableModel.MyTableModel) ((DisciplinaTableModel) tabela)
						.getTable().getModel();

				// adiciona ao arry list campos em branco para mais a frente
				// serem editados
				for (int i = 0; i < model.getColumnCount(); i++) {
					linha.add("");
				}

				// adiciona a linha ao modelo
				model.addRow(linha);
				
				for (int i = 0; i < model.getColumnCount(); i++) { // atualiza a nova linha para ser exibida na tabela
					if (model.getData().size() - 1 < 0) {
						model.fireTableCellUpdated(0, i);
					} else {
						model.fireTableCellUpdated(model.getData().size() - 1,i);
					}
				}
				disciplinasAdicionadas++; // armazena mais uma linha para ser salva
										// posteriormente
			}
		});

		// gera os acontecimentos ao se clicar no bot�o salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				DisciplinaTableModel.MyTableModel model = (DisciplinaTableModel.MyTableModel) ((DisciplinaTableModel) tabela)
						.getTable().getModel();

				for (int i = 0; i < disciplinasAdicionadas; i++) { 
					// para todos os novos dados inseridos � inserido um a um no banco de dados
					timetable.Disciplina disc; // cria um novo curso
					// captura os dados inseridos na tabela e os insere no curso
					disc = new timetable.Disciplina(((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),0).toString(), 
								Integer.parseInt(((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),1).toString()), 
								((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),2).toString(), 
								((DisciplinaTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),3).toString());
					
					// insere este novo curso no banco de dados
					genericoDAO.salvaOuEdita(disc);
				}
				disciplinasAdicionadas = 0; // zera a quantidade de docentes necess�rios a serem adicionados
			}
		});
	}
}
