package interfaces;

import interfaces.PlanoDepartamentalTableModel.MyTableModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import timetable.Turma;

public class PedidosCoordenadoresTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	TableCellRenderer linha;
	private static ArrayList<ArrayList<Object>> cor;
	
	public PedidosCoordenadoresTableModel() {
		super(new GridLayout(1, 0));
		
		cor = new ArrayList<ArrayList<Object>>();

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
	
	public static ArrayList<ArrayList<Object>> getCor() {
		return cor;
	}
	
	public void loadTableValues(){
		tableModel.loadTableValues();
	}
	
	public void loadDataTable(){
		tableModel.fireTableDataChanged();
	}

	class MyTableModel extends TableModel {

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			super(DEBUG, 6);
			
			columnNames[0] = "Semestre";
			columnNames[1] = "Codigo da Disciplina";
			columnNames[2] = "Disciplina";
			columnNames[3] = "Turma";
			columnNames[4] = "Horário";
			columnNames[5] = "Observações";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
		}
		
		public void loadTableValues(){
			
			ArrayList<timetable.Turma> _turma = (ArrayList<timetable.Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);
			ArrayList<timetable.Curso> _curso = (ArrayList<timetable.Curso>) hibernate.HibernateUtil.findAll(timetable.Curso.class);
			
			int colunasFixas = 6;			
			int columnNumber = colunasFixas;
						
			columnNumber = columnNumber + _curso.size();
			
			columnNames = new String[columnNumber];
			
			columnNames[0] = "Semestre";
			columnNames[1] = "Codigo da Disciplina";
			columnNames[2] = "Disciplina";
			columnNames[3] = "Turma";
			columnNames[4] = "Horário";
			columnNames[5] = "Observações";
			
			int colunCont = colunasFixas;
			for(Iterator<?> itCurso = _curso.iterator(); itCurso.hasNext();){
				timetable.Curso curso = ((timetable.Curso)itCurso.next());
				columnNames[colunCont] = curso.getCodigo() + " " + curso.getNome() + " - " + curso.getTurno();
				colunCont++;
			}
			
			_turma.sort(new Comparator<timetable.Turma>() {
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
			
			for(Iterator<?> itTurma = _turma.iterator(); itTurma.hasNext();linhaCont++){					
				timetable.Turma turma = ((timetable.Turma)itTurma.next());
				ArrayList<Object>line = new ArrayList<Object>();
				line.add("Semestre");
				line.add(turma.getDisciplina().getCodigo());
				line.add(turma.getDisciplina().getNome());
				line.add(turma.getCodigo());
				line.add("Horário");
				line.add("");

				for(int i=colunasFixas;i<columnNumber;i++)
					line.add("");
				data.add(line);
					
				ArrayList<Object> linha = new ArrayList<Object>();
				
				linha.add(linhaCont);
				linha.add(timetable.Disciplina.getOrSetCoresPerfis(turma.getDisciplina().getPerfil()));
				
				cor.add(linha);
			}			
		}
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	
}
