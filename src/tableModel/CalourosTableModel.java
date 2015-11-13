package tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import timetable.Calouros;
import hibernate.CalourosDAO;

/**
 *
 * @author H�ber
 */

@SuppressWarnings("serial")
public class CalourosTableModel extends AbstractTableModel {		
	private static final int COL_ID = 0;
	private static final int COL_NUMERO_VAGAS = 1;
	private String[] colunas = new String[]{"ID", "Numero de Vagas"};
	private ArrayList<Calouros> linhas;
	private CalourosDAO calourosDAO;
	
	public CalourosTableModel() {		
		linhas = new ArrayList<Calouros>();
		calourosDAO = new CalourosDAO();
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
		Calouros calouro = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_ID:
				return calouro.getIdCalouro();
			case COL_NUMERO_VAGAS:
				return calouro.getNumVagas();
			default:
				System.out.println("Coluna inv�lida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Calouros calouro = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_ID:
				int id = Integer.parseInt(value.toString());
				calouro.setIdCalouro(id);
				break;
			case COL_NUMERO_VAGAS:
				int numeroVagas = Integer.parseInt(value.toString());
				calouro.setNumVagas(numeroVagas);				
				break;
			default:
				System.out.println("Coluna inv�lida");
		}
		if(calouro.getNumVagas() != 0)
			calourosDAO.salvaOuEdita(calouro);
	}
	
	public Calouros getCalouro(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addCalouro(Calouros calouro){
		linhas.add(calouro);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateCalouro(int indiceLinha, Calouros calouro) { 
		linhas.set(indiceLinha, calouro); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeCalouro(int indiceLinha) { 
		linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha); 
	}

	public ArrayList<Calouros> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Calouros> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(rowIndex != COL_ID)
			return true;
		return false;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Calouros>) calourosDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
