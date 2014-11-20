package interfaces;

import java.awt.Color;
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
import javax.swing.table.TableModel;

import timetable.Docente;
import timetable.Turma;

public class PlanoDepartamental extends InterfacesTabela{
	
	public PlanoDepartamental() {
		super(new PlanoDepartamentalTableModel(), "Salvar");
		
		insereProfComboBox(((PlanoDepartamentalTableModel) tabela).getTable(), ((PlanoDepartamentalTableModel) tabela).getTable().getColumnModel().getColumn(6));
		
		///TEMPORARIO
		
				ArrayList<ArrayList<Object>> cores = new ArrayList<ArrayList<Object>>();
				
				ArrayList<Object> linha = new ArrayList<Object>();
				
				linha.add(0);
				linha.add(Color.GREEN);
				
				cores.add(linha);
				
				ArrayList<Object> linha2 = new ArrayList<Object>();
				
				linha2.add(1);
				linha2.add(Color.CYAN);
				
				cores.add(linha2);
				
				for(int i=2;i<5;i++){
					ArrayList<Object> linha3 = new ArrayList<Object>();			
					linha3.add(i);
					linha3.add(Color.RED);
					cores.add(linha3);
				}
				
				for(int i=6;i<10;i++){
					ArrayList<Object> linha3 = new ArrayList<Object>();			
					linha3.add(i);
					linha3.add(Color.GRAY);
					cores.add(linha3);
				}
				
				
				int[] rows = {0,3};
				for(int i = 0; i<((PlanoDepartamentalTableModel) tabela).getTable().getColumnCount(); i++){
					((PlanoDepartamentalTableModel) this.tabela).getTable().getColumnModel().getColumn(i).setCellRenderer(new CorLinhaCellRenderer(cores));
				}
				
				///TEMPORARIO
	}

	public void insereProfComboBox(JTable table, TableColumn ComboColumn) {
		ArrayList<timetable.Docente> prof = (ArrayList<Docente>) hibernate.HibernateUtil.findAll(timetable.Docente.class);
		JComboBox<String> comboBox = new JComboBox();
		Iterator<?> it = prof.iterator();
		while(it.hasNext()){
			comboBox.addItem(((timetable.Docente)it.next()).getNomeCompleto());
		}
		ComboColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		ComboColumn.setCellRenderer(renderer);
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}

}
