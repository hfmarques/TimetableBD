package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetable.Curso;
import timetable.Disciplina;
import timetable.PedidosCoordenadores;
import timetable.VagasAtendidas;
import timetable.VagasSolicitadas;
import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import interfaces.Home;
import tabelasInternas.DefaultInternalTable;
import tabelasInternas.TotalVagasInternalTable;
import tabelasInternas.VagasDesperiotizadosInternalTable;
import tabelasInternas.VagasPeriotizadosInternalTable;

/**
*
* @author Héber
*/

@SuppressWarnings("serial")
public class PedidosCoordenadoresTableModel extends AbstractTableModel{

	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_DISCIPLINA = 1;
	private static final int COL_NOME_DISCIPLINA = 2;	
	private static final int COL_CODIGO_CURSO = 3;
	private static final int COL_NOME_CURSO = 4;
	private static final int COL_TOTAL_VAGAS_SOLICITADAS = 5;
	private static final int COL_TOTAL_VAGAS_ATENDIDAS = 6;
	private static final int COL_PERIOTIZADOS_SOLICITADOS = 7;
	private static final int COL_PERIOTIZADOS_ATENDIDOS = 8;
	private static final int COL_NAO_PERIOTIZADOS_SOLICITADOS = 9;
	private static final int COL_NAO_PERIOTIZADOS_ATENDIDOS = 10;	
	private static final int COL_OBSERVACOES = 11;
	private String[] colunas = new String[]{ "", "Código da Disciplina", "Nome da Disciplina", "Código do Curso", "Nome do Curso", "Total de Vagas Solicitadas", "Total de Vagas Atendidas", "Periotizados Solicitados", "Periotizados Atendidos", "Não Periotizados Solicitado", "Não Periotizados Atendidos", "Observações"};
	private ArrayList<Disciplina> linhas;
	private ArrayList<Curso> curso;
	private DisciplinaDAO disciplinaDAO;
	private CursoDAO cursoDAO;
	private ArrayList<Boolean> botao;	
	
	public PedidosCoordenadoresTableModel() {	
		linhas = new ArrayList<Disciplina>();
		curso = new ArrayList<Curso>();
		botao = new ArrayList<Boolean>();
		cursoDAO = new CursoDAO();
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
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_DISCIPLINA:
				return disciplina.getCodigo();
			case COL_NOME_DISCIPLINA:
				return disciplina.getNome();
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
				
			case COL_TOTAL_VAGAS_SOLICITADAS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasSolicitadas> vagasSolicitadas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasSolicitadas> vagas = linhas.get(rowIndex).getVagasSolicitadas();
						for(VagasSolicitadas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasSolicitadas.add(vs);
								break;
							}
						}
					}
					return new TotalVagasInternalTable(vagasSolicitadas, coresInternas);
				}
				else
					return "";
				
			case COL_TOTAL_VAGAS_ATENDIDAS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasAtendidas> vagasAtendidas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasAtendidas> vagas = linhas.get(rowIndex).getVagasAtendidas();
						for(VagasAtendidas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasAtendidas.add(vs);
								break;
							}
						}
					}
					return new TotalVagasInternalTable(vagasAtendidas, coresInternas);
				}
				else
					return "";
			
			case COL_PERIOTIZADOS_SOLICITADOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasSolicitadas> vagasSolicitadas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasSolicitadas> vagas = linhas.get(rowIndex).getVagasSolicitadas();
						for(VagasSolicitadas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasSolicitadas.add(vs);
								break;
							}
						}
					}
					return new VagasPeriotizadosInternalTable(vagasSolicitadas, coresInternas);
				}
				else
					return "";
			
			case COL_PERIOTIZADOS_ATENDIDOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasAtendidas> vagasAtendidas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasAtendidas> vagas = linhas.get(rowIndex).getVagasAtendidas();
						for(VagasAtendidas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasAtendidas.add(vs);
								break;
							}
						}
					}
					return new VagasPeriotizadosInternalTable(vagasAtendidas, coresInternas);
				}
				else
					return "";
				
			case COL_NAO_PERIOTIZADOS_SOLICITADOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasSolicitadas> vagasSolicitadas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasSolicitadas> vagas = linhas.get(rowIndex).getVagasSolicitadas();
						for(VagasSolicitadas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasSolicitadas.add(vs);
								break;
							}
						}
					}
					return new VagasDesperiotizadosInternalTable(vagasSolicitadas, coresInternas);
				}
				else
					return "";
			
			case COL_NAO_PERIOTIZADOS_ATENDIDOS:
				if(curso.size()>=0 && botao.get(rowIndex)){
					List<VagasAtendidas> vagasAtendidas = new ArrayList<>();
					PedidosCoordenadores pedidosCoordenadores = null;
					for(Curso c: curso){
						for(PedidosCoordenadores p: c.getPedidosCoordenadores()){
							if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
								pedidosCoordenadores = p;
							}
						}
						List<VagasAtendidas> vagas = linhas.get(rowIndex).getVagasAtendidas();
						for(VagasAtendidas vs: vagas){
							if(vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.getIdPedidosCoordenadores()){
								vagasAtendidas.add(vs);
								break;
							}
						}
					}
					return new VagasDesperiotizadosInternalTable(vagasAtendidas, coresInternas);
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
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_BOTAO || columnIndex == COL_PERIOTIZADOS_ATENDIDOS || columnIndex == COL_NAO_PERIOTIZADOS_ATENDIDOS)
			return true;
		return false;
	}

	public void updateDataRows(){
		linhas.clear();
		try {
			if(Home.getAno()!=0 && Home.getSemestre()!= 0)
				linhas = (ArrayList<Disciplina>) disciplinaDAO.procuraTodos();
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
		
		linhas.sort(new Comparator<Disciplina>() {
			@Override
			public int compare(Disciplina objeto1, Disciplina objeto2) {
				if(objeto1.getNome().equals(objeto2.getNome())){
					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
				}
				return objeto1.getNome().compareTo(objeto2.getNome());
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

	public ArrayList<Disciplina> getLinhas() {
		return linhas;
	}
}