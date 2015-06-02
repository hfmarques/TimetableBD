package interfaces;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import timetable.Calouros;
import Utilitarios.OperacoesInterface;
import hibernate.CalourosDAO;
import hibernate.CursoDAO;
import hibernate.HibernateUtil;

/**
 *
 * @author H�ber
 */
@SuppressWarnings("serial")
public class CursoTableModel extends JPanel /* extends AbstractTableModel */{
	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	private CursoDAO cursoDAO;

	public CursoTableModel() {
		super(new GridLayout(1, 0));
		cursoDAO = new CursoDAO();
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);
		
		OperacoesInterface.dimensionaTabela(table);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void loadTableValues(){
		tableModel.loadTableValues();
	}
	
	public void loadDataTable(){
		tableModel.fireTableDataChanged();
	}

	class MyTableModel extends InterfaceTableModel {

		public MyTableModel() {
			super(DEBUG, 5); // 5 = numero de colunas
			columnNames[0] = "Nome";
			columnNames[1] = "C�digo";
			columnNames[2] = "Turno";
			columnNames[3] = "Calouros Primeiro Semestre";
			columnNames[4] = "Calouros Segundo Semestre";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			// col
			loadTableValues();
			
		}
		
		public void loadTableValues(){

			List<timetable.Curso> lista = null;
			try {
				lista = cursoDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.clear();
			
			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				
				row.add(((timetable.Curso) lista.get(i)).getNome());
				row.add(((timetable.Curso) lista.get(i)).getCodigo());
				row.add(((timetable.Curso) lista.get(i)).getTurno());
				row.add(((timetable.Curso) lista.get(i)).getCalouros().get(0).getNumVagas());
				row.add(((timetable.Curso) lista.get(i)).getCalouros().get(1).getNumVagas());
				data.add(row);				
			}
		}
	}
}
