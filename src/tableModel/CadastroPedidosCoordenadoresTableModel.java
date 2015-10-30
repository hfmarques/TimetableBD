package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import timetable.Curso;
import timetable.Disciplina;
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
public class CadastroPedidosCoordenadoresTableModel extends AbstractTableModel{

	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_CURSO = 1;
	private static final int COL_NOME_CURSO = 2;
	private static final int COL_CODIGO_DISCIPLINA = 3;
	private static final int COL_NOME_DISCIPLINA = 4;
	private static final int COL_TOTAL_VAGAS = 5;
	private static final int COL_PERIOTIZADOS = 6;
	private static final int COL_NAO_PERIOTIZADOS = 7;
	private static final int COL_OBSERVACOES = 8;
	private String[] colunas = new String[]{ "", "Código do Curso", "Nome do Curso", "Código da Disciplina", "Nome da Disciplina", "Total de Vagas", "Periotizados", "Não Periotizados", "Observações"};
	private ArrayList<Curso> linhas;
	private ArrayList<Turma> turma;
	private TurmaDAO turmaDAO;
	private CursoDAO cursoDAO;
	private ArrayList<Boolean> botao;	
	
	public CadastroPedidosCoordenadoresTableModel() {	
		linhas = new ArrayList<Curso>();
		turma = new ArrayList<Turma>();
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
		Curso curso = linhas.get(rowIndex);
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_CURSO:
				return curso.getCodigo();
			case COL_NOME_CURSO:
				return curso.getNome();
			case COL_CODIGO_DISCIPLINA:				
				if(turma.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turma){
						dadosInternos.add(t.getDisciplina().getCodigo() + " - " + t.getCodigo());
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_NOME_DISCIPLINA:
				if(turma.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turma){
						dadosInternos.add(t.getDisciplina().getNome());
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_TOTAL_VAGAS:
				if(turma.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turma){
						dadosInternos.add(Integer.toString(t.getMaxVagas()));
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_PERIOTIZADOS:
				if(turma.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turma){
						dadosInternos.add("0");
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true, true);
				}
				else
					return "";
			case COL_NAO_PERIOTIZADOS:
				if(turma.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turma){
						dadosInternos.add("0");
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true, true);
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
				turma = (ArrayList<Turma>) turmaDAO.procuraTurmasPorAnoSemestre(Home.getAno(), Home.getSemestre());
			linhas = (ArrayList<Curso>) cursoDAO.procuraTodos();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		linhas.sort(new Comparator<Curso>() {
			@Override
			public int compare(Curso objeto1, Curso objeto2) {
				if(objeto1.getNome().equals(objeto2.getNome())){
					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
				}
				return objeto1.getNome().compareTo(objeto2.getNome());
			}
		});
		
		turma.sort(new Comparator<Turma>() {
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
		if(botao.get(rowIndex) && turma.size() > 0){
			height = height * turma.size();
		}
		return height;
	}
	
	public void alteraValorBotao(int index){
		if(botao.get(index))
			botao.set(index, false);
		else
			botao.set(index, true);
	}
}
