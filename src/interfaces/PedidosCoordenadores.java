package interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import timetable.Docente;
import timetable.Turma;

public class PedidosCoordenadores extends InterfacesTabela{
	
	public PedidosCoordenadores() {
		super(new PedidosCoordenadoresTableModel(), "Salvar");
		
		ArrayList<ArrayList<Object>> cor = PedidosCoordenadoresTableModel.getCor();

		for(int i = 0; i<((PedidosCoordenadoresTableModel) tabela).getTable().getColumnCount(); i++){
			((PedidosCoordenadoresTableModel) this.tabela).getTable().getColumnModel().getColumn(i).setCellRenderer(new CorLinhaCellRenderer(cor));
		}
		
		//gera os eventos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				ArrayList<timetable.PedidosCoordenadores> pedCoordenadores = (ArrayList<timetable.PedidosCoordenadores>) hibernate.HibernateUtil.findAll(timetable.PedidosCoordenadores.class);
//				ArrayList<timetable.VagasOferecidas> vagOferecidas = (ArrayList<timetable.VagasOferecidas>) hibernate.HibernateUtil.findAll(timetable.VagasOferecidas.class);
//				
//				for(int i = 0;i<((PedidosCoordenadoresTableModel) tabela).getTable().getRowCount(); i++){ //varre todas as diciplinas
//					int colunaInicial = 4; //primeira coluna ao se salvar um objeto
//					for(int j =0; j < ((PedidosCoordenadoresTableModel) tabela).getTable().getColumnCount(); j++){ //varre todos os cursos
//						
//						String codigo = ((PedidosCoordenadoresTableModel) tabela).getTable().getColumnName(colunaInicial);				
//						codigo = codigo.substring(0,codigo.indexOf(" "));	
//						
//						timetable.Curso curso = hibernate.HibernateUtil.findCursoByCode(codigo);
//						timetable.Disciplina disc = hibernate.HibernateUtil.findDisciplinaByCode(((PedidosCoordenadoresTableModel) tabela).getTable().getValueAt(j,1).toString());
//						
//						int pedidoCorrespondente = -1;
//						for(int k = 0;k<pedCoordenadores.size();k++){
//							if(pedCoordenadores.get(k).getCurso().equals(curso)){
//								pedidoCorrespondente = k;
//								break;
//							}
//			
//						}
//						
//						int discCorrespondente = -1;
//						for(int k = 0;k<pedCoordenadores.get(pedidoCorrespondente).getDisciplina().size();k++){
//							if(pedCoordenadores.get(pedidoCorrespondente).getDisciplina().get(k).equals(disc)){
//								discCorrespondente = k;
//								break;
//							}
//			
//						}
//						
//						
//						
//						colunaInicial++;
//					}
//				}			
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
