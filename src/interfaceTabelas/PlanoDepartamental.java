package interfaceTabelas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;

import estruturasAuxiliaresTabelas.CellRenderer;
import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import hibernate.DocenteDAO;
import hibernate.HorarioDAO;
import hibernate.HorariosDocentesDAO;
import hibernate.TurmaDAO;
import interfaces.Home;
import interfaces.LayoutConstraints;
import tableModel.PlanoDepartamentalTableModel;
import timetable.Disciplina;
import timetable.Docente;
import timetable.Horario;
import timetable.Sala;
import timetable.Turma;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class PlanoDepartamental extends InterfacesTabela {
	
	private JButton botaoImportarDados;
	private CellRenderer renderer;
	private ArrayList<Color> coresLinhas;
	
	public PlanoDepartamental(){		
		super(new PlanoDepartamentalTableModel(), "Gerar Grade");
		
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		
		coresLinhas = new ArrayList<Color>();
		renderer = new CellRenderer();		
		

		for(int i=0;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		
		botaoImportarDados = new JButton("Importar Dados");
		painelBotao.add(botaoImportarDados);
		
		// seta a posição do botão de horários dos professores
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 150);
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoImportarDados, constraints);

		botaoPadrao.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				TurmaDAO turmaDAO = new TurmaDAO();
				HorarioDAO horarioDAO = new HorarioDAO();
				FileWriter arq = null;
				try {
					arq = new FileWriter("timetableMH/dados.tbd");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedWriter escrita = new BufferedWriter(arq);
				
				ArrayList<timetable.Docente> docente = null;
				DocenteDAO docenteDAO = new DocenteDAO();
				try {
					docente = (ArrayList<timetable.Docente>) docenteDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				ArrayList<timetable.Disciplina> disciplina = null;
				try {
					DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
					disciplina = (ArrayList<timetable.Disciplina>) disciplinaDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				ArrayList<timetable.Turma> turma = null;
				try {
					turma = (ArrayList<timetable.Turma>) turmaDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				ArrayList<timetable.Curso> curso = null;
				try {
					CursoDAO cursoDAO = new CursoDAO();
					curso = (ArrayList<timetable.Curso>) cursoDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {				
					escrita.write( "# periodo, numero de docentes, numero de disciplinas, numero de turmas, numero de cursos \n"
								 + "# Docentes: \n"
								 + "# 	id, código, nome, creditação esperada, horarios\n"
								 + "# 	seg, ter, qua, qui, sex x 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22\n"
								 + "# Disciplina: \n"
								 + "# 	id, código, crédito, perfil\n"
								 + "# Turma: \n"
								 + "#	id, código, turno, horario fixo, dia1(em número), horario1, dia2(em número), horario2, id disciplina, id docente\n"
								 + "# Grades:\n"
								 + "# 	Código do curso, qtd de periodos, turno\n"
								 + "#   quantidade de disciplinas no periodo e id das disciplinas do periodo, onde cada linha é um periodo\n"
								 + "##############################################################################################################\n\n"
								);
								
					escrita.write(Home.getSemestre() + "\n");
					escrita.write(docente.size()  + "\n");
					escrita.write(disciplina.size()  + "\n");
					escrita.write(turma.size()  + "\n");
					escrita.write(curso.size() + "\n\n");
					for(Iterator<?> it = docente.iterator(); it.hasNext();){
						timetable.Docente doc = ((timetable.Docente)it.next());
						escrita.write(doc.getIdDocente() + " " + doc.getCodigo() + " " + doc.getNome() + " " + doc.getCreditacaoEsperada());
						escrita.newLine();
						ArrayList<timetable.HorariosDocentes> horariosDocentes = null;
						try {
							HorariosDocentesDAO horariosDocentesDAO = new HorariosDocentesDAO();
							horariosDocentes = (ArrayList<timetable.HorariosDocentes>) horariosDocentesDAO.encontraHorariosPorDocente(doc.getCodigo());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						escrita.write(horariosDocentes.get(0).isOito().toString() + " " + horariosDocentes.get(0).isNove().toString() + " " + horariosDocentes.get(0).isDez().toString() + " " + horariosDocentes.get(0).isOnze().toString() + " " + 
								horariosDocentes.get(0).isDoze().toString() + " " + horariosDocentes.get(0).isTreze().toString() + " " + horariosDocentes.get(0).isQuatorze().toString() + " " + horariosDocentes.get(0).isQuinze().toString() + " " + 
								horariosDocentes.get(0).isDezesseis().toString() + " " + horariosDocentes.get(0).isDezessete().toString() + " " + horariosDocentes.get(0).isDezoito().toString() + " " + horariosDocentes.get(0).isDezenove().toString() + " " + 
								horariosDocentes.get(0).isVinte().toString() + " " + horariosDocentes.get(0).isVinteUm().toString() + " " + horariosDocentes.get(0).isVinteDois().toString() + " \n"
								
							 	+ horariosDocentes.get(1).isOito().toString() + " " + horariosDocentes.get(1).isNove().toString() + " " + horariosDocentes.get(1).isDez().toString() + " " + horariosDocentes.get(1).isOnze().toString() + " " + 
								horariosDocentes.get(1).isDoze().toString() + " " + horariosDocentes.get(1).isTreze().toString() + " " + horariosDocentes.get(1).isQuatorze().toString() + " " + horariosDocentes.get(1).isQuinze().toString() + " " + 
								horariosDocentes.get(1).isDezesseis().toString() + " " + horariosDocentes.get(1).isDezessete().toString() + " " + horariosDocentes.get(1).isDezoito().toString() + " " + horariosDocentes.get(1).isDezenove().toString() + " " + 
								horariosDocentes.get(1).isVinte().toString() + " " + horariosDocentes.get(1).isVinteUm().toString() + " " + horariosDocentes.get(1).isVinteDois().toString() + " \n"
									 
								+ horariosDocentes.get(2).isOito().toString() + " " + horariosDocentes.get(2).isNove().toString() + " " + horariosDocentes.get(2).isDez().toString() + " " + horariosDocentes.get(2).isOnze().toString() + " " + 
								horariosDocentes.get(2).isDoze().toString() + " " + horariosDocentes.get(2).isTreze().toString() + " " + horariosDocentes.get(2).isQuatorze().toString() + " " + horariosDocentes.get(2).isQuinze().toString() + " " + 
								horariosDocentes.get(2).isDezesseis().toString() + " " + horariosDocentes.get(2).isDezessete().toString() + " " + horariosDocentes.get(2).isDezoito().toString() + " " + horariosDocentes.get(2).isDezenove().toString() + " " + 
								horariosDocentes.get(2).isVinte().toString() + " " + horariosDocentes.get(2).isVinteUm().toString() + " " + horariosDocentes.get(2).isVinteDois().toString() + " \n"
								
								+ horariosDocentes.get(3).isOito().toString() + " " + horariosDocentes.get(3).isNove().toString() + " " + horariosDocentes.get(3).isDez().toString() + " " + horariosDocentes.get(3).isOnze().toString() + " " + 
								horariosDocentes.get(3).isDoze().toString() + " " + horariosDocentes.get(3).isTreze().toString() + " " + horariosDocentes.get(3).isQuatorze().toString() + " " + horariosDocentes.get(3).isQuinze().toString() + " " + 
								horariosDocentes.get(3).isDezesseis().toString() + " " + horariosDocentes.get(3).isDezessete().toString() + " " + horariosDocentes.get(3).isDezoito().toString() + " " + horariosDocentes.get(3).isDezenove().toString() + " " + 
								horariosDocentes.get(3).isVinte().toString() + " " + horariosDocentes.get(3).isVinteUm().toString() + " " + horariosDocentes.get(3).isVinteDois().toString() + " \n"
								
								+ horariosDocentes.get(4).isOito().toString() + " " + horariosDocentes.get(4).isNove().toString() + " " + horariosDocentes.get(4).isDez().toString() + " " + horariosDocentes.get(4).isOnze().toString() + " " + 
								horariosDocentes.get(4).isDoze().toString() + " " + horariosDocentes.get(4).isTreze().toString() + " " + horariosDocentes.get(4).isQuatorze().toString() + " " + horariosDocentes.get(4).isQuinze().toString() + " " + 
								horariosDocentes.get(4).isDezesseis().toString() + " " + horariosDocentes.get(4).isDezessete().toString() + " " + horariosDocentes.get(4).isDezoito().toString() + " " + horariosDocentes.get(4).isDezenove().toString() + " " + 
								horariosDocentes.get(4).isVinte().toString() + " " + horariosDocentes.get(4).isVinteUm().toString() + " " + horariosDocentes.get(4).isVinteDois().toString() + " \n");
						escrita.newLine();
						escrita.newLine();
					}
					
					escrita.newLine();
					
					
					for(Iterator<?> it = disciplina.iterator(); it.hasNext();){
						timetable.Disciplina disc = ((timetable.Disciplina)it.next());
						escrita.write(disc.getIdDisciplina() + " " + disc.getCodigo() + " " + disc.getCreditos() + " " + disc.getPerfil());
						escrita.newLine();
					}					

					escrita.newLine();

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
						SimpleDateFormat formatter = new SimpleDateFormat("EEE");
						SimpleDateFormat formatter2 = new SimpleDateFormat("HH");
						escrita.write(tur.getIdTurma() + " " + tur.getCodigo() + " " + tur.getTurno() + " " + tur.isHorarioFixo() + " ");
						if(tur.isHorarioFixo()){
							if(formatter.format(tur.getHorario().getDia1()).equals("Seg"))
								escrita.write("2" + " ");
							else if(formatter.format(tur.getHorario().getDia1()).equals("Ter"))
								escrita.write("3" + " ");
							else if(formatter.format(tur.getHorario().getDia1()).equals("Qua"))
								escrita.write("4" + " ");
							else if(formatter.format(tur.getHorario().getDia1()).equals("Qui"))
								escrita.write("5" + " ");
							else if(formatter.format(tur.getHorario().getDia1()).equals("Sex"))
								escrita.write("6" + " ");
							
							escrita.write(formatter2.format(tur.getHorario().getDia1()) +  " " );
							
							if(tur.getDisciplina().getCreditos() == 4){
								if(formatter.format(tur.getHorario().getDia2()).equals("Seg"))
									escrita.write("2" + " ");
								else if(formatter.format(tur.getHorario().getDia2()).equals("Ter"))
									escrita.write("3" + " ");
								else if(formatter.format(tur.getHorario().getDia2()).equals("Qua"))
									escrita.write("4" + " ");
								else if(formatter.format(tur.getHorario().getDia2()).equals("Qui"))
									escrita.write("5" + " ");
								else if(formatter.format(tur.getHorario().getDia2()).equals("Sex"))
									escrita.write("6" + " ");
							
								escrita.write(formatter2.format(tur.getHorario().getDia2()) +  " " );
							}
							else{
								escrita.write("-1 -1 ");
							}
						}						
						escrita.write(tur.getDisciplina().getIdDisciplina() + " " + tur.getDocente().get(0).getIdDocente());							
						escrita.newLine();
					}
					
					escrita.newLine();
					
					for(Iterator<?> it = curso.iterator(); it.hasNext();){
						timetable.Curso cur = ((timetable.Curso)it.next());
						escrita.write(cur.getCodigo() + " " + cur.getPeriodo().size() + " " + cur.getTurno());
						escrita.newLine();
						for(Iterator<?> it2 = cur.getPeriodo().iterator(); it2.hasNext();){
							timetable.Periodo per = ((timetable.Periodo)it2.next());
							escrita.write(per.getTurma().size() + " ");
							for(Iterator<?> it3 = per.getTurma().iterator(); it3.hasNext();){
								timetable.Turma tur = ((timetable.Turma)it3.next());
								escrita.write(tur.getDisciplina().getIdDisciplina() + " " + tur.getCodigo() + " ");
							}
							escrita.newLine();
						}
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
//				
//		        try {
//		        	Process p = Runtime.getRuntime().exec("MH.bat");
//		        	boolean vivo = true;
//		        	JOptionPane.showMessageDialog(new JFrame(), "Aguarde o algoritmo executar", "Aguarde", JOptionPane.OK_OPTION);
//		        	while(vivo){
//		        		vivo = p.isAlive();
//		        	}
//		        	JOptionPane.showMessageDialog(new JFrame(), "Executado com sucesso", "Sucesso", JOptionPane.OK_OPTION);
//		        } catch (Exception e1) {
//		            e1.printStackTrace();
//		            System.out.println("Não conseguiu executar o executavel");
//		        }
//				FileReader arq2 = null;
//				String linha = null;
//				try {
//					arq2 = new FileReader("timetableMH/solucao.tbd");
//				} catch (FileNotFoundException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//					return;
//				}
//			    BufferedReader ler = new BufferedReader(arq2);
//				
//				try {
//					ler.readLine();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				try {
//					ler.readLine();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				try {
//					ler.readLine();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				try {
//					ler.readLine();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				try {
//					linha = ler.readLine();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				int numero = Integer.parseInt(linha);
//			    for(int i = 0;i<numero;i++){
//			    	linha = null;
//					try {
//						linha = ler.readLine();
//					} catch (IOException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
//			    	String[] s = linha.split(" ");
//					int id = Integer.parseInt(s[0]);
//					for(int j = 0;j<turma.size();j++){
//						if(turma.get(j).getIdTurma() == id){
//							SimpleDateFormat formatter2 = new SimpleDateFormat("EEE HH");
//							Date data = null;
//							String hora = null;
//							hora = s[1];
//							
//							if(hora.equals("0"))
//								hora = "Seg ";
//							else if(hora.equals("1"))
//								hora = "Ter ";
//							else if(hora.equals("2"))
//								hora = "Qua ";
//							else if(hora.equals("3"))
//								hora = "Qui ";
//							else if(hora.equals("4"))
//								hora = "Sex ";
//							
//							hora += Integer.toString((Integer.parseInt(s[2]) + 8));
//							try {
//								data = formatter2.parse(hora);
//							} catch (ParseException e1) {
//								JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
//							}
//							turma.get(j).getHorario().setDia1(data);
//							
//							if(turma.get(j).getDisciplina().getCreditos() == 4){
//								hora = s[3];
//							
//								if(hora.equals("0"))
//									hora = "Seg ";
//								else if(hora.equals("1"))
//									hora = "Ter ";
//								else if(hora.equals("2"))
//									hora = "Qua ";
//								else if(hora.equals("3"))
//									hora = "Qui ";
//								else if(hora.equals("4"))
//									hora = "Sex ";
//								
//								hora += Integer.toString((Integer.parseInt(s[4]) + 8));
//								try {
//									System.out.println(hora);
//									data = formatter2.parse(hora);
//								} catch (ParseException e1) {
//									JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
//								}
//								turma.get(j).getHorario().setDia2(data);						
//							}
//							horarioDAO.salvaOuEdita(turma.get(j).getHorario());
//							turmaDAO.salvaOuEdita(turma.get(j));
//							break;
//						}
//					}
//				}

			}
		});
		
		botaoImportarDados.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Turma> tur = null;
				ArrayList<Disciplina> disc = null;
				ArrayList<Docente> doc = null;
				BufferedReader ler = null;
				
				TurmaDAO turmaDAO = new TurmaDAO();
				HorarioDAO horarioDAO = new HorarioDAO();
				DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
				DocenteDAO docenteDAO = new DocenteDAO();
				
				try {
					tur = (ArrayList<Turma>) turmaDAO.procuraTodos();
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					disc = (ArrayList<Disciplina>) disciplinaDAO.procuraTodos();
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					doc = (ArrayList<Docente>) docenteDAO.procuraTodos();
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				FileReader arq = null;
				String linha = null;
				try {
					arq = new FileReader("plano.csv");
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					return;
				}
			    
				ler = new BufferedReader(arq);
				
			    try {
			    	linha = ler.readLine();
			    } catch (IOException e2) {
			    	// TODO Auto-generated catch block
			    	e2.printStackTrace();
			    }
			    
			    
				while(linha != null){					
					
			    	String[] s = linha.split(";");
			    	
			    	String[] nome = s[7].split(" ");
			    	
			    	String cod_disc = s[0];
			    	String nomeDisc = s[1];
			    	String turma = s[2];
			    	int creditos = Integer.parseInt(s[5]);
			    	String codigoDoc = s[6];
			    	String docente = s[7];
			    	String perfil = s[8];
			    	String turno = s[9];
			    	
			    	Disciplina disc_ = new Disciplina(cod_disc, creditos, nomeDisc, perfil);
			    	Docente docente_ = new Docente(codigoDoc, nome[0], docente, 8);
			    	
			    	
					SimpleDateFormat formatter2 = new SimpleDateFormat("EEE HH");
					Date data1 = null;
					Date data2 = null;
					String hora = null;
					
					if(!s[3].equals("EAD")){
						hora = s[3].split("a")[0];
						
						if(hora.equals("2"))
							hora = "Seg ";
						else if(hora.equals("3"))
							hora = "Ter ";
						else if(hora.equals("4"))
							hora = "Qua ";
						else if(hora.equals("5"))
							hora = "Qui ";
						else if(hora.equals("6"))
							hora = "Sex ";
						
						hora += Integer.toString((Integer.parseInt(s[3].split(" ")[1].split("-")[0])));
						try {
							data1 = formatter2.parse(hora);
						} catch (ParseException e1) {
							JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						if(disc_.getCreditos() == 4 && !s[4].equals("EAD")){
							hora = s[4].split("a")[0];
							
							if(hora.equals("2"))
								hora = "Seg ";
							else if(hora.equals("3"))
								hora = "Ter ";
							else if(hora.equals("4"))
								hora = "Qua ";
							else if(hora.equals("5"))
								hora = "Qui ";
							else if(hora.equals("6"))
								hora = "Sex ";
							
							hora += Integer.toString((Integer.parseInt(s[4].split(" ")[1].split("-")[0])));
							try {
								data2 = formatter2.parse(hora);
							} catch (ParseException e1) {
								JOptionPane.showMessageDialog(new JFrame(), "Formato de data incorreto, Exemplo: \"seg 08:00.\"", "Erro", JOptionPane.ERROR_MESSAGE);
							}						
						}
					}					
					
					Boolean encontrou = false;
					if(disc == null){
						disc = new ArrayList<Disciplina>();
					}else{
						for (Disciplina d : disc) {
							if(d.getCodigo().equals(disc_.getCodigo()))
									encontrou = true;
						}
					}
					
					if(encontrou == false){
						disc.add(disc_);
						disciplinaDAO.salvaOuEdita(disc_);
					}
					
					encontrou = false;
					if(doc == null){
						doc = new ArrayList<Docente>();
					}else{
						for (Docente d : doc) {
							if(d.getCodigo().equals(docente_.getCodigo()))
									encontrou = true;
						}
					}
					
					
					if(encontrou == false){
						ArrayList<timetable.HorariosDocentes> h = new ArrayList<timetable.HorariosDocentes>();
						h.add(new timetable.HorariosDocentes("Segunda", docente_));
						h.add(new timetable.HorariosDocentes("Terça", docente_));
						h.add(new timetable.HorariosDocentes("Quarta", docente_));
						h.add(new timetable.HorariosDocentes("Quinta", docente_));
						h.add(new timetable.HorariosDocentes("Sexta", docente_));
						
						docente_.setHorariosDocentes(h);
						doc.add(docente_);
						docenteDAO.salvaOuEdita(docente_);
						for (timetable.HorariosDocentes horariosDocentes : h) {
							docenteDAO.salvaOuEdita(horariosDocentes);							
						}
					}
					
					Disciplina d = null;
					try {
						d = disciplinaDAO.encontraDisciplinaPorCodigo(cod_disc);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Docente dc = null;
					
					try {
						dc = docenteDAO.encontraDocentePorName(docente);
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Turma turma_ = new Turma(turma, turno, 50, d, null, dc, Home.getAno(), Home.getSemestre(), false);					
					turma_.setHorario(new Horario(data1, data2));
					encontrou = false;
					if(tur == null){
						tur = new ArrayList<Turma>();
					}else{
						for (Turma t : tur) {
							if(t.getDisciplina().getCodigo().equals(turma_.getDisciplina().getCodigo()) && t.getCodigo().equals(turma_.getCodigo()))
									encontrou = true;
						}
					}
					
					if(encontrou == false){
						tur.add(turma_);
						horarioDAO.salvaOuEdita(turma_.getHorario());
						turmaDAO.salvaOuEdita(turma_);
					}
					
					try {
						linha = ler.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
			}
		});
	}
	
	public void updateTable() {		
		((PlanoDepartamentalTableModel)tableModel).updateDataRows();
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		preencheCoresLinhas();
		((PlanoDepartamentalTableModel)tableModel).fireTableDataChanged();
	}
	
	private void preencheCoresLinhas(){
		int numeroLinhas = table.getRowCount();
		coresLinhas.clear();
		Disciplina.resetCoresPerfis();
		for(int i=0; i<numeroLinhas;i++){
			Color cor = Disciplina.getOrSetCoresPerfis(((PlanoDepartamentalTableModel)tableModel).getLinhas().get(i).getDisciplina().getPerfil());
			coresLinhas.add(cor);
		}
		renderer.setCoresLinhas(coresLinhas);
	}
}