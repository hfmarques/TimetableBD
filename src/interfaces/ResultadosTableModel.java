package interfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import hibernate.HibernateUtil;

/**
 *
 * @author H�ber
 */
@SuppressWarnings("serial")
public class ResultadosTableModel extends JPanel {

	private final boolean DEBUG = false;
	private JTable table;
	BotaoTabela botaoExpandir;

	public ResultadosTableModel() {
		super(new GridLayout(1, 0));

		MyTableModel tableModel = new MyTableModel();
		table = new JTable(tableModel);

		// inicializa os professores da tabela
		tableModel.loadTableValues();

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);

		@SuppressWarnings("unused")
		BotaoTabela botaoExpandir = new BotaoTabela(table, 0);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	class MyTableModel extends AbstractTableModel {

		private String[] columnNames = { "", "C�digo do Professor",
				"Nome do Professor", "Disciplinas", "Codigo da Disciplina",
				"Creditos da Disciplina", "Cr�ditos: Atuais / Esperados" };

		private ArrayList<ArrayList<Object>> data;

		public MyTableModel() {
			data = new ArrayList<ArrayList<Object>>(); // row
		}

		public void loadTableValues() {

			List<?> lista = HibernateUtil.findAll(timetable.Docente.class);
			List<?> turma = HibernateUtil.findTurmas();

			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add("-");
				row.add(((timetable.Docente) lista.get(i)).getCodigo());
				row.add(((timetable.Docente) lista.get(i)).getNome());
				String[] disc = { " " };
				row.add(disc);
				String[] cod = { " " };
				row.add(cod);
				String[] credDisc = { " " };
				row.add(credDisc);
				int creditos = 0;
				for (int tur = 0; tur < turma.size(); tur++) { // calcula a
																// quantidade de
																// cr�ditos de
																// cada
																// professor
					for (int doc = 0; doc < ((timetable.Turma) turma.get(tur))
							.getDocente().size(); doc++) {

						if (((timetable.Docente) lista.get(i)).getCodigo()
								.equals(((timetable.Turma) turma.get(tur))
										.getDocente().get(doc).getCodigo())) {

							creditos += ((timetable.Turma) turma.get(tur))
									.getDisciplina().getCreditos();

						}
					}
				}
				// define a exibi��o dos creditos como atuais / esperados
				String cred = Integer.toString(creditos)
						+ "  /  "
						+ Integer.toString(((timetable.Docente) lista.get(i))
								.getCreditacaoEsperada());

				row.add(cred);

				data.add(row);
			}
			/* Next we create our table models */

			/*
			 * First we create the main model We overide the AbstractTableModel
			 * necessary methods
			 */
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
				/* Magic Happens */
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					/*
					 * If what we're displaying isn't an array of values we
					 * return the normal renderer
					 */
					if (!value.getClass().isArray()) {
						return table.getDefaultRenderer(value.getClass())
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
					} else {
						final Object[] passed = (Object[]) value;
						/*
						 * We create the table that will hold the multivalue
						 * fields and that will be embedded in the main table
						 */
						JTable embedded = new JTable(new AbstractTableModel() {
							public int getColumnCount() {
								return 1;
							}

							public int getRowCount() {
								return passed.length;
							}

							public Object getValueAt(int rowIndex,
									int columnIndex) {
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
							embedded.setBackground(table
									.getSelectionBackground());
							embedded.setForeground(table
									.getSelectionForeground());
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

		public String[] getColumnNames() {
			return columnNames;
		}

		public void setColumnNames(String[] columnNames) {
			this.columnNames = columnNames;
		}

		public ArrayList<ArrayList<Object>> getData() {
			return data;
		}

		public void addRow(ArrayList<Object> row) {
			data.add(row);
		}

		public void setData(ArrayList<ArrayList<Object>> data) {
			this.data = data;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data.get(row).get(col);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value + " (an instance of "
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
	}
}
