package interfaceTabelas;

import hibernate.PeriodoDAO;
import hibernate.TurmaDAO;
import interfaces.Home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import estruturasAuxiliaresTabelas.BotaoTabela;
import estruturasAuxiliaresTabelas.CellRenderer;
import tableModel.ResultadosProfessorTableModel;
import timetable.Periodo;
import timetable.Turma;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class ResultadoProfessor extends InterfacesTabela {

	private BotaoTabela botaoExpandir;
	private CellRenderer renderer;
	
	public ResultadoProfessor(){		
		super(new ResultadosProfessorTableModel(), "Gerar Relatório");
		
		botaoExpandir = new BotaoTabela(table, ResultadosProfessorTableModel.getColBotao());
		renderer = new CellRenderer();
		
		for(int i=1;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		
		botaoExpandir.getEditButton().addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				((ResultadosProfessorTableModel)tableModel).alteraValorBotao(table.getSelectedRow());
				updateRow(table.getSelectedRow());				
			}
		});
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileWriter arq = null;
				try {
					arq = new FileWriter("relatório.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedWriter escrita = new BufferedWriter(arq);
				
				try {
					escrita.write("Semestre;Código da Disciplina;Disciplina;Turma;Horário 1;Horário 2;Créditos;Docente");
					escrita.newLine();
					
					ArrayList<timetable.Turma> turma = null;
					try {
						TurmaDAO turmaDAO = new TurmaDAO();
						turma = (ArrayList<timetable.Turma>) turmaDAO .procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					turma.sort(new Comparator<timetable.Turma>() {
						public int compare(timetable.Turma o1, timetable.Turma o2) {
							if(o1.getDisciplina().getPerfil().equals(o2.getDisciplina().getPerfil())){
								if(o1.getDisciplina().getNome().equals(o2.getDisciplina().getNome())){
									return o1.getCodigo().compareTo(o2.getCodigo());
								}
								return o1.getDisciplina().getNome().compareTo(o2.getDisciplina().getNome());						
							}						
							return o1.getDisciplina().getPerfil().compareTo(o2.getDisciplina().getPerfil());
						}
					});
					
					for(Iterator<?> it = turma.iterator(); it.hasNext();){
						timetable.Turma tur = ((timetable.Turma)it.next());
						String docente = "";
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						
						if(tur.getHorario() != null){
							if(tur.getHorario().getDia1() != null){
								c1.setTime(tur.getHorario().getDia1());								
							}
							if(tur.getHorario().getDia2() != null){
								c2.setTime(tur.getHorario().getDia2());								
							}							
						}
						
						if(tur.getDocente().size() != 0){
							for(Iterator<?> it1 = tur.getDocente().iterator(); it1.hasNext();){
								timetable.Docente doc = (timetable.Docente) it1.next();
								docente += doc.getNomeCompleto();
								docente += " ";
							}
						}
						docente += ";";
						if(tur.getHorario() == null || tur.getHorario().getDia1() == null)
							escrita.write(Home.getSemestre() +";"+ tur.getDisciplina().getCodigo()+ ";" + 
							tur.getDisciplina().getNome() + ";" + tur.getCodigo() + ";;;" + tur.getDisciplina().getCreditos() + ";" + docente);
						else if(tur.getHorario().getDia2() == null)
							escrita.write(Home.getSemestre() +";"+ tur.getDisciplina().getCodigo()+ ";" + 
							tur.getDisciplina().getNome() + ";" + tur.getCodigo() + ";" +
							c1.get(Calendar.DAY_OF_WEEK) + " " + c1.get(Calendar.HOUR_OF_DAY) + ";;" + tur.getDisciplina().getCreditos() + ";" + docente);
						else
							escrita.write(Home.getSemestre() +";"+ tur.getDisciplina().getCodigo()+ ";" + 
							tur.getDisciplina().getNome() + ";" + tur.getCodigo() + ";" +
							c1.get(Calendar.DAY_OF_WEEK) + " " + c1.get(Calendar.HOUR_OF_DAY) + ";" +
							c2.get(Calendar.DAY_OF_WEEK) + " " + c2.get(Calendar.HOUR_OF_DAY) + ";" + tur.getDisciplina().getCreditos() + ";" + docente);
						escrita.newLine();
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					escrita.close();
					arq.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					arq = new FileWriter("relatório2.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				escrita = new BufferedWriter(arq);
				
				try {
					escrita.write("Código Professor;Nome Professor;Nome da Disciplina;Codigo da Disciplina;Creditos da Disciplina");
					escrita.newLine();
					
					ArrayList<timetable.Turma> turma = null;
					try {
						TurmaDAO turmaDAO = new TurmaDAO();
						turma = (ArrayList<timetable.Turma>) turmaDAO .procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					turma.sort(new Comparator<timetable.Turma>() {
						public int compare(timetable.Turma o1, timetable.Turma o2) {
							return o1.getDocente().get(0).getNomeCompleto().compareTo(o2.getDocente().get(0).getNomeCompleto());
						}
					});
					
					String docente = "";
					for(Iterator<?> it = turma.iterator(); it.hasNext();){
						timetable.Turma tur = ((timetable.Turma)it.next());
						
						if(!tur.getDocente().get(0).getNomeCompleto().equals(docente)){
							docente = "";
							if(tur.getDocente().size() != 0){
								for(Iterator<?> it1 = tur.getDocente().iterator(); it1.hasNext();){
									timetable.Docente doc = (timetable.Docente) it1.next();
									docente += doc.getCodigo();
									docente += " ";
								}
								docente += ";";
								for(Iterator<?> it1 = tur.getDocente().iterator(); it1.hasNext();){
									timetable.Docente doc = (timetable.Docente) it1.next();
									docente += doc.getNomeCompleto();
									docente += " ";
								}
							}
							
							escrita.write(docente + ";" + tur.getDisciplina().getNome() + ";" + tur.getDisciplina().getCodigo() + " " + 
									tur.getCodigo() + ";" + tur.getDisciplina().getCreditos());							
						}
						else{
							escrita.write(";;" + tur.getDisciplina().getNome() + ";" + tur.getDisciplina().getCodigo() + " " + 
									tur.getCodigo() + ";" + tur.getDisciplina().getCreditos());	
						}						
						docente = tur.getDocente().get(0).getNomeCompleto();
						
						escrita.newLine();
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					escrita.close();
					arq.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					arq = new FileWriter("relatório3.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				escrita = new BufferedWriter(arq);
				
				try {
					PeriodoDAO periodoDAO = new PeriodoDAO();
					List<Periodo> periodo = periodoDAO.procuraTodos();
					
					periodo.sort(new Comparator<Periodo>() {
						@Override
						public int compare(Periodo p1, Periodo p2){
							if(p1.getCurso().getNome().equals(p2.getCurso().getNome()))
								return p1.getIdPeriodo() - p2.getIdPeriodo();
							return p1.getCurso().getNome().compareTo(p2.getCurso().getNome());
						}
					});
					String per = "";
					for(Periodo p: periodo){			
						if(!per.equals(p.getCurso().getNome())){
							escrita.write(p.getCurso().getNome());
							escrita.newLine();						
						}						
						
						per = p.getCurso().getNome();
						
						int numeroPeriodo =  p.getNumeroPeriodo() + 1;
						escrita.write("Periodo:" + ";" + numeroPeriodo);
						escrita.newLine();
						
						escrita.write(";Seg;Ter;Qua;Qui;Sex");
						escrita.newLine();
						for (int i = 0; i < 14; i++) {
							int hora = i + 8;
							escrita.write(hora + ":00;");
							String seg = "";
							String ter = "";
							String qua = "";
							String qui = "";
							String sex = "";
							for(Turma t: p.getTurma()){
								Calendar c1 = Calendar.getInstance();
								Calendar c2 = Calendar.getInstance();
								
								if(t.getHorario() != null){
									if(t.getHorario().getDia1() != null){
										c1.setTime(t.getHorario().getDia1());								
									}
									if(t.getHorario().getDia2() != null){
										c2.setTime(t.getHorario().getDia2());								
									}							
								}
								if(c1.get(Calendar.DAY_OF_WEEK) == 2){
									if(c1.get(Calendar.HOUR_OF_DAY) == hora){
										seg += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c1.get(Calendar.DAY_OF_WEEK) == 3){
									if(c1.get(Calendar.HOUR_OF_DAY) == hora){
										ter += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c1.get(Calendar.DAY_OF_WEEK) == 4){
									if(c1.get(Calendar.HOUR_OF_DAY) == hora){
										qua += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c1.get(Calendar.DAY_OF_WEEK) == 5){
									if(c1.get(Calendar.HOUR_OF_DAY) == hora){
										qui += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c1.get(Calendar.DAY_OF_WEEK) == 6){
									if(c1.get(Calendar.HOUR_OF_DAY) == hora){
										sex += t.getDisciplina().getCodigo() + " ";
									}
								}
								
								if(c2.get(Calendar.DAY_OF_WEEK) == 2){
									if(c2.get(Calendar.HOUR_OF_DAY) == hora){
										seg += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c2.get(Calendar.DAY_OF_WEEK) == 3){
									if(c2.get(Calendar.HOUR_OF_DAY) == hora){
										ter += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c2.get(Calendar.DAY_OF_WEEK) == 4){
									if(c2.get(Calendar.HOUR_OF_DAY) == hora){
										qua += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c2.get(Calendar.DAY_OF_WEEK) == 5){
									if(c2.get(Calendar.HOUR_OF_DAY) == hora){
										qui += t.getDisciplina().getCodigo() + " ";
									}
								}
								if(c2.get(Calendar.DAY_OF_WEEK) == 6){
									if(c2.get(Calendar.HOUR_OF_DAY) == hora){
										sex += t.getDisciplina().getCodigo() + " ";
									}
								}
							}
							escrita.write(seg + ";" + ter + ";" + qua + ";" + qui + ";" + sex);
							escrita.newLine();							
						}
					}				
										
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					escrita.close();
					arq.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}

	@Override
	public void updateTable() {
		int height = 0;
		((ResultadosProfessorTableModel)tableModel).updateDataRows();
		((ResultadosProfessorTableModel)tableModel).fireTableDataChanged();
		for(int i= 0; i<table.getRowCount(); i++){
			height = ((ResultadosProfessorTableModel)tableModel).getInternalTableHeight(i);
			if(height > 0)
				table.setRowHeight(i, height);
		}
		
	}
	
	public void updateRow(int rowIndex){
		((ResultadosProfessorTableModel)tableModel).fireTableRowsUpdated(rowIndex, rowIndex);
		int height = ((ResultadosProfessorTableModel)tableModel).getInternalTableHeight(rowIndex);
		table.setRowHeight(rowIndex, height);
	}
}