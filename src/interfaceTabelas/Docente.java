package interfaceTabelas;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import hibernate.DocenteDAO;
import interfaces.LayoutConstraints;
import tableModel.DocenteTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Docente extends InterfacesTabela implements ActionListener{

	private JButton botaoExibirHorarios;
	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Docente(){		
		super(new DocenteTableModel(), "Inserir Docente");
		
		botaoExibirHorarios = new JButton("Horarios dos Docentes");
		painelBotao.add(botaoExibirHorarios);
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Docente");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
		
		// seta a posição do botão de horários dos professores
 		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
 		constraints.insets = new Insets(0, 0, 0, 170);
 		constraints.fill = GridBagConstraints.RELATIVE;
 		constraints.anchor = GridBagConstraints.EAST;
 		btnGridBag.setConstraints(botaoExibirHorarios, constraints);
 		
 		// gera os acontecimentos ao se clicar no botão inserir
 		botaoPadrao.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				timetable.Docente docente = new timetable.Docente();
 				((DocenteTableModel)tableModel).addDocente(docente);
 			}
 		});
		
		botaoExibirHorarios.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				DocenteDAO docenteDAO = new DocenteDAO();
				List<timetable.Docente> lista = null;
				try {
					lista = docenteDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String[] docente = new String[lista.size()];
				
				for(int i=0;i<lista.size();i++){
					docente[i] = lista.get(i).getNome();
				}
				
				JFrame frameAno = new JFrame("Exibir Grade");
				Object resposta = JOptionPane.showInputDialog(frameAno, 
			        "Selecione o Docente",
			        "Exibir",
			        JOptionPane.QUESTION_MESSAGE,
			        null, 
			        docente, 
			        docente[0]);
				
			    if(resposta == null)
					return;
			    
			    JFrame frame = new JFrame("Horarios do docente: " + resposta.toString());
			    HorariosDocentes horariosDocentes = null;
			    for(timetable.Docente d: lista){
			    	if(d.getNome().equals(resposta.toString())){
			    		horariosDocentes = new HorariosDocentes(d);
			    	}
			    }
			    
			    if(horariosDocentes != null){
			    	frame.setLayout(new BorderLayout());
			    	frame.add(horariosDocentes.getPainel(), BorderLayout.CENTER);
			    
			    	frame.pack();
			    	frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH ); //maximiza a janela
			    	frame.setVisible(true);
			    }
				
			}
		});
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((DocenteTableModel)tableModel).removeDocente(table.getSelectedRow());  
        }
    }
	
	public void updateTable() {
		((DocenteTableModel)tableModel).updateDataRows();
		((DocenteTableModel)tableModel).fireTableDataChanged();		
	}
}