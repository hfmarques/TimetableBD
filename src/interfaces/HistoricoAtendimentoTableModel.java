package interfaces;

import hibernate.CursoDAO;
import hibernate.TurmaDAO;
import interfaces.PedidosCoordenadoresTableModel.MyTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Utilitarios.OperacoesInterface;

public class HistoricoAtendimentoTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	TurmaDAO turmaDAO;
	CursoDAO cursoDAO;
	
	public HistoricoAtendimentoTableModel() {
		super(new GridLayout(1, 0));
		turmaDAO = new TurmaDAO();
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

		@SuppressWarnings("rawtypes")
		public MyTableModel() {
			super(DEBUG, 9);
			
			columnNames[0] = "Código do Curso";
			columnNames[1] = "Nome do Curso";
			columnNames[2] = "Semestre";
			columnNames[3] = "Código da Disciplina";
			columnNames[4] = "Disciplina";
			columnNames[5] = "Turma";
			columnNames[6] = "Periotizados Solicitados/Atendidos";
			columnNames[7] = "Desperiotizados Solicitados/Atendidos";
			columnNames[8] = "Total: Solicitado/Atendido/Matriculado";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
		}
		
		public void loadTableValues(){		
			
			ArrayList<timetable.Turma> _turma = null;
			try {
				_turma = (ArrayList<timetable.Turma>) turmaDAO.procuraTodos();
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
			
			data.clear();
			
			for(Iterator<?> itCurso = _curso.iterator(); itCurso.hasNext();){
				timetable.Curso curso = ((timetable.Curso)itCurso.next());
				for(Iterator<?> itTurma = _turma.iterator(); itTurma.hasNext();){					
					timetable.Turma turma = ((timetable.Turma)itTurma.next());
					ArrayList<Object>line = new ArrayList<Object>();
					line.add(curso.getCodigo());
					line.add(curso.getNome());
					line.add("Semestre");
					line.add(turma.getDisciplina().getCodigo());
					line.add(turma.getDisciplina().getNome());
					line.add(turma.getCodigo());
					line.add("-");
					line.add("-");
					line.add("-");
					data.add(line);
				}
			}			
		}

	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	
}
