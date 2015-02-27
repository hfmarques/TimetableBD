package interfaces;

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

import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */
public class Curso extends InterfacesTabela {

	private JButton botaoInserir;
	private int cursosAdicionados;

	public Curso() {
		super(new CursoTableModel(), "Salvar");

		botaoInserir = new JButton("Inserir Curso");
		painelBotao.add(botaoInserir);
		// seta a posição do botão inserir
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 1130, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		btnGridBag.setConstraints(botaoInserir, constraints);

		// gera os acontecimentos ao se clicar no botão inserir
		botaoInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Object> linha = new ArrayList<Object>(); // arraylist
																	// para
				// armazenar a nova
				// linha da tabela
				// recebe por parametro o "model" da tabela para poder fazer as
				// auterações no mesmo
				CursoTableModel.MyTableModel model = (CursoTableModel.MyTableModel) ((CursoTableModel) tabela)
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

		// gera os acontecimentos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as
				// auterações no mesmo
				CursoTableModel.MyTableModel model = (CursoTableModel.MyTableModel) ((CursoTableModel) tabela)
						.getTable().getModel();

				for (int i = 0; i < cursosAdicionados; i++) { // para todos os
																// novos dados
																// inseridos é
																// inserido um a
																// um no banco
																// de dados
					timetable.Curso curso; // cria um novo curso
					// captura os dados inseridos na tabela e os insere no curso
					int numVagas1 = Integer.parseInt(((CursoTableModel) tabela)
							.getTable()
							.getValueAt((model.getData().size() - 1) - (i), 3)
							.toString());
					int numVagas2 = Integer.parseInt(((CursoTableModel) tabela)
							.getTable()
							.getValueAt((model.getData().size() - 1) - (i), 4)
							.toString());
					timetable.Calouros calouroSem1 = HibernateUtil
							.findCalouroID(numVagas1);
					timetable.Calouros calouroSem2 = HibernateUtil
							.findCalouroID(numVagas2);
					if (calouroSem1 != null && calouroSem2 != null) {
						curso = new timetable.Curso(((CursoTableModel) tabela)
								.getTable()
								.getValueAt((model.getData().size() - 1) - (i),
										0).toString(),
								((CursoTableModel) tabela)
										.getTable()
										.getValueAt(
												(model.getData().size() - 1)
														- (i), 1).toString(),
								((CursoTableModel) tabela)
										.getTable()
										.getValueAt(
												(model.getData().size() - 1)
														- (i), 2).toString(),
								calouroSem1, calouroSem2);
						HibernateUtil.saveOrUpdate(curso);
					} else { // arrumar o que acontece quando o erro é
								// localizado
						model.getData().remove(
								(model.getData().size()) - cursosAdicionados);
						model.fireTableStructureChanged();
						JOptionPane.showMessageDialog(null,
								"Calouro não encontrado", "Error",
								JOptionPane.ERROR_MESSAGE);
						cursosAdicionados--;
					}

					// insere este novo curso no banco de dados
				}
				cursosAdicionados = 0; // zera a quantidade de cursos
										// necessárias a serem adicionados
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
