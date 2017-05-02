package tableModel;

import interfaces.Home;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import timetable.Disciplina;
import timetable.Horario;
import timetable.Sala;
import timetable.Turma;
import hibernate.DisciplinaDAO;
import hibernate.HorarioDAO;
import hibernate.SalaDAO;
import hibernate.TurmaDAO;

/**
 *
 * @author Hï¿½ber
 */

@SuppressWarnings("serial")
public class TurmaTableModel extends AbstractTableModel {		
	private static final int COL_CODIGO = 0;
	private static final int COL_TURNO = 1;
	private static final int COL_MAXIMO_VAGAS = 2;
	private static final int COL_NOME_DISCOPLINA = 3;
	private static final int COL_CODIGO_DISCIPLINA = 4;
	private static final int COL_SALA = 5;
	private static final int COL_HORARIO_1 = 6;
	private static final int COL_HORARIO_2 = 7;
	private static final int COL_HORARIO_FIXO = 8;
	private String[] colunas = new String[]{"Codigo", "Turno", "Maximo de Vagas", "Nome da Diciplina", "Codigo da Disciplina", "Sala", "Dia", "Dia", "Horario Fixo"};
	private ArrayList<Turma> linhas;
	private TurmaDAO turmaDAO;
	private DisciplinaDAO disciplinaDAO;
	private HorarioDAO horarioDAO;
	@SuppressWarnings("unused")
	private SalaDAO salaDAO;
	
	public TurmaTableModel() {		
		linhas = new ArrayList<Turma>();
		turmaDAO = new TurmaDAO();
		salaDAO = new SalaDAO();
		disciplinaDAO = new DisciplinaDAO();
		horarioDAO = new HorarioDAO();
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
			case COL_HORARIO_1:
				if(turma.getHorario() == null)
					return "";
				return turma.getHorario().getDia1();
			case COL_HORARIO_2:
				if(turma.getHorario() == null)
					return "";
				return turma.getHorario().getDia2();
			case COL_HORARIO_FIXO:
				return turma.isHorarioFixo();
			default:
				System.out.println("Coluna invï¿½lida");
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
				JOptionPane.showMessageDialog(new JFrame(), "O valor inserido no campo \"Turno\" ï¿½ diferente de \"Diurno\" ou \"Noturno\", por favor insira um destes dois valores", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
			break;
		case COL_MAXIMO_VAGAS:
			if(value.toString().matches("^[0-9]*$")){
				int maxVagas = Integer.parseInt(value.toString());
				turma.setMaxVagas(maxVagas);
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "O campo \"Mï¿½ximo de vagas\" nï¿½o ï¿½ um nï¿½mero, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(new JFrame(), "Cï¿½digo da disciplina nï¿½o existe, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(new JFrame(), "Nï¿½mero da sala nï¿½o encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				fireTableRowsUpdated(rowIndex, rowIndex);
				break;
			}
			turma.setSala(sala);
			break;
			
		case COL_HORARIO_1:
			if(turma.getHorario() == null){
				turma.setHorario(new Horario());
			}
			SimpleDateFormat formatter = new SimpleDateFormat("EEE HH:mm");
			Date data;
			try {
				data = formatter.parse(value.toString());
			} catch (ParseException e2) {
				JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
				fireTableRowsUpdated(rowIndex, rowIndex);
				break;
			}
			turma.getHorario().setDia1(data);
			fireTableCellUpdated(rowIndex, COL_HORARIO_1);
			break;
		case COL_HORARIO_2:
			if(turma.getHorario() == null){
				turma.setHorario(new Horario());
			}
			SimpleDateFormat formatter2 = new SimpleDateFormat("EEE HH:mm");
			Date data2;
			try {
				data2 = formatter2.parse(value.toString());
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
				fireTableRowsUpdated(rowIndex, rowIndex);
				break;
			}
			turma.getHorario().setDia2(data2);
			fireTableCellUpdated(rowIndex, COL_HORARIO_2);
			break;
		case COL_HORARIO_FIXO:
			turma.setHorarioFixo((boolean) value);
			break;
		default:
			System.out.println("Coluna invï¿½lida");
		}
		
		if(turma.getCodigo() != "" && turma.getTurno() != "" && turma.getMaxVagas() != 0 && turma.getDisciplina() != null && turma.getHorario() != null && turma.getHorario().getDia1() != null){
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
				horarioDAO.salvaOuEdita(turma.getHorario());
				turmaDAO.salvaOuEdita(turma);
			}
			
		}

	}
	
	@Override  
    public Class<?> getColumnClass(int col) {  
        if (col == COL_HORARIO_FIXO)  
            return Boolean.class;  
        else  
            return super.getColumnClass(col);  
    }
	
	public static int getColHorario1() {
		return COL_HORARIO_1;
	}
	
	public static int getColHorario2() {
		return COL_HORARIO_2;
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