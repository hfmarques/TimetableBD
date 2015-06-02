package interfaces;

import hibernate.TurmaDAO;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.hibernate.HibernateException;

import Utilitarios.OperacoesInterface;
import timetable.Turma;

@SuppressWarnings("serial")
public class PlanoDepartamentalTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	private MyTableModel tableModel;
	private static ArrayList<ArrayList<Object>> cor;
	private TurmaDAO turmaDAO;
	
	public PlanoDepartamentalTableModel() {
		super(new GridLayout(1, 0));
		turmaDAO = new TurmaDAO();
		
		cor = new ArrayList<ArrayList<Object>>();
		
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

	public static ArrayList<ArrayList<Object>> getCor() {
		return cor;
	}
	
	public void loadTableValues(){
		tableModel.loadTableValues();
	}
	
	public void loadDataTable(){
		tableModel.fireTableDataChanged();
	}

	class MyTableModel extends InterfaceTableModel {

		public MyTableModel() {
			super(DEBUG, 8);
			
			columnNames[0] = "Semestre";
			columnNames[1] = "Código Disciplinao";
			columnNames[2] = "Disciplina";
			columnNames[3] = "Turma";
			columnNames[4] = "Horário";
			columnNames[5] = "Créditos";
			columnNames[6] = "Docente";
			columnNames[7] = "Vaga";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
		}
		
		public void loadTableValues(){			
			ArrayList<timetable.Turma> turma = null;
			try {
				turma = (ArrayList<timetable.Turma>) turmaDAO.procuraTodos();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			turma.sort(new Comparator<timetable.Turma>() {
				@Override
				public int compare(Turma o1, Turma o2) {
					if(o1.getDisciplina().getPerfil().equals(o2.getDisciplina().getPerfil())){
						if(o1.getDisciplina().getNome().equals(o2.getDisciplina().getNome())){
							return o1.getCodigo().compareTo(o2.getCodigo());
						}
						return o1.getDisciplina().getNome().compareTo(o2.getDisciplina().getNome());						
					}						
					return o1.getDisciplina().getPerfil().compareTo(o2.getDisciplina().getPerfil());
				}
			});
			
			int linhaCont = 0;
			
			data.clear();
			
			for(Iterator<?> it = turma.iterator(); it.hasNext(); linhaCont++){
				ArrayList<Object>line = new ArrayList<Object>();
				timetable.Turma tur = ((timetable.Turma)it.next());
				line.add("Semestre");
				line.add(tur.getDisciplina().getCodigo());
				line.add(tur.getDisciplina().getNome());
				line.add(tur.getCodigo());
				line.add("Horário");
				line.add(tur.getDisciplina().getCreditos());
				line.add("Clique para escolher o Docente");
				line.add("Vaga");
				data.add(line);
				
				ArrayList<Object> linha = new ArrayList<Object>();
				
				linha.add(linhaCont);
				linha.add(timetable.Disciplina.getOrSetCoresPerfis(tur.getDisciplina().getPerfil()));
				
				cor.add(linha);
			}			
		}
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}	
}
