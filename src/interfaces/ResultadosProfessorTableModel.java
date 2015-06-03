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
	private BotaoTabela botaoExpandir;
	private MyTableModel tableModel;
	private TurmaDAO turmaDAO;
	private DocenteDAO docenteDAO;
	private int column;

	public ResultadosProfessorTableModel() {
		super(new GridLayout(1, 0));
		
		turmaDAO = new TurmaDAO();
		docenteDAO = new DocenteDAO();
		
		column = 7; // numero de colunas
		tableModel = new MyTableModel(column);
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
		public MyTableModel(int column) {
			super(DEBUG, column);
			
			this.column = column;
			
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
