package interfaces;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import Utilitarios.OperacoesInterface;
import hibernate.CalourosDAO;
import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class CalourosTableModel extends JPanel {

	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	CalourosDAO calourosDAO;

	public CalourosTableModel() {
		super(new GridLayout(1, 0));
		
		calourosDAO = new CalourosDAO();

		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);
		
		Utilitarios.OperacoesInterface.dimensionaTabela(table);
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

	class MyTableModel extends interfaces.InterfaceTableModel {

		public MyTableModel() {
			super(DEBUG, 2);
			
			columnNames[0] = "ID";
			columnNames[1] = "Numero de Vagas";
			
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
			
		}
		
		public void loadTableValues(){
			// col

			List<timetable.Calouros> lista = null;
			try {
				lista = calourosDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.clear();

			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(Integer.toString(((timetable.Calouros) lista.get(i))
						.getIdCalouro()));
				row.add(Integer.toString(((timetable.Calouros) lista.get(i))
						.getNumVagas()));
				data.add(row);
			}

		}
	}

}
