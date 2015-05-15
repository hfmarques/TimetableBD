	package interfaces;

import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
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

import Utilitarios.OperacoesInterface;
import timetable.Turma;

public class PedidosCoordenadoresTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	TableCellRenderer linha;
	private static ArrayList<ArrayList<Object>> cor;
	DisciplinaDAO discDAO;
	CursoDAO cursoDAO;
	
	public PedidosCoordenadoresTableModel() {
		super(new GridLayout(1, 0));
		discDAO = new DisciplinaDAO();
		cursoDAO = new CursoDAO();
		
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

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			super(DEBUG, 6);
			
			columnNames[0] = "Semestre";
			columnNames[1] = "Codigo da Disciplina";
			columnNames[2] = "Disciplina";
			columnNames[3] = "Observações";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
		}
		
		public void loadTableValues(){
			
			ArrayList<timetable.Disciplina> _disciplina = null;
			try {
				_disciplina = (ArrayList<timetable.Disciplina>) discDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<timetable.Curso> _curso = null;
			try {
				_curso = (ArrayList<timetable.Curso>) cursoDAO.procuraTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int colunasFixas = 4;			
			int columnNumber = colunasFixas;
						
			columnNumber = columnNumber + _curso.size();
			
			columnNames = new String[columnNumber];
			
			columnNames[0] = "Semestre";
			columnNames[1] = "Codigo da Disciplina";
			columnNames[2] = "Disciplina";
			columnNames[3] = "Observações";
			
			int colunCont = colunasFixas;
			for(Iterator<?> itCurso = _curso.iterator(); itCurso.hasNext();){
				timetable.Curso curso = ((timetable.Curso)itCurso.next());
				columnNames[colunCont] = curso.getCodigo() + " " + curso.getNome() + " - " + curso.getTurno();
				colunCont++;
			}
			
			_disciplina.sort(new Comparator<timetable.Disciplina>() {
				@Override
				public int compare(timetable.Disciplina o1,	timetable.Disciplina o2) {
					if(o1.getPerfil().equals(o2.getPerfil())){
						return o1.getNome().compareTo(o2.getNome());						
					}						
					return o1.getPerfil().compareTo(o2.getPerfil());
				}
			});
			
			int linhaCont = 0;
			
			data.clear();
			
			for(Iterator<?> itTurma = _disciplina.iterator(); itTurma.hasNext();linhaCont++){					
				timetable.Disciplina disciplina = ((timetable.Disciplina)itTurma.next());
				ArrayList<Object>line = new ArrayList<Object>();
				line.add("Semestre");
				line.add(disciplina.getCodigo());
				line.add(disciplina.getNome());
				line.add("-");

				for(int i=colunasFixas;i<columnNumber;i++)
					line.add("-");
				data.add(line);
					
				ArrayList<Object> linha = new ArrayList<Object>();
				
				linha.add(linhaCont);
				linha.add(timetable.Disciplina.getOrSetCoresPerfis(disciplina.getPerfil()));
				
				cor.add(linha);
			}			
		}
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	
}
