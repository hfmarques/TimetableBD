package interfaces;

import hibernate.DocenteDAO;
import hibernate.HibernateUtil;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import timetable.Docente;
import Utilitarios.OperacoesInterface;

@SuppressWarnings("serial")
public class ProfessorTableModel extends JPanel /* extends AbstractTableModel */{
	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	DocenteDAO docenteDAO;
	
	public ProfessorTableModel() {
		super(new GridLayout(1, 0));
		docenteDAO = new DocenteDAO();
		
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
			super(DEBUG, 3);
			
			columnNames[0] = "C�digo";
			columnNames[1] = "Nome Completo";
			columnNames[2] = "Credita��o Esperada";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			// col
			loadTableValues();
		}
		
		public void loadTableValues(){

			List<Docente> lista = null;
			try {
				lista = docenteDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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