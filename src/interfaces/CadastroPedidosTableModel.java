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
* @author H�ber
*/

public class CadastroPedidosTableModel extends JPanel{

	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	BotaoTabela botaoExpandir;
	DisciplinaDAO discDAO;
	CursoDAO cursoDAO;
	
	
	public CadastroPedidosTableModel() {
		super(new GridLayout(1, 0));
		
		discDAO = new DisciplinaDAO();
		cursoDAO = new CursoDAO();
		
		tableModel = new MyTableModel();
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
						
						table.getModel().setValueAt(tCod, table.getSelectedRow(), 3);
						table.getModel().setValueAt(tDisc, table.getSelectedRow(), 4);
						table.getModel().setValueAt(totVagas, table.getSelectedRow(), 5);
						table.getModel().setValueAt(periotizados, table.getSelectedRow(), 6);
						
						disciplina.clear();					
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
		return new MyTableModel();
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	class MyTableModel extends InterfaceTableModel {

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			super(DEBUG, 8);
			
			columnNames[0] = "";
			columnNames[1] = "C�digo do Curso";
			columnNames[2] = "Nome do Curso";
			columnNames[3] = "C�digo da Disciplina";
			columnNames[4] = "Nome da Disciplina";
			columnNames[5] = "Total de Vagas";
			columnNames[6] = "Periotizados/n�o Periotizados";
			columnNames[7] = "Observa��es";
			
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
			
			
			for (int i = 0; i < curso.size(); i++) {
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
			
			/*� criado um novo table model*/
			AbstractTableModel modelo = new AbstractTableModel() {
				public String getColumnName(int col) {
					return columnNames[col].toString();
				}

				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int col) {
					if (getRowCount() < 1) {
						return null;
					}
					return data.get(0).get(col).getClass();
				}

				public int getRowCount() {
					return data.size();
				}

				public int getColumnCount() {
					return columnNames.length;
				}

				public Object getValueAt(int row, int col) {
					return data.get(row).get(col);
				}

				public boolean isCellEditable(int row, int col) {
					return true;
				}

				public void setValueAt(Object value, int row, int col) {
					if (DEBUG) {
						System.out.println("Setting value at " + row + ","
								+ col + " to " + value + " (an instance of "
								+ value.getClass() + ")");
					}

					data.get(row).set(col, value);
					fireTableCellUpdated(row, col);

					if (DEBUG) {
						System.out.println("New value of data:");
						printDebugData();
					}
				}

				private void printDebugData() {
					int numRows = getRowCount();
					int numCols = getColumnCount();

					for (int i = 0; i < numRows; i++) {
						System.out.print("    row " + i + ":");
						for (int j = 0; j < numCols; j++) {
							System.out.print("  " + data.get(i).get(j));
						}
						System.out.println();
					}
					System.out.println("--------------------------");
				}

			};
			/* � aplicado o modelo na tabela original */
			table.setModel(modelo);
			/*
			 * este cell render mostra os dados nos campos com mais de um valor
			 */
			TableCellRenderer jTableCellRenderer = new TableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					/*
					 * se o que est� sendo mostrado n�o for um vetor � retornado o valor original
					 */
					if (!value.getClass().isArray()) {
						return table.getDefaultRenderer(value.getClass())
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
					} else {
						final Object[] passed = (Object[]) value;
						/*
						 * � criado uma tabela pra mostrar os campos com varios valores
						 */
						JTable embedded = new JTable(new AbstractTableModel() {
							public int getColumnCount() {
								return 1;
							}

							public int getRowCount() {
								return passed.length;
							}

							public Object getValueAt(int rowIndex,	int columnIndex) {
								return passed[rowIndex];
							}

							public boolean isCellEditable(int row, int col) {
								if (col < 0) {
									return false;
								} else {
									return true;
								}
							}

							@SuppressWarnings("unused")
							public String[] getColumnNames() {
								return columnNames;
							}

							@SuppressWarnings("unused")
							public ArrayList<ArrayList<Object>> getData() {
								return data;
							}

							@SuppressWarnings("unused")
							public void addRow(ArrayList<Object> row) {
								data.add(row);
							}

							@Override
							public String getColumnName(int col) {
								return columnNames[col];
							}

							@SuppressWarnings({ "unchecked", "rawtypes" })
							@Override
							public Class getColumnClass(int c) {
								return getValueAt(0, c).getClass();
							}

							@Override
							public void setValueAt(Object value, int row,
									int col) {
								if (DEBUG) {
									System.out.println("Setting value at "
											+ row + "," + col + " to " + value
											+ " (an instance of "
											+ value.getClass() + ")");
								}

								data.get(row).set(col, value);
								fireTableCellUpdated(row, col);

								if (DEBUG) {
									System.out.println("New value of data:");
									printDebugData();
								}
							}

							private void printDebugData() {
								int numRows = getRowCount();
								int numCols = getColumnCount();

								for (int i = 0; i < numRows; i++) {
									System.out.print("    row " + i + ":");
									for (int j = 0; j < numCols; j++) {
										System.out.print("  "
												+ data.get(i).get(j));
									}
									System.out.println();
								}
								System.out
										.println("--------------------------");
							}
						});
						
						if (isSelected) {
							embedded.setBackground(table.getSelectionBackground());
							embedded.setForeground(table.getSelectionForeground());
						}
						if (hasFocus) {
							embedded.setRowSelectionInterval(0, 1);
						}
						embedded.addMouseListener(new MouseAdapter() {
							public void mouseClicked(
									java.awt.event.MouseEvent evt) {
								System.out.println("PEPE");
							}
						});

						setPreferredSize(embedded.getPreferredSize());
						if (getPreferredSize().height != table
								.getRowHeight(row)) {
							table.setRowHeight(row, getPreferredSize().height);
						}
						
						embedded.setCellEditor(new CellEditor());

						return embedded;
					}
				}
			};
			/* Finally we apply the new cellRenderer to the whole table */
			TableColumnModel tcm = table.getColumnModel();
			for (int it = 0; it < tcm.getColumnCount(); it++) {
				tcm.getColumn(it).setCellRenderer(jTableCellRenderer);
				// tcm.getColumn(it).setCellEditor(new CellEditor());
			}

			/*
			 * Note: if we need to edit the values inside the embedded jtable we
			 * will need to create a TableCellEditor too.
			 */
		}
	}
}
