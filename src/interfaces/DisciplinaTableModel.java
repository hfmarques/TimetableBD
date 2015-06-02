package interfaces;

import hibernate.DisciplinaDAO;
import hibernate.HibernateUtil;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import timetable.Disciplina;
import Utilitarios.OperacoesInterface;

@SuppressWarnings("serial")
public class DisciplinaTableModel extends JPanel /* extends AbstractTableModel */{
	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	DisciplinaDAO discDAO;

	public DisciplinaTableModel() {
		super(new GridLayout(1, 0));
		discDAO = new DisciplinaDAO();
		
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

	class MyTableModel extends interfaces.InterfaceTableModel {

		public MyTableModel() {
			
			super(DEBUG, 4);
			
			columnNames[0] = "Código";
			columnNames[1] = "Crédito";
			columnNames[2] = "Nome";
			columnNames[3] = "Perfil";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			// col
			loadTableValues();
		}
		
		public void loadTableValues(){

			List<Disciplina> lista = null;
			try {
				lista = discDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.clear();

			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(((timetable.Disciplina) lista.get(i)).getCodigo());
				row.add(((Integer)((timetable.Disciplina) lista.get(i)).getCreditos()).toString());
				row.add(((timetable.Disciplina) lista.get(i)).getNome());
				row.add(((timetable.Disciplina) lista.get(i)).getPerfil());
				data.add(row);
			}

		}
	}

}
