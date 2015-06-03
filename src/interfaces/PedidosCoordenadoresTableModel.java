	package interfaces;

import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import interfaces.PlanoDepartamentalTableModel.MyTableModel;

import java.awt.Color;
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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Utilitarios.OperacoesInterface;
import timetable.Disciplina;
import timetable.Turma;

public class PedidosCoordenadoresTableModel extends JPanel {
	
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	BotaoTabela botaoExpandir;
	DisciplinaDAO discDAO;
	CursoDAO cursoDAO;
	private int column;
	
	public PedidosCoordenadoresTableModel() {
		super(new GridLayout(1, 0));
		discDAO = new DisciplinaDAO();
		cursoDAO = new CursoDAO();
		
		column = 8; //numero de colunas
		tableModel = new MyTableModel(column);
		table = new JTable(tableModel);
		
		// inicializa os cursos
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
				
				List<timetable.Curso> curso = null;
				try {
					curso = cursoDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (table.getModel().getValueAt(table.getSelectedRow(), 0).equals("+")) {
					// caso o checkbox esteja marcado insere os dados das disciplinas

					// adiciona a tabela os dados encontrados
					int tamanhoDisc = curso.size();
					
					if(tamanhoDisc > 0){
						String[] tCod = new String[tamanhoDisc];
						for (int j = 0; j < tamanhoDisc; j++) {
							tCod[j] = curso.get(j).getCodigo();
						}
						
						String[] tDisc = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							tDisc[j] = curso.get(j).getNome();
						}
						
						String[] totVagas = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							totVagas[j] = "0";
						}
						
						String[] periotizados = new String[tamanhoDisc];
						for (int j = 0; j <tamanhoDisc; j++) {
							periotizados[j] = "0/0";
						}
						
						table.getModel().setValueAt(tCod, table.getSelectedRow(), 3);
						table.getModel().setValueAt(tDisc, table.getSelectedRow(), 4);
						table.getModel().setValueAt(totVagas, table.getSelectedRow(), 5);
						table.getModel().setValueAt(periotizados, table.getSelectedRow(), 6);
						
						curso.clear();					
					}
					table.getModel().setValueAt("-", table.getSelectedRow(), 0);
				} else { // caso contrario os retira se estiverem na tabela
					String[] empty = { "" };
					table.getModel().setValueAt(empty, table.getSelectedRow(), 3);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 4);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 5);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 6);
					table.getModel().setValueAt("+", table.getSelectedRow(), 0);
				}
				
				curso.clear();
				
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
			columnNames[1] = "Código da Disciplina";
			columnNames[2] = "Nome da Disciplina";
			columnNames[3] = "Código do Curso";
			columnNames[4] = "Nome do Curso";
			columnNames[5] = "Total de Vagas";
			columnNames[6] = "Periotizados/não Periotizados";
			columnNames[7] = "Observações";
			
			data = new ArrayList<ArrayList<Object>>(); // row
		}
		
		public void loadTableValues(){
			ArrayList<timetable.Disciplina> disciplina = null;
			try {
				disciplina = (ArrayList<timetable.Disciplina>) discDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.clear();
			
			int linhaCont = 0;
			
			for (int i = 0; i < disciplina.size(); i++, linhaCont++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add("+");
				row.add(((timetable.Disciplina) disciplina.get(i)).getCodigo());
				row.add(((timetable.Disciplina) disciplina.get(i)).getNome());
				String[] cod = { " " };
				row.add(cod);
				String[] disc = { " " };
				row.add(disc);
				String[] totVagas = { " " };
				row.add(totVagas);
				String[] periotizados = { " " };
				row.add(periotizados);
				row.add("");
				
			}			
		}
		
		public void loadTable() {

			loadTableValues();
			
			/*é criado um novo table model*/
			AbstractTableModel modelo = new InterfaceTableModel(DEBUG, tableModel.getColumnNames(), tableModel.getData()) {};
			
			/* é aplicado o modelo na tabela original */
			table.setModel(modelo);
			/*
			 * este cell render mostra os dados nos campos com mais de um valor
			 */
			TableCellRenderer jTableCellRenderer = new CustomTableCellRenderer(DEBUG, column) {};
			/* é aplicado o novo cellrenderer a tabela */
			TableColumnModel tcm = table.getColumnModel();
			for (int it = 0; it < tcm.getColumnCount(); it++) {
				tcm.getColumn(it).setCellRenderer(jTableCellRenderer);
				// tcm.getColumn(it).setCellEditor(new CellEditor());
			}
		}
	}	
}
