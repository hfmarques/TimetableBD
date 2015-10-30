package interfaceTabelas;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;

import interfaces.CellRenderer;
import tableModel.PlanoDepartamentalTableModel;
import timetable.Disciplina;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class PlanoDepartamental extends InterfacesTabela {
	
	private CellRenderer renderer;
	private ArrayList<Color> coresLinhas;
	
	public PlanoDepartamental(){		
		super(new PlanoDepartamentalTableModel());
		
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		
		coresLinhas = new ArrayList<Color>();
		renderer = new CellRenderer();		
		
		for(int i=0;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);		
	}
	
	public void updateTable() {		
		((PlanoDepartamentalTableModel)tableModel).updateDataRows();
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		preencheCoresLinhas();
		((PlanoDepartamentalTableModel)tableModel).fireTableDataChanged();
	}
	
	private void preencheCoresLinhas(){
		int numeroLinhas = table.getRowCount();
		coresLinhas.clear();
		Disciplina.resetCoresPerfis();
		for(int i=0; i<numeroLinhas;i++){
			Color cor = Disciplina.getOrSetCoresPerfis(((PlanoDepartamentalTableModel)tableModel).getLinhas().get(i).getDisciplina().getPerfil());
			coresLinhas.add(cor);
		}
		renderer.setCoresLinhas(coresLinhas);
	}
}