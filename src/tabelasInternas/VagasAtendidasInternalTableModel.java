package tabelasInternas;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import hibernate.VagasAtendidasDAO;
import timetable.VagasAtendidas;
/**
*
* @author Héber
*/

//tabela interna com apenas uma coluna
@SuppressWarnings("serial")
public class VagasAtendidasInternalTableModel extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private ArrayList<VagasAtendidas> linhas;
	private boolean editable;
	private String valorExibido;
	private VagasAtendidasDAO atendidasDAO;
	
	public VagasAtendidasInternalTableModel(ArrayList<VagasAtendidas> linhas) {		
		this.linhas  = linhas;
		this.editable = false;
		this.atendidasDAO = new VagasAtendidasDAO();
	}
	
	//valor para valorExibido - periotizadasOfertadas, desperiotizadasOfertadas
	public VagasAtendidasInternalTableModel(ArrayList<VagasAtendidas> linhas, boolean editable, String valorExibido) {		
		this.linhas  = linhas;
		this.editable = editable;
		this.atendidasDAO = new VagasAtendidasDAO();
	}

	@Override
	public int getColumnCount() {
		return this.colunas.length;
	}

	@Override
	public int getRowCount() {
		return this.linhas.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) { 
		return colunas[columnIndex]; 
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex){
			case COL_DEFAULT:
				if(valorExibido == "periotizadasOfertadas")
					return linhas.get(rowIndex).getVagasPeriotOfert();
				else if(valorExibido == "desperiotizadasOfertadas")
					return linhas.get(rowIndex).getVagasDesperiotOfert();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		switch(columnIndex){
		case COL_DEFAULT:
			if(valorExibido == "periotizadasOfertadas"){
				linhas.get(rowIndex).setVagasPeriotOfert(Integer.parseInt(value.toString()));
				atendidasDAO.salvaOuEdita(linhas.get(rowIndex));
			}
			else if(valorExibido == "desperiotizadasOfertadas"){
				linhas.get(rowIndex).setVagasDesperiotOfert(Integer.parseInt(value.toString()));
				atendidasDAO.salvaOuEdita(linhas.get(rowIndex));
			}
		default:
			System.out.println("Coluna inválida");
		}
	}
	
	public ArrayList<VagasAtendidas> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<VagasAtendidas> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editable;
	}
}
