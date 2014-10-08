package interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import hibernate.HibernateUtil;

/**
 *
 * @author H�ber
 */
public class Calouros {

	private JPanel painel;
	private CalourosTableModel tabela;
	private JScrollPane scroll;
	private JButton botaoInserir;
	private JButton botaoSalvar;
	private int cursosAdicionados;

	public Calouros() {
		JPanel painelBotao = new JPanel();

		botaoInserir = new JButton("Inserir Calouros");
		botaoSalvar = new JButton("Salvar");
		painel = new JPanel();
		scroll = new JScrollPane();
		tabela = new CalourosTableModel();

		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		painel.setLayout(gridBag);
		GridBagLayout btnGridBag = new GridBagLayout();
		painelBotao.setLayout(btnGridBag);

		scroll.getViewport().setBorder(null);
		scroll.getViewport().add(tabela);
		scroll.setSize(450, 450);

		painelBotao.add(botaoInserir);
		painelBotao.add(botaoSalvar);
		painel.add(scroll);
		painel.add(painelBotao);

		// seta a posi��o do bot�o salvar
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 40);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoSalvar, constraints);

		// seta a posi��o do bot�o inserir
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 1020, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		btnGridBag.setConstraints(botaoInserir, constraints);

		// seta a posi��o do painel onde se localiza as tabelas
		LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(painel, constraints);

		// seta o tamanho da tabela
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
		constraints.insets = new Insets(30, 20, 20, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(scroll, constraints);

		// seta a posi��o do painel onde se localiza os bot�es
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(2, 2, 20, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridBag.setConstraints(painelBotao, constraints);

		// gera os acontecimentos ao se clicar no bot�o inserir
		botaoInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Object> linha = new ArrayList(); // arraylist para
															// armazenar a nova
															// linha da tabela
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				CalourosTableModel.MyTableModel model = (CalourosTableModel.MyTableModel) tabela
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
				cursosAdicionados++; // armazena mais uma linha para ser salva
										// posteriormente
			}
		});

		// gera os acontecimentos ao se clicar no bot�o salvar
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				CalourosTableModel.MyTableModel model = (CalourosTableModel.MyTableModel) tabela
						.getTable().getModel();

				for (int i = 0; i < cursosAdicionados; i++) { // para todos os
																// novos dados
																// inseridos �
																// inserido um a
																// um no banco
																// de dados
					timetable.Calouros calouro; // cria um novo curso
					// captura os dados inseridos na tabela e os insere no curso
					calouro = new timetable.Calouros(Integer.parseInt(tabela
							.getTable()
							.getValueAt((model.getData().size() - 1) - (i), 1)
							.toString()));

					// insere este novo curso no banco de dados
					HibernateUtil.saveOrUpdate(calouro);
				}
				cursosAdicionados = 0; // zera a quantidade de cursos
										// necess�rias a serem adicionados
			}
		});
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}
}
