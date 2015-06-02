package interfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TabExpander;

import org.hibernate.HibernateException;

import timetable.Docente;
import timetable.Turma;
import hibernate.DocenteDAO;
import hibernate.HibernateUtil;
import hibernate.TurmaDAO;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class ResultadosProfessorTableModel extends JPanel {

	private final boolean DEBUG = false;
	private JTable table;
	BotaoTabela botaoExpandir;
	MyTableModel tableModel;
	TurmaDAO turmaDAO;
	DocenteDAO docenteDAO;


	public ResultadosProfessorTableModel() {
		super(new GridLayout(1, 0));
		
		turmaDAO = new TurmaDAO();
		docenteDAO = new DocenteDAO();
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);

		// inicializa os professores da tabela
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
				
				List<?> turma = null;
				try {
					turma = turmaDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (table.getModel().getValueAt(table.getSelectedRow(), 0).equals("+")) {
					// caso o checkbox esteja marcado insere os dados extras dos professores

					ArrayList<String> disc = new ArrayList<String>();
					ArrayList<String> cod = new ArrayList<String>();
					ArrayList<Integer> cred = new ArrayList<Integer>();

					for (int j = 0; j < turma.size(); j++) { // para todas as turmas
						for (int k = 0; k < ((timetable.Turma) turma.get(j))
								.getDocente().size(); k++) { // para todos os professores
							// se existe doscente
							// busca se o professore referente a linha atual da tambela
							// possui o mesmo codigo que o professor que está no loop,
							// se sim adiciona sua turma
							if (!((timetable.Turma) turma.get(j)).getDocente().isEmpty()
									&& table.getModel().getValueAt(table.getSelectedRow(), 1)
											.equals(((timetable.Turma) turma.get(j))
													.getDocente().get(k).getCodigo())) {
								disc.add(((timetable.Turma) turma.get(j))
										.getDisciplina().getNome());
								cod.add(((timetable.Turma) turma.get(j))
										.getDisciplina().getNome()
										+ " - "
										+ ((timetable.Turma) turma.get(j)).getCodigo());
								cred.add(((timetable.Turma) turma.get(j))
										.getDisciplina().getCreditos());
							}
						}

					}

					if (!disc.isEmpty() || !cod.isEmpty() || !cred.isEmpty()) { // se houver dados no array de disciplina, codigo
																				// e creditação para aquela turma
						// adiciona a tabela os dados encontrados
						String[] sDisc = new String[disc.size()];
						for (int j = 0; j < disc.size(); j++) {
							sDisc[j] = disc.get(j);
						}

						String[] sCod = new String[cod.size()];
						for (int j = 0; j < cod.size(); j++) {
							sCod[j] = cod.get(j);
						}

						String[] sCred = new String[cred.size()];
						for (int j = 0; j < cred.size(); j++) {
							sCred[j] = cred.get(j).toString();
						}

						table.getModel().setValueAt(sDisc, table.getSelectedRow(), 3);
						table.getModel().setValueAt(sCod, table.getSelectedRow(), 4);
						table.getModel().setValueAt(sCred, table.getSelectedRow(), 5);

						disc.clear();
						cod.clear();
						cred.clear();
					}
					table.getModel().setValueAt("-", table.getSelectedRow(), 0);
				} else { // caso contrario os retira se estiverem na tabela
					String[] empty = { "" };
					table.getModel().setValueAt(empty, table.getSelectedRow(), 3);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 4);
					table.getModel().setValueAt(empty, table.getSelectedRow(), 5);
					table.getModel().setValueAt("+", table.getSelectedRow(), 0);
				}
				turma.clear();				
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

		public MyTableModel() {
			super(DEBUG, 7);
			
			columnNames[0] = "";
			columnNames[1] = "Código do Professor";
			columnNames[2] = "Nome do Professor";
			columnNames[3] = "Disicplinas";
			columnNames[4] = "Código da Disciplinas";
			columnNames[5] = "Creditos da Disciplina";
			columnNames[6] = "Créditos: Atuais / Esperados";
			
			data = new ArrayList<ArrayList<Object>>(); // row
		}

		public void loadTableValues(){			
			List<Docente> prof = null;
			try {
				prof = docenteDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Turma> turma = null;
			try {
				turma = turmaDAO.procuraTodos();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.clear();

			for (int i = 0; i < prof.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add("-");
				row.add(((timetable.Docente) prof.get(i)).getCodigo());
				row.add(((timetable.Docente) prof.get(i)).getNome());
				String[] disc = { " " };
				row.add(disc);
				String[] cod = { " " };
				row.add(cod);
				String[] credDisc = { " " };
				row.add(credDisc);
				int creditos = 0;
				for (int tur = 0; tur < turma.size(); tur++) { // calcula a quantidade de créditos de cada professor
					for (int doc = 0; doc < ((timetable.Turma) turma.get(tur))
							.getDocente().size(); doc++) {

						if (((timetable.Docente) prof.get(i)).getCodigo()
								.equals(((timetable.Turma) turma.get(tur))
										.getDocente().get(doc).getCodigo())) {

							creditos += ((timetable.Turma) turma.get(tur))
									.getDisciplina().getCreditos();

						}
					}
				}
				// define a exibição dos creditos como atuais / esperados
				String cred = Integer.toString(creditos)
						+ "  /  "
						+ Integer.toString(((timetable.Docente) prof.get(i))
								.getCreditacaoEsperada());

				row.add(cred);

				data.add(row);
			}
			
		}
		public void loadTable() {

			loadTableValues();
			
			/*é criado um novo table model*/
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
			/* We apply the model to the main jTable */
			table.setModel(modelo);
			/*
			 * We create a cell Renderer to display the data of the multivalue
			 * fields
			 */
			TableCellRenderer jTableCellRenderer = new TableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					/*
					 * se o que está sendo mostrado não for um vetor é retornado o valor original
					 */
					if (!value.getClass().isArray()) {
						return table.getDefaultRenderer(value.getClass())
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
					} else {
						final Object[] passed = (Object[]) value;
						/*
						 * é criado uma tabela pra mostrar os campos com varios valores
						 */
						JTable embedded = new JTable(new AbstractTableModel() {
							public int getColumnCount() {
								return 1;
							}

							public int getRowCount() {
								return passed.length;
							}

							public Object getValueAt(int rowIndex, int columnIndex) {
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
						/*
						 * If this is what you plan to enable mouseClick
						 * detection, in your table, IT WONT WORK. Have a look
						 * at TableCellEditor.
						 */
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
