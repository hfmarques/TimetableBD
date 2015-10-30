package tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import hibernate.DisciplinaDAO;
import timetable.Disciplina;

@SuppressWarnings("serial")
public class DisciplinaTableModel extends AbstractTableModel /* extends AbstractTableModel */{
	private static final int COL_CODIGO = 0;
	private static final int COL_CREDITO = 1;
	private static final int COL_NOME = 2;
	private static final int COL_PERFIL = 3;
	private String[] colunas = new String[]{"C�digo", "C�ditos", "Nome", "Perfil"};
	private ArrayList<Disciplina> linhas;
	private DisciplinaDAO disciplinaDAO;

	public DisciplinaTableModel() {			
		linhas = new ArrayList<Disciplina>();
		disciplinaDAO = new DisciplinaDAO();
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
		Disciplina disciplina = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				return disciplina.getCodigo();
			case COL_CREDITO:
				return disciplina.getCreditos();
			case COL_NOME:
				return disciplina.getNome();
			case COL_PERFIL:
				return disciplina.getPerfil();
			default:
				System.out.println("Coluna inv�lida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Disciplina disciplina = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_CODIGO:
			String codigo = value.toString();
			disciplina.setCodigo(codigo);
			break;
		case COL_CREDITO:
			int credito = Integer.parseInt(value.toString());
			disciplina.setCreditos(credito);
			break;
		case COL_NOME:
			String nome = value.toString();
			disciplina.setNome(nome);
			break;
		case COL_PERFIL:
			String perfil = value.toString();
			disciplina.setPerfil(perfil);
			break;
		default:
			System.out.println("Coluna inv�lida");
		}
		
		if(disciplina.getCodigo() != null && disciplina.getCreditos() != 0 && disciplina.getNome() != null && disciplina.getPerfil() != null)
			disciplinaDAO.salvaOuEdita(disciplina);
	}
	
	public Disciplina getDisciplina(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addDisciplina(Disciplina disciplina){
		linhas.add(disciplina);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateDisciplina(int indiceLinha, Disciplina disciplina) { 
		linhas.set(indiceLinha, disciplina); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeDisciplina(int indiceLinha) { 
		linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha); 
	}

	public ArrayList<Disciplina> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Disciplina> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Disciplina>) disciplinaDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}