package interfaces;

import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import hibernate.HibernateUtil;
import interfaces.PedidosCoordenadoresTableModel.MyTableModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import timetable.Disciplina;
import Utilitarios.OperacoesInterface;

/**
*
* @author Héber
*/

public class CadastroPedidosTableModel extends JPanel{

	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	private BotaoTabela botaoExpandir;
	private DisciplinaDAO discDAO;
	private CursoDAO cursoDAO;
	private int column;
	
	
	public CadastroPedidosTableModel() {
		super(new GridLayout(1, 0));
		
		discDAO = new DisciplinaDAO();
		cursoDAO = new CursoDAO();
		
		column = 8; //numero de colunas
		tableModel = new MyTableModel(column);
		table = new JTable(tableModel);
		
		// inicializa as disciplinas
		tableModel.loadTable();

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);
		
		BotaoTabela botaoExpandir = new BotaoTabela(table, 0);
		botaoExpandir.getEditButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				
				List<Disciplina> disciplina = null;
				try {
					disciplina = discDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (table.getModel().getValueAt(table.getSelectedRow(), 0).equals("+")) {
					// caso o checkbox esteja marcado insere os dados das disciplinas

					// adiciona a tabela os dados encontrados
					int tamanhoDisc = disciplina.size();
					
					if(tamanhoDisc > 0){
						String[] tCod = new String[tamanhoDisc];
						for (int j = 0; j < tamanhoDisc; j++) {
							tCod[j] = disciplina.get(j).getCodigo();
						}
						
						String[] tDisc = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							tDisc[j] = disciplina.get(j).getNome();
						}
						
						String[] totVagas = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							totVagas[j] = "0";
						}
						
						String[] periotizados = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							periotizados[j] = "0/0";
						}
						
						table.setRowHeight(table.getSelectedRow(), tamanhoDisc*20);
						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tCod, false), table.getSelectedRow(), 3);
						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tDisc, false), table.getSelectedRow(), 4);
						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, totVagas, false), table.getSelectedRow(), 5);
						table.getModel().setValueAt(new CustomInnerTable(tamanhoDisc, periotizados, true), table.getSelectedRow(), 6);
						
						disciplina.clear();					
					}
					table.getModel().setValueAt("-", table.getSelectedRow(), 0);
				} else { // caso contrario os retira se estiverem na tabela
					String[] empty = { "" };
					table.setRowHeight(table.getSelectedRow(), 20);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 3);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 4);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 5);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 6);
					table.getModel().setValueAt("+", table.getSelectedRow(), 0);
				}
				
				disciplina.clear();
				
			}
		});
	}
		
	
	public void loadTableValues(){
		tableModel.loadTableValues();
		
	}
	public void updateTable(){
		for(int i=0;i<tableModel.getColumnCount();i++){
			for(int j=0;j<tableModel.getRowCount();j++){
				tableModel.fireTableCellUpdated(j, i);
				System.out.println(i+" "+j);
			}
		}
	}
	
	public void loadDataTable(){
		tableModel.fireTableDataChanged();
	}
	
	public void clearTable(){
		((AbstractTableModel)table.getModel()).fireTableRowsDeleted(0, tableModel.getRowCount()-1);
	}
	
	public void insertRowAfterUpdated(){
		((AbstractTableModel)table.getModel()).fireTableRowsInserted(0, tableModel.getRowCount()-1);
	}
	public JTable getTable() {
		return table;
	}
	
	public MyTableModel getNewTableModel(){
		return new MyTableModel(column);
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	class MyTableModel extends InterfaceTableModel {
		
		private int column;
		@SuppressWarnings("rawtypes")
		public MyTableModel(int column) {
			super(DEBUG, column);
			
			this.column = column;
			columnNames[0] = "";
			columnNames[1] = "Código do Curso";
			columnNames[2] = "Nome do Curso";
			columnNames[3] = "Código da Disciplina";
			columnNames[4] = "Nome da Disciplina";
			columnNames[5] = "Total de Vagas";
			columnNames[6] = "Periotizados/não Periotizados";
			columnNames[7] = "Observações";
			
			data = new ArrayList<ArrayList<Object>>(); // row
		}
		
		public void loadTableValues(){
			ArrayList<timetable.Curso> curso = null;
			try {
				curso = (ArrayList<timetable.Curso>) cursoDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.clear();
			
			int linhaCont = 0;
			
			for (int i = 0; i < curso.size(); i++,linhaCont++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add("-");
				row.add(((timetable.Curso) curso.get(i)).getCodigo());
				row.add(((timetable.Curso) curso.get(i)).getNome());
				String[] cod = { " " };
				row.add(cod);
				String[] disc = { " " };
				row.add(disc);
				String[] totVagas = { " " };
				row.add(totVagas);
				String[] periotizados = { " " };
				row.add(periotizados);
				row.add("");
				
				data.add(row);
			}			
		}
		
		public void loadTable() {

			loadTableValues();
			
			/*é criado um novo table model*/
			AbstractTableModel modelo = new InterfaceTableModel(DEBUG, tableModel.getColumnNames(), tableModel.getData()) {};
			
			/* é aplicado o modelo na tabela original */
			table.setModel(modelo);
			/* é aplicado o novo cellrenderer a tabela */
			TableColumnModel tcm = table.getColumnModel();
			for (int it = 3; it < 7; it++) {
				tcm.getColumn(it).setCellRenderer(new CustInnerRenderer());
				tcm.getColumn(it).setCellEditor(new CustInnerCellEditor());
			}
		}
	}
}
