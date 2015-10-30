//package interfaces;
//
//import hibernate.CursoDAO;
//import hibernate.DisciplinaDAO;
//import hibernate.HibernateUtil;
//import hibernate.TurmaDAO;
//import interfaces.PedidosCoordenadoresTableModel.MyTableModel;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import timetable.Calouros;
//import timetable.Curso;
//import timetable.Disciplina;
//import timetable.Turma;
//import Utilitarios.OperacoesInterface;
//
///**
//*
//* @author Héber
//*/
//
//public class CadastroPedidosTableModel extends JPanel{
//
//	private JTable table;
//	private MyTableModel tableModel;
//	private BotaoTabela botaoExpandir;
//	private DisciplinaDAO discDAO;
//	private TurmaDAO turmaDAO;
//	private CursoDAO cursoDAO;
//	
//	
//	public CadastroPedidosTableModel() {
//		super(new GridLayout(1, 0));
//		
//		discDAO = new DisciplinaDAO();
//		cursoDAO = new CursoDAO();
//		turmaDAO = new TurmaDAO();
//		
//		ArrayList<Curso> curso = null;
//		
//		try{
//			curso = (ArrayList<Curso>) cursoDAO.procuraTodos();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////		tableModel = new MyTableModel(curso);
//		
//		table = new JTable(tableModel);
//		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
//		table.setFillsViewportHeight(true);
//
//		JScrollPane scrollPane = new JScrollPane(table);
//		table.setDefaultEditor(Integer.class, new CellEditor());
//		add(scrollPane);
//		
////		BotaoTabela botaoExpandir = new BotaoTabela(table, 0);
////		botaoExpandir.getEditButton().addActionListener(new ActionListener() {
//			
////			@Override
////			public void actionPerformed(ActionEvent e) {
////				botaoExpandir.editingStopped();
////				
////				List<Disciplina> disciplina = null;
////				List<Turma> turma = null;
////				try {
////					disciplina = discDAO.procuraTodos();
////				} catch (Exception e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////				
////				if (table.getModel().getValueAt(table.getSelectedRow(), 0).equals("+")) {
////					// caso o checkbox esteja marcado insere os dados das disciplinas
////
////					// adiciona a tabela os dados encontrados
////					int tamanhoDisc = disciplina.size();
////					
////					if(tamanhoDisc > 0){
////						String[] tCod = new String[tamanhoDisc];
////						for (int j = 0; j < tamanhoDisc; j++) {
////							tCod[j] = disciplina.get(j).getCodigo();
////						}
////						
////						String[] tDisc = new String[tamanhoDisc];
////						for (int j = 0; j <tamanhoDisc; j++) {
////							tDisc[j] = disciplina.get(j).getNome();
////						}
////						
////						String[] totVagas = new String[tamanhoDisc];
////						for (int j = 0; j <tamanhoDisc; j++) {
////							try {
////								turma = turmaDAO.encontraTurmasPorCodigoDisc(tCod[j]);
////							} catch (Exception e1) {
////								// TODO Auto-generated catch block
////								e1.printStackTrace();
////							}
////							int maxVagas = 0;
////							for(Turma t: turma){
////								maxVagas += t.getMaxVagas();
////							}
////							totVagas[j] = Integer.toString(maxVagas);
////						}
////						
////						String[] periotizados = new String[tamanhoDisc];
////						for (int j = 0; j <tamanhoDisc; j++) {
////							periotizados[j] = "0/0";
////						}
////						
////						table.setRowHeight(table.getSelectedRow(), tamanhoDisc*20);
////						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tCod, false), table.getSelectedRow(), 3);
////						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tDisc, false), table.getSelectedRow(), 4);
////						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, totVagas, false), table.getSelectedRow(), 5);
////						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, periotizados, true), table.getSelectedRow(), 6);
////						
////						disciplina.clear();					
////					}
////					table.getModel().setValueAt("-", table.getSelectedRow(), 0);
////				} else { // caso contrario os retira se estiverem na tabela
////					String[] empty = { "" };
////					table.setRowHeight(table.getSelectedRow(), 20);
////					table.getModel().setValueAt(empty, table.getSelectedRow(), 3);
////					table.getModel().setValueAt(empty, table.getSelectedRow(), 4);
////					table.getModel().setValueAt(empty, table.getSelectedRow(), 5);
////					table.getModel().setValueAt(empty, table.getSelectedRow(), 6);
////					table.getModel().setValueAt("+", table.getSelectedRow(), 0);
////				}
////				
////				disciplina.clear();
////				
////			}
////		});
//	}
//		
//	
//	public JTable getTable() {
//		return table;
//	}
//
//	public void setTable(JTable table) {
//		this.table = table;
//	}
//	
//	public MyTableModel getTableModel() {
//		return tableModel;
//	}
//	
//	class MyTableModel extends AbstractTableModel {
//		
//		private static final int COL_BOTAO = 0;
//		private static final int COL_CODIGO_CURSO = 1;
//		private static final int COL_NOME_CURSO = 2;
//		private static final int COL_CODIGO_DISCIPLINA = 3;
//		private static final int COL_NOME_DISCIPLINA = 4;
//		private static final int COL_TOTAL_VAGAS = 5;
//		private static final int COL_PERIOTIZADOS = 6;
//		private static final int COL_OBSERVACOES = 7;
//		private String[] colunas = new String[]{ "", "Código do Curso", "Nome do Curso", "Código da Disciplina",
//		"Nome da Disciplina", "Total de Vagas", "Periotizados/não Periotizados", "Observações"};
//		ArrayList<Object> linhas;
//		
//		public MyTableModel() {
//			linhas = new ArrayList<Object>();			
//		}
//		
//		@Override
//		public int getColumnCount() {
//			return this.colunas.length;
//		}
//
//		@Override
//		public int getRowCount() {
//			return this.linhas.size();
//		}
//
//		@Override
//		public Object getValueAt(int rowIndex, int columnIndex) {
////			
////			switch(columnIndex){
////				case COL_BOTAO:
////					return "-";
////				case COL_CODIGO_CURSO:
////					
////					
////					COL_NOME_CURSO = 2;
////					private static final int COL_CODIGO_DISCIPLINA = 3;
////					private static final int COL_NOME_DISCIPLINA = 4;
////					private static final int COL_TOTAL_VAGAS = 5;
////					private static final int COL_PERIOTIZADOS = 6;
////					private static final int COL_OBSERVACOES = 7;
////					System.out.println("Coluna inválida");
//					return null;
////			}
//		}
//		
//		public void setValueAt(Object value, int rowIndex, int columnIndex){
////			Calouros calouro = linhas.get(rowIndex);
////			
////			switch(columnIndex){
////				case COL_ID:
////					int id = Integer.parseInt(value.toString());
////					calouro.setIdCalouro(id);
////					calourosDAO.salvaOuEdita(calouro);
////					break;
////				case COL_NUMERO_VAGAS:
////					int numeroVagas = Integer.parseInt(value.toString());
////					calouro.setNumVagas(numeroVagas);
////					calourosDAO.salvaOuEdita(calouro);
////					break;
////				default:
////					System.out.println("Coluna inválida");
////			}
//		}
//		
//		public Object getCalouro(int rowIndex){
//			return linhas.get(rowIndex);
//		}
//		
//		public void addCalouro(Calouros calouro){
//			linhas.add(calouro);
//			int ultimoIndice = getRowCount() - 1; 
//			fireTableRowsInserted(ultimoIndice, ultimoIndice);
//		}
//		
//		public void updateCalouro(int indiceLinha, Calouros calouro) { 
//			linhas.set(indiceLinha, calouro); 
//			fireTableRowsUpdated(indiceLinha, indiceLinha); 
//		}
//		
//		public void removeCalouro(int indiceLinha) { 
//			linhas.remove(indiceLinha); 
//			fireTableRowsDeleted(indiceLinha, indiceLinha); 
//		}
//
//		public ArrayList<Object> getLinhas() {
//			return linhas;
//		}
//
//		public void setLinhas(ArrayList<Object> linhas) {
//			this.linhas = linhas;
//		}
//		
//		@Override
//		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			return true;
//		}
//	}
//}
