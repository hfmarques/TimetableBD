package interfaceTabelas;

import hibernate.TurmaDAO;
import interfaces.Home;
import interfaces.LayoutConstraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tableModel.TurmaTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Turma extends InterfacesTabela {

	private JButton botaoImportaTurmas;
	
	public Turma(){		
		super(new TurmaTableModel(), "Inserir Turma");
		
		botaoImportaTurmas = new JButton("Importar Turmas");
		
		painelBotao.add(botaoImportaTurmas);
		
		// seta a posição do botão salvar
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 170);
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoImportaTurmas, constraints);
		
		// gera os acontecimentos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Turma turma = new timetable.Turma();
				((TurmaTableModel)tableModel).addTurma(turma);
				
			}
		});
		
		botaoImportaTurmas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TurmaDAO turmaDAO = new TurmaDAO();
				List<timetable.Turma> lista = null;
				try {
					lista = turmaDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ArrayList<Integer> ano = new ArrayList<>();
				HashMap<Integer, ArrayList<Integer>> semestre = new HashMap<>();
				for(timetable.Turma t: lista){
					if(!ano.contains(t.getAno())){
						ano.add(t.getAno());
					}
					if(!semestre.containsKey(t.getAno()))
						semestre.put(t.getAno(), new ArrayList<Integer>());
					
					if(!semestre.get(t.getAno()).contains(t.getSemestre()))
						semestre.get(t.getAno()).add(t.getSemestre());
				}
				
				Integer[] Ano = new Integer[ano.size()];
				
				for(int i=0;i<ano.size();i++){
					Ano[i] = ano.get(i);
				}
				
				JFrame frameAno = new JFrame("Importar Turmas");
				Object resposta = JOptionPane.showInputDialog(frameAno, 
			        "Escolha o Ano",
			        "Importar Turma",
			        JOptionPane.QUESTION_MESSAGE, 
			        null, 
			        Ano, 
			        Ano[0]);
				
			    if(resposta == null)
					return;
				int respostaAno = Integer.parseInt(resposta.toString());
				
				Integer[] Semestre = new Integer[semestre.get(respostaAno).size()];
			    for(int i=0; i<Semestre.length;i++){
			    	Semestre[i] = semestre.get(respostaAno).get(i);
		    	}
			    
			    resposta = JOptionPane.showInputDialog(frameAno, 
				        "Escolha o semestre",
				        "Importar Turma",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        Semestre, 
				        Semestre[0]);
			    
			    int respostaSemestre = 0;
			    if(resposta == null)
			    	return;
			    
			    respostaSemestre = Integer.parseInt(resposta.toString());
			    
			    try {
					lista = turmaDAO.procuraTurmasPorAnoSemestre(respostaAno, respostaSemestre);
					for(timetable.Turma t: lista){
						turmaDAO.salvar(new timetable.Turma(t.getCodigo(), t.getTurno(), t.getMaxVagas(), t.getDisciplina(), t.getSala(), null, Home.getAno(), Home.getSemestre()));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
			    updateTable();
			}
		});
	}
	
	public void updateTable() {
		((TurmaTableModel)tableModel).updateDataRows();
		((TurmaTableModel)tableModel).fireTableDataChanged();		
	}
	
}
