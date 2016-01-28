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
 * @author Héber
 */

@SuppressWarnings("serial")
public class TurmaTableModel extends AbstractTableModel {		
	private static final int COL_CODIGO = 0;
	private static final int COL_TURNO = 1;
	private static final int COL_MAXIMO_VAGAS = 2;
	private static final int COL_NOME_DISCOPLINA = 3;
	private static final int COL_CODIGO_DISCIPLINA = 4;
	private static final int COL_SALA = 5;
	private String[] colunas = new String[]{"Código", "Turno", "Máximo de Vagas", "Nome da Diciplina", "Código da Disciplina", "Sala"};
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
				System.out.println("Coluna inválida");
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
			if(value.toString().equals("Diurno") || value.toString().equals("Noturno")){
				turma.setTurno(value.toString());
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "O valor inserido no campo \"Turno\" é diferente de \"Diurno\" ou \"Noturno\", por favor insira um destes dois valores", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		case COL_MAXIMO_VAGAS:
			if(value.toString().matches("^[0-9]*$")){
				int maxVagas = Integer.parseInt(value.toString());
				turma.setMaxVagas(maxVagas);
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "O campo \"Máximo de vagas\" não é um número, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}				
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
				fireTableCellUpdated(rowIndex, COL_NOME_DISCOPLINA);
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(), "Código da disciplina não existe, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(new JFrame(), "Número da sala não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				fireTableRowsUpdated(rowIndex, rowIndex);
				break;
			}
			turma.setSala(sala);
			break;
		default:
			System.out.println("Coluna inválida");
		}
		
		if(turma.getCodigo() != "" && turma.getTurno() != "" && turma.getMaxVagas() != 0 && turma.getDisciplina() != null && turma.getSala()!= null){
			if(turma.getAno() == 0 || turma.getSemestre() == 0){
				turma.setAno(Home.getAno());
				turma.setSemestre(Home.getSemestre());
			}
			
			Boolean existeTurma = false;
			for(Turma t: linhas){
				if(t.getDisciplina().getCodigo().equals(turma.getDisciplina().getCodigo()) && t.getCodigo().equals(turma.getCodigo()) && !t.equals(turma)){
					existeTurma = true;
				}
			}
			
			if(existeTurma){
				JOptionPane.showMessageDialog(new JFrame(), "Já existe turma \"" + turma.getCodigo() + "\" da diciplina \"" + turma.getDisciplina().getNome() + "\"", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}else{
				turmaDAO.salvaOuEdita(turma);
			}
			
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
		Turma t = linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha);
		turmaDAO.exclui(t);
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