package tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import hibernate.DocenteDAO;
import timetable.Docente;

@SuppressWarnings("serial")
public class DocenteTableModel extends AbstractTableModel /* extends AbstractTableModel */{
	private static final int COL_CODIGO = 0;
	private static final int COL_NOME_COMPLETO = 1;
	private static final int COL_CREDITACAO_ESPERADA = 2;
	private String[] colunas = new String[]{"Código", "Nome Completo", "Creditação Esperada"};
	private ArrayList<Docente> linhas;
	private DocenteDAO docenteDAO;
	
	public DocenteTableModel() {		
		linhas = new ArrayList<Docente>();
		docenteDAO = new DocenteDAO();
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
		Docente docente = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				return docente.getCodigo();
			case COL_NOME_COMPLETO:
				return docente.getNomeCompleto();
			case COL_CREDITACAO_ESPERADA:
				return docente.getCreditacaoEsperada();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Docente docente = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				docente.setCodigo(value.toString());
				break;
			case COL_NOME_COMPLETO:
				docente.setNomeCompleto(value.toString());
				docente.setNome(value.toString().substring(0, value.toString().indexOf(" ")));
				break;
			case COL_CREDITACAO_ESPERADA:
				int creditacaoEsperada = Integer.parseInt(value.toString());
				docente.setCreditacaoEsperada(creditacaoEsperada);
				break;
			default:
				System.out.println("Coluna inválida");
		}
		
		if(docente.getCodigo() != null && docente.getNome() != null && docente.getNomeCompleto() != null && docente.getCreditacaoEsperada() != 0)
			docenteDAO.salvaOuEdita(docente);
	}
	
	public Docente getDocente(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addDocente(Docente docente){
		linhas.add(docente);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateDocente(int indiceLinha, Docente docente) { 
		linhas.set(indiceLinha, docente); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeDocente(int indiceLinha) { 
		linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha); 
	}

	public ArrayList<Docente> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Docente> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}