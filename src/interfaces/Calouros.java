package interfaces;

import interfaces.CalourosTableModel.MyTableModel;

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
 * @author H�ber
 */
public class Calouros extends InterfacesTabela {

	private JButton botaoInserir;
	private int calourosAdicionados;

	public Calouros() {		
		super(new CalourosTableModel(), "Salvar");

		botaoInserir = new JButton("Inserir Calouro");
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
				ArrayList<Object> linha = new ArrayList<Object>(); // arraylist
																	// para
				// armazenar a nova
				// linha da tabela
				// recebe por parametro o "model" da tabela para poder fazer as
				// autera��es no mesmo
				CalourosTableModel.MyTableModel model = (MyTableModel) ((CalourosTableModel) tabela)
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
				calourosAdicionados++; // armazena mais uma linha para ser salva
										// posteriormente
			}
		});

		// gera os acontecimentos ao se clicar no bot�o salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as autera��es no mesmo
				CalourosTableModel.MyTableModel model = (CalourosTableModel.MyTableModel) ((CalourosTableModel) tabela).getTable().getModel();
				
				try{
					for (int i = 0; i < calourosAdicionados; i++) { // para todos os novos dados inseridos confere se estes est�o corretos
						int compare = Integer.parseInt(((CalourosTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),1).toString());					
						if(compare < 0 || compare > 150){
							throw new Exception();
						}
					}
					
					for (int i = 0; i < calourosAdicionados; i++) { // para todos os novos dados inseridos � inserido um a um no banco de dados
						timetable.Calouros calouro; // cria um novo calouro captura os dados inseridos na tabela e os insere no calouro
						
						calouro = new timetable.Calouros(Integer.parseInt(((CalourosTableModel) tabela).getTable().getValueAt((model.getData().size() - 1) - (i),1).toString()));

						// insere este novo curso no banco de dados
						genericoDAO.salvaOuEdita(calouro);
					}
					
					calourosAdicionados = 0; // zera a quantidade de cursos necess�rias a serem adicionados
				}

				catch(Exception exc){
					JOptionPane.showMessageDialog(null,	"N�mero de vagas de calouros deve ser entre 0 e 150", "Error", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
	}
}
