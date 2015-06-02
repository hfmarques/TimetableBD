package interfaces;

import hibernate.CursoDAO;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import timetable.Curso;
import timetable.Disciplina;
import timetable.Docente;
import timetable.Turma;

public class PedidosCoordenadores extends InterfacesTabela{
	
	private CursoDAO cursoDAO;
	
	public PedidosCoordenadores() {
		super(new PedidosCoordenadoresTableModel(), "Salvar");
		
		cursoDAO = new CursoDAO();
		
		//gera os eventos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
	}
	
	public void inicializaCursos(){
		List<Curso> curso = null;
		try {
			curso = (List<timetable.Curso>) cursoDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < ((PedidosCoordenadoresTableModel) tabela).getTable().getModel().getRowCount(); i++) { // procura em todas as linhas da tabela
			if (((PedidosCoordenadoresTableModel) tabela).getTable().getModel().getValueAt(i, 0).equals("-")) { // caso o checkbox esteja marcado insere os dados extras dos professores

			// caso o checkbox esteja marcado insere os dados das disciplinas

			// adiciona a tabela os dados encontrados
			int tamanhoCurso = curso.size();
			String[] tCurso = new String[tamanhoCurso];
			for (int j = 0; j <tamanhoCurso; j++) {
				tCurso[j] = curso.get(j).getNome();
			}

			String[] tCod = new String[tamanhoCurso];
			for (int j = 0; j < tamanhoCurso; j++) {
				tCod[j] = curso.get(j).getCodigo();
			}
			
			String[] totVagas = new String[tamanhoCurso];
			for (int j = 0; j <tamanhoCurso; j++) {
				totVagas[j] = "0";
			}
			
			String[] periotizados = new String[tamanhoCurso];
			for (int j = 0; j <tamanhoCurso; j++) {
				periotizados[j] = "0/0";
			}
			
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(tCod, i, 3);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(tCurso, i, 4);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(totVagas,i, 5);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(periotizados, i, 6);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt("-", i, 0);
			
		} else { // caso contrario os retira se estiverem na tabela
			String[] empty = { "" };
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(empty, i, 3);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(empty, i, 4);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(empty, i, 5);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt(empty, i, 6);
			((PedidosCoordenadoresTableModel) tabela).getTable().getModel().setValueAt("+", i, 0);
		}
	}
		
		curso.clear();
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}
}
