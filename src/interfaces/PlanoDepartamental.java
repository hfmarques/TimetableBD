package interfaces;

import hibernate.DocenteDAO;
import hibernate.GenericoDAO;
import hibernate.HibernateUtil;
import hibernate.TurmaDAO;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.hibernate.HibernateException;

import antlr.collections.List;
import timetable.Docente;
import timetable.Turma;

public class PlanoDepartamental extends InterfacesTabela{
	
	private ArrayList<Docente> docente;
	private DocenteDAO docenteDAO;
	private TurmaDAO turmaDAO;
	
	public PlanoDepartamental() {
		super(new PlanoDepartamentalTableModel(), "Salvar");
		docenteDAO = new DocenteDAO();
		turmaDAO = new TurmaDAO();
		
		docente = new ArrayList<Docente>();
		try {
			docente = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		insereProfComboBox(((PlanoDepartamentalTableModel) tabela).getTable(), ((PlanoDepartamentalTableModel) tabela).getTable().getColumnModel().getColumn(6));
		
		ArrayList<ArrayList<Object>> cor = PlanoDepartamentalTableModel.getCor();
		
		for(int i = 0; i<((PlanoDepartamentalTableModel) tabela).getTable().getColumnCount(); i++){
			((PlanoDepartamentalTableModel) this.tabela).getTable().getColumnModel().getColumn(i).setCellRenderer(new CorLinhaCellRenderer(cor));
		}
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable table = ((PlanoDepartamentalTableModel) tabela).getTable();
				for(int i=0;i<table.getRowCount();i++){
					if(!table.getValueAt(i, 6).toString().equals("Clique para escolher o Docente")){
						Turma turma = null;
						try {
							turma = turmaDAO.encontraTurmasPorCodigo(table.getValueAt(i, 1).toString(), table.getValueAt(i, 3).toString());
						} catch (HibernateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(turma == null) // condição teoricamente impossivel
							break;
						
						turma.getDocente().clear();
						Iterator<Docente> itProf = docente.iterator();
						Docente professor = null;
						while(itProf.hasNext()){
							Docente aux = itProf.next();
							System.out.println(table.getValueAt(i, 6).toString());
							if(aux.getNomeCompleto().equals(table.getValueAt(i, 6).toString())){
								professor = aux;
								break;
							}									
						}
						if(professor == null)
							System.out.println("NULLLLO");
						turma.getDocente().add(professor);
						System.out.println(professor.getNome());
						System.out.println();
						genericoDAO.salvaOuEdita(turma);
					}
				}
			}
		});
	}

	private void insereProfComboBox(JTable table, TableColumn ComboColumn) {
		JComboBox<String> comboBox = new JComboBox();
		Iterator<?> it = docente.iterator();
		while(it.hasNext()){
			comboBox.addItem(((timetable.Docente)it.next()).getNomeCompleto());
		}
		ComboColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Clique para selecionar o professor");
		ComboColumn.setCellRenderer(renderer);
	}
	
	public void atualizaComboBox(){
		try {
			docente = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		insereProfComboBox(((PlanoDepartamentalTableModel) tabela).getTable(), ((PlanoDepartamentalTableModel) tabela).getTable().getColumnModel().getColumn(6));
		
		ArrayList<ArrayList<Object>> cor = PlanoDepartamentalTableModel.getCor();
		
		for(int i = 0; i<((PlanoDepartamentalTableModel) tabela).getTable().getColumnCount(); i++){
			((PlanoDepartamentalTableModel) this.tabela).getTable().getColumnModel().getColumn(i).setCellRenderer(new CorLinhaCellRenderer(cor));
		}
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}

}
