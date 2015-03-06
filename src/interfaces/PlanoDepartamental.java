package interfaces;

import hibernate.HibernateUtil;

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

import antlr.collections.List;
import timetable.Docente;
import timetable.Turma;

public class PlanoDepartamental extends InterfacesTabela{
	
	public PlanoDepartamental() {
		super(new PlanoDepartamentalTableModel(), "Salvar");
		
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
						ArrayList<Turma> lista = (ArrayList<Turma>) HibernateUtil.findTurmasByCode(table.getValueAt(i, 1).toString(), table.getValueAt(i, 3).toString());
						lista.get(0).getDocente().clear();
						lista.get(0).getDocente().add(HibernateUtil.findDocenteByName(table.getValueAt(i, 6).toString()).get(0));
						System.out.println(lista.get(0).getDocente().get(0).getNome());
						System.out.println();
						HibernateUtil.saveOrUpdate(lista.get(0));
					}
				}
			}
		});
	}

	private void insereProfComboBox(JTable table, TableColumn ComboColumn) {
		ArrayList<timetable.Docente> prof = (ArrayList<Docente>) hibernate.HibernateUtil.findAll(timetable.Docente.class);
		JComboBox<String> comboBox = new JComboBox();
		Iterator<?> it = prof.iterator();
		while(it.hasNext()){
			comboBox.addItem(((timetable.Docente)it.next()).getNomeCompleto());
		}
		ComboColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Clique para selecionar o professor");
		ComboColumn.setCellRenderer(renderer);
	}
	
	public void atualizaComboBox(){
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
