package tabelasInternas;

import interfaces.CellRenderer;
import timetable.VagasAtendidas;
import timetable.VagasSolicitadas;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class DefaultInternalTable extends JPanel{
	
	private JTable table;
	private AbstractTableModel tableModel;
	
	//boolean cores = flag para indicar se haverá cor nas tabelas internas
	public DefaultInternalTable(ArrayList<String> linhas, ArrayList<Color> coresLinhas, boolean cores){
		super(new GridLayout(1, 0));
		
		tableModel = new DefaultInternalTableModel(linhas);
		table  = new JTable(tableModel);
		
		if(cores){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}
	
	public DefaultInternalTable(ArrayList<String> linhas, ArrayList<Color> coresLinhas, boolean cores, boolean editable){
		super(new GridLayout(1, 0));
		
		tableModel = new DefaultInternalTableModel(linhas, editable);
		table  = new JTable(tableModel);
		
		if(cores){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}
	
	@SuppressWarnings("unchecked")
	public DefaultInternalTable(ArrayList<?> linhas, ArrayList<Color> coresLinhas, boolean cores, boolean editable, String dadosExibidos, Object tipoClasse){
		super(new GridLayout(1, 0));
		
		if(tipoClasse.getClass() == VagasAtendidas.class){
			tableModel = new VagasAtendidasInternalTableModel((ArrayList<VagasAtendidas>) linhas, editable, dadosExibidos);
		}
		else if(tipoClasse.getClass() == VagasSolicitadas.class){
			tableModel = new VagasSolicitadasInternalTableModel((ArrayList<VagasSolicitadas>) linhas, editable, dadosExibidos);
		}
		table  = new JTable(tableModel);
		
		if(cores){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}

	public JTable getTable() {
		return table;
	}

	public AbstractTableModel getTableModel() {
		return tableModel;
	}
}
