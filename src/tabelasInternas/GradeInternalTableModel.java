package tabelasInternas;

import hibernate.GenericoDAO;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetable.Disciplina;
import timetable.Periodo;
import timetable.Turma;

@SuppressWarnings("serial")
public class GradeInternalTableModel extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private List<Turma> linhas;
	public GenericoDAO dao;
	
	public GradeInternalTableModel(Periodo periodo) {		
		this.linhas  = periodo.getTurma();
		this.dao = new GenericoDAO();
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
				return linhas.get(rowIndex).getDisciplina().getNome() + " " + linhas.get(rowIndex).getCodigo();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		
	}
	
	public void addTurma(Turma d){
		linhas.add(d);
	}
	
	public void addLinha(){
		linhas.add(new Turma());
	}
	
	public List<Turma> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Turma> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
