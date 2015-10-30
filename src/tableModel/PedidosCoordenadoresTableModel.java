package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import timetable.Curso;
import timetable.Turma;
import hibernate.CursoDAO;
import hibernate.TurmaDAO;
import interfaces.Home;
import tabelasInternas.DefaultInternalTable;

/**
*
* @author Héber
*/

@SuppressWarnings("serial")
public class PedidosCoordenadoresTableModel extends AbstractTableModel{

	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_DISCIPLINA = 1;
	private static final int COL_NOME_DISCIPLINA = 2;
	private static final int COL_TOTAL_VAGAS = 3;
	private static final int COL_CODIGO_CURSO = 4;
	private static final int COL_NOME_CURSO = 5;	
	private static final int COL_NAO_PERIOTIZADOS = 6;
	private static final int COL_PERIOTIZADOS = 7;
	private static final int COL_OBSERVACOES = 8;
	private String[] colunas = new String[]{ "", "Código do Curso", "Nome do Curso", "Código da Disciplina", "Nome da Disciplina", "Total de Vagas", "Periotizados", "Não Periotizados", "Observações"};
	private ArrayList<Turma> linhas;
	private ArrayList<Curso> curso;
	private TurmaDAO turmaDAO;
	private CursoDAO cursoDAO;
	private ArrayList<Boolean> botao;	
	
	public PedidosCoordenadoresTableModel() {	
		linhas = new ArrayList<Turma>();
		curso = new ArrayList<Curso>();
		botao = new ArrayList<Boolean>();
		cursoDAO = new CursoDAO();
		turmaDAO = new TurmaDAO();
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
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_DISCIPLINA:
				return turma.getDisciplina().getCodigo() + " - " + turma.getCodigo();
			case COL_NOME_DISCIPLINA:
				return turma.getDisciplina().getNome();
			case COL_TOTAL_VAGAS:
				return turma.getMaxVagas();
			case COL_CODIGO_CURSO:				
				if(curso.size()>=0 && botao.get(rowIndex)){
					for(Curso c: curso){
						dadosInternos.add(c.getCodigo());
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false);
				}
				else
					return "";
			case COL_NOME_CURSO:
				if(curso.size()>=0 && botao.get(rowIndex)){
					for(Curso c: curso){
						dadosInternos.add(c.getNome());
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false);
				}
				else
					return "";
			
			case COL_PERIOTIZADOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					for(int c = 0;c<curso.size();c++){
						dadosInternos.add("0");
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false, true);
				}
				else
					return "";
			case COL_NAO_PERIOTIZADOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					for(int c = 0;c<curso.size();c++){
						dadosInternos.add("0");
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false, true);
				}
				else
					return "";
			case COL_OBSERVACOES:
				return "";
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_BOTAO || columnIndex == COL_PERIOTIZADOS || columnIndex == COL_NAO_PERIOTIZADOS)
			return true;
		return false;
	}

	public void updateDataRows(){
		linhas.clear();
		try {
			if(Home.getAno()!=0 && Home.getSemestre()!= 0)
				linhas = (ArrayList<Turma>) turmaDAO.procuraTurmasPorAnoSemestre(Home.getAno(), Home.getSemestre());
			curso = (ArrayList<Curso>) cursoDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		curso.sort(new Comparator<Curso>() {
			@Override
			public int compare(Curso objeto1, Curso objeto2) {
				if(objeto1.getNome().equals(objeto2.getNome())){
					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
				}
				return objeto1.getNome().compareTo(objeto2.getNome());
			}
		});
		
		linhas.sort(new Comparator<Turma>() {
			@Override
			public int compare(Turma objeto1, Turma objeto2) {
				if(objeto1.getDisciplina().getNome().equals(objeto2.getDisciplina().getNome())){
					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
				}
				return objeto1.getDisciplina().getNome().compareTo(objeto2.getDisciplina().getNome());
			}
		});	
		
		botao.clear(); 
		
		for(int i=0; i<linhas.size(); i++)
			botao.add(true);
	}

	public static int getColBotao() {
		return COL_BOTAO;
	}

	public int getInternalTableHeight(int rowIndex){
		int height = 16;
		if(botao.get(rowIndex) && curso.size() > 0){
			height = height * curso.size();
		}
		return height;
	}
	
	public void alteraValorBotao(int index){
		if(botao.get(index))
			botao.set(index, false);
		else
			botao.set(index, true);
	}

	public ArrayList<Turma> getLinhas() {
		return linhas;
	}
}