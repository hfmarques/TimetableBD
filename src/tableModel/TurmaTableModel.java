package tableModel;

import interfaces.Home;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import timetable.Disciplina;
import timetable.Sala;
import timetable.Turma;
import hibernate.DisciplinaDAO;
import hibernate.SalaDAO;
import hibernate.TurmaDAO;

/**
 *
 * @author H�ber
 */

@SuppressWarnings("serial")
public class TurmaTableModel extends AbstractTableModel {		
	private static final int COL_CODIGO = 0;
	private static final int COL_TURNO = 1;
	private static final int COL_MAXIMO_VAGAS = 2;
	private static final int COL_NOME_DISCOPLINA = 3;
	private static final int COL_CODIGO_DISCIPLINA = 4;
	private static final int COL_SALA = 5;
	private String[] colunas = new String[]{"C�digo", "Turno", "M�ximo de Vagas", "Nome da Diciplina", "C�digo da Disciplina", "Sala"};
	private ArrayList<Turma> linhas;
	private TurmaDAO turmaDAO;
	private DisciplinaDAO disciplinaDAO;
	@SuppressWarnings("unused")
	private SalaDAO salaDAO;
	
	public TurmaTableModel() {		
		linhas = new ArrayList<Turma>();
		turmaDAO = new TurmaDAO();
		salaDAO = new SalaDAO();
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
		Turma turma = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				return turma.getCodigo();
			case COL_TURNO:
				return turma.getTurno();
			case COL_MAXIMO_VAGAS:
				return turma.getMaxVagas();
			case COL_NOME_DISCOPLINA:
				if(turma.getDisciplina() == null)
					return "";
				return turma.getDisciplina().getNome();
			case COL_CODIGO_DISCIPLINA:
				if(turma.getDisciplina() == null)
					return "";
				return turma.getDisciplina().getCodigo();
			case COL_SALA:
				if(turma.getSala() == null)
					return "";
				return turma.getSala().getNumero();
			default:
				System.out.println("Coluna inv�lida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Turma turma = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_CODIGO:
			turma.setCodigo(value.toString());			
			break;
		case COL_TURNO:
			turma.setTurno(value.toString());
			break;
		case COL_MAXIMO_VAGAS:
			int maxVagas = Integer.parseInt(value.toString());
			turma.setMaxVagas(maxVagas);
			break;
		case COL_CODIGO_DISCIPLINA:
			Disciplina disciplina = null;
			try {
				disciplina = disciplinaDAO.encontraDisciplinaPorCodigo(value.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(disciplina != null){
				turma.setDisciplina(disciplina);
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(), "C�digo da disciplina n�o existe, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		case COL_SALA:
			Sala sala = null;
			try {
				sala = SalaDAO.encontraSalaPorNumero(value.toString());
			} catch (Exception e) {
				e.printStackTrace();				
			}
			if (sala == null){
				JOptionPane.showMessageDialog(new JFrame(), "N�mero da sala n�o encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				fireTableRowsUpdated(rowIndex, rowIndex);
				break;
			}
			turma.setSala(sala);
			break;
		default:
			System.out.println("Coluna inv�lida");
		}
		
		if(turma.getCodigo() != "" && turma.getTurno() != "" && turma.getMaxVagas() != 0 && turma.getDisciplina() != null && turma.getSala()!= null){
			if(turma.getAno() == 0 || turma.getSemestre() == 0){
				turma.setAno(Home.getAno());
				turma.setSemestre(Home.getSemestre());
			}
			turmaDAO.salvaOuEdita(turma);
		}

	}
	
	public Turma getTurma(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addTurma(Turma turma){
		linhas.add(turma);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateTurma(int indiceLinha, Turma turma) { 
		linhas.set(indiceLinha, turma); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeTurma(int indiceLinha) { 
		linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha); 
	}

	public ArrayList<Turma> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Turma> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex != COL_NOME_DISCOPLINA )
			return true;
		else
			return false;			
	}
	
	public void updateDataRows(){
		try {
			if(Home.getAno() != 0 && Home.getSemestre() != 0)
				linhas = (ArrayList<Turma>) turmaDAO.procuraTurmasPorAnoSemestre(Home.getAno(), Home.getSemestre());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}