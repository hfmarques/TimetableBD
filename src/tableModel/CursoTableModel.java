package tableModel;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import timetable.Calouros;
import timetable.Curso;
import hibernate.CalourosDAO;
import hibernate.CursoDAO;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class CursoTableModel extends AbstractTableModel {		
	private static final int COL_NOME = 0;
	private static final int COL_CODIGO = 1;
	private static final int COL_TURNO = 2;
	private static final int COL_CALOURO_PRIMEIRO = 3;
	private static final int COL_CALOURO_SEGUNDO = 4;
	private String[] colunas = new String[]{"Nome", "Código", "Turno", "Calouros no Priemiro Semestre", "Calouros no Segundo Semestre"};
	private ArrayList<Curso> linhas;
	private CursoDAO cursoDAO;
	private CalourosDAO calourosDAO;
	
	public CursoTableModel() {		
		linhas = new ArrayList<Curso>();
		cursoDAO = new CursoDAO();
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
		Curso curso = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_NOME:
			return curso.getNome();
		case COL_CODIGO:
			return curso.getCodigo();
		case COL_TURNO:
			return curso.getTurno();
		case COL_CALOURO_PRIMEIRO:
			if(curso.getCalouros().size() != Curso.getNumeroCalouros())
				return "";
			return curso.getCalouros().get(0).getNumVagas();
		case COL_CALOURO_SEGUNDO:
			if(curso.getCalouros().size() != Curso.getNumeroCalouros())
				return "";
			return curso.getCalouros().get(1).getNumVagas();			
		default:
			System.out.println("Coluna inválida");
			return null;
		}
	}
	
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Curso curso = linhas.get(rowIndex);
		Calouros calouros = null;

		switch(columnIndex){
		case COL_NOME:
			String nome = value.toString();
			curso.setNome(nome);
			break;
		case COL_CODIGO:
			String codigo = value.toString();
			curso.setCodigo(codigo);
			break;
		case COL_TURNO:
			String turno = value.toString();
			curso.setTurno(turno);
			break;
		case COL_CALOURO_PRIMEIRO:
			int numeroCalouros = Integer.parseInt(value.toString());
			try{
				calouros = calourosDAO.encontraCalouroPorVagas(numeroCalouros);		
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(calouros != null){
				if(curso.getCalouros().size()!= Curso.getNumeroCalouros())
					curso.getCalouros().add(calouros);
				else
					curso.getCalouros().set(0, calouros);
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(), "Número de calouros não existe, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		case COL_CALOURO_SEGUNDO:
			int numeroCalouros2 = Integer.parseInt(value.toString());
			try{
				calouros = calourosDAO.encontraCalouroPorVagas(numeroCalouros2);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(calouros != null){
				if(curso.getCalouros().size()!= Curso.getNumeroCalouros())
					curso.getCalouros().add(calouros);
				else
					curso.getCalouros().set(1, calouros);
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(), "Número de calouros não existe, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
			default:
				System.out.println("Coluna inválida");
		}
		
		if(curso.getNome() != null && curso.getCodigo() != null && curso.getTurno() != null && curso.getCalouros().size() == Curso.getNumeroCalouros())
			cursoDAO.salvaOuEdita(curso);			
	}
	
	public Curso getCurso(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addCurso(Curso curso){
		linhas.add(curso);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateCurso(int indiceLinha, Curso curso) { 
		linhas.set(indiceLinha, curso); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeCurso(int indiceLinha) { 
		linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha); 
	}

	public ArrayList<Curso> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Curso> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Curso>) cursoDAO.procuraTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
