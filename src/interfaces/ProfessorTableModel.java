package interfaces;

import hibernate.HibernateUtil;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ProfessorTableModel extends JPanel /* extends AbstractTableModel */{
	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;

	public ProfessorTableModel() {
		super(new GridLayout(1, 0));

		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Integer.class, new CellEditor());
		add(scrollPane);
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

	class MyTableModel extends TableModel {

		public MyTableModel() {
			super(DEBUG, 3);
			
			columnNames[0] = "Código";
			columnNames[1] = "Nome Completo";
			columnNames[2] = "Creditação Esperada";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			// col
			loadTableValues();
		}
		
		public void loadTableValues(){

			List<?> lista = HibernateUtil.findAll(timetable.Docente.class);
			
			data.clear();
			
			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(((timetable.Docente) lista.get(i)).getCodigo());
				row.add(((timetable.Docente) lista.get(i)).getNomeCompleto());
				row.add(((timetable.Docente) lista.get(i)).getCreditacaoEsperada());
				data.add(row);
			}

		}
	}

}