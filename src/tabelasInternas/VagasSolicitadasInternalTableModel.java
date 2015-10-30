package tabelasInternas;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import hibernate.VagasSolicitadasDAO;
import timetable.VagasSolicitadas;
/**
*
* @author Héber
*/

//tabela interna com apenas uma coluna
@SuppressWarnings("serial")
public class VagasSolicitadasInternalTableModel extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private ArrayList<VagasSolicitadas> linhas;
	private boolean editable;
	private String valorExibido;
	private VagasSolicitadasDAO solicitadasDAO;
	
	public VagasSolicitadasInternalTableModel(ArrayList<VagasSolicitadas> linhas) {		
		this.linhas  = linhas;
		this.editable = false;
		this.solicitadasDAO = new VagasSolicitadasDAO();
	}
	
	//valor para valorExibido - periotizadasOfertadas, desperiotizadasOfertadas
	public VagasSolicitadasInternalTableModel(ArrayList<VagasSolicitadas> linhas, boolean editable, String valorExibido) {		
		this.linhas  = linhas;
		this.editable = editable;
		this.solicitadasDAO = new VagasSolicitadasDAO();
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
				solicitadasDAO.salvaOuEdita(linhas.get(rowIndex));
			}
			else if(valorExibido == "desperiotizadasOfertadas"){
				linhas.get(rowIndex).setVagasDesperiotOfert(Integer.parseInt(value.toString()));
				solicitadasDAO.salvaOuEdita(linhas.get(rowIndex));
			}
		default:
			System.out.println("Coluna inválida");
		}
	}
	
	public ArrayList<VagasSolicitadas> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<VagasSolicitadas> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editable;
	}
}
