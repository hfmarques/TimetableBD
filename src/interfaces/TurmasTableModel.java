package interfaces;

import hibernate.HibernateUtil;
import hibernate.TurmaDAO;
import interfaces.DisciplinaTableModel.MyTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.hibernate.HibernateException;

import timetable.Turma;
import Utilitarios.OperacoesInterface;

public class TurmasTableModel extends JPanel{

	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	TurmaDAO turmaDAO;

	public TurmasTableModel() {
		super(new GridLayout(1, 0));
		turmaDAO = new TurmaDAO();
		
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
			super(DEBUG, 6);
			
			columnNames[0] = "Código";
			columnNames[1] = "Turno";
			columnNames[2] = "Máximo de Vagas";
			columnNames[3] = "Nome da Disciplina";
			columnNames[4] = "Codigo da Disciplina";
			columnNames[5] = "Sala";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			// col
			loadTableValues();
		}
		
		public void loadTableValues(){
			List<Turma> lista = null;
			try {
				lista = turmaDAO.procuraTodos();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			data.clear();
			
			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(((timetable.Turma) lista.get(i)).getCodigo());
				row.add(((timetable.Turma) lista.get(i)).getTurno());
				row.add(((Integer)((timetable.Turma) lista.get(i)).getMaxVagas()).toString());
				row.add(((timetable.Turma) lista.get(i)).getDisciplina().getNome());
				row.add(((timetable.Turma) lista.get(i)).getDisciplina().getCodigo());
				row.add(((timetable.Turma) lista.get(i)).getSala().getNumero());
				data.add(row);
			}

		}
	}


}
