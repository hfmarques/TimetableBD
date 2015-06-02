package interfaces;

import hibernate.DisciplinaDAO;
import hibernate.GenericoDAO;
import hibernate.HibernateUtil;
import hibernate.SalaDAO;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import timetable.Disciplina;
import timetable.Sala;
import timetable.Turma;

public class Turmas extends InterfacesTabela{
	
	private JButton botaoInserir;
	private int turmasAdicionadas;
	DisciplinaDAO discDAO;
	SalaDAO salaDAO;

	public Turmas() {
		super(new TurmasTableModel(), "Salvar");
		discDAO = new DisciplinaDAO();
		salaDAO = new SalaDAO();
		
		botaoInserir = new JButton("Inserir Turma");
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
				ArrayList<Object> linha = new ArrayList<Object>(); // arraylist para armazenar a nova linha da tabela
				// recebe por parametro o "model" da tabela para poder fazer as alterações no mesmo
				TurmasTableModel.MyTableModel model = (TurmasTableModel.MyTableModel) ((TurmasTableModel) tabela).getTable().getModel();
				// adiciona ao arry list campos em branco para mais a frente serem editados
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
				
				turmasAdicionadas++; // armazena mais uma linha para ser salva posteriormente
			}
		});

		// gera os acontecimentos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recebe por parametro o "model" da tabela para poder fazer as auterações no mesmo
				TurmasTableModel.MyTableModel model = (TurmasTableModel.MyTableModel) ((TurmasTableModel) tabela).getTable().getModel();

				for (int i = 0; i < turmasAdicionadas; i++) {
					// para todos os novos dados inseridos é inserido um a um no banco de dados
					timetable.Turma novaTurma; // cria um novo curso
					// captura os dados inseridos na tabela e os insere no curso
					int numLinhas = model.getData().size();
					
					if(numLinhas != 0)
						numLinhas = numLinhas - 1;
					
					Disciplina disc = null;
					try {
						disc = discDAO.encontraDisciplinaPorCodigo(((TurmasTableModel) tabela).getTable().getValueAt(numLinhas, 4).toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Sala sala = null;
					try {
						sala = salaDAO.encontraSalaPorNumero(((TurmasTableModel) tabela).getTable().getValueAt(numLinhas, 5).toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(disc == null){
						JOptionPane.showMessageDialog(new JFrame(), "Código da disciplina não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					
					else if(sala == null){
						JOptionPane.showMessageDialog(new JFrame(), "Número da sala não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					
					else{
						novaTurma = new timetable.Turma(((TurmasTableModel) tabela).getTable().getValueAt(numLinhas - (i), 0).toString(),
								((TurmasTableModel) tabela).getTable().getValueAt(numLinhas - (i), 1).toString(),
								Integer.parseInt(((TurmasTableModel) tabela).getTable().getValueAt(numLinhas - (i), 2).toString()),
								disc,
								sala,
								null);
						// insere este novo curso no banco de dados
						genericoDAO.salvar(novaTurma);
					}
				}
				turmasAdicionadas = 0; // zera a quantidade de docentes necessários a serem adicionados
			}
		});
	
	}
	
}
