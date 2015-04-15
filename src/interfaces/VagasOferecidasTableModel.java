package interfaces;

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

public class VagasOferecidasTableModel extends JPanel {
	private final boolean DEBUG = false;
	private JTable table;
	MyTableModel tableModel;
	
	public VagasOferecidasTableModel() {
		super(new GridLayout(1, 0));

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
			super(DEBUG, 11);
			
			columnNames[0] = "Código do Curso";
			columnNames[1] = "Nome do Curso";
			columnNames[2] = "Semestre";
			columnNames[3] = "Código da Disciplina";
			columnNames[4] = "Disciplina";
			columnNames[5] = "Turma";
			columnNames[6] = "Horário";
			columnNames[7] = "Turnos";
			columnNames[8] = "Total de Vagas";
			columnNames[9] = "Vagas Periotizados: Oferecidas/Solicitadas";
			columnNames[10] = "Vagas Desperiotizados: Oferecidas/Solicitadas";
			
			data = new ArrayList<ArrayList<Object>>(); // row
			loadTableValues();
		}
		
		public void loadTableValues(){			
			ArrayList<timetable.Turma> _turma = (ArrayList<timetable.Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);
			ArrayList<timetable.Curso> _curso = (ArrayList<timetable.Curso>) hibernate.HibernateUtil.findAll(timetable.Curso.class);
			
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
					line.add("Horário");
					line.add(curso.getTurno());
					line.add("");
					line.add("");
					line.add("");
					data.add(line);
				}
			}			
		}
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	
}
