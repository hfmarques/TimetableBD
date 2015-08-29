package interfaces;

import hibernate.DisciplinaDAO;
import hibernate.GenericoDAO;
import hibernate.HibernateUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import timetable.Disciplina;

public class CadastroPedidos extends InterfacesTabela {
	
	private DisciplinaDAO discDao;

	public CadastroPedidos() {
		super(new CadastroPedidosTableModel(), "Salvar");
		
		discDao = new DisciplinaDAO();
		
		inicializaDisciplinas();
		//gera os eventos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
	}
	
	public void inicializaDisciplinas(){
		List<Disciplina> disciplina = null;
		try {
			disciplina = (List<Disciplina>) discDao.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < ((CadastroPedidosTableModel) tabela).getTable().getModel().getRowCount(); i++) { // procura em todas as linhas da tabela
			if (((CadastroPedidosTableModel) tabela).getTable().getModel().getValueAt(i, 0).equals("-")) { // caso o checkbox esteja marcado insere os dados extras dos professores

			// caso o checkbox esteja marcado insere os dados das disciplinas

			// adiciona a tabela os dados encontrados
			int tamanhoDisc = disciplina.size();
			String[] tDisc = new String[tamanhoDisc];
			for (int j = 0; j <tamanhoDisc; j++) {
				tDisc[j] = disciplina.get(j).getNome();
			}

			String[] tCod = new String[tamanhoDisc];
			for (int j = 0; j < tamanhoDisc; j++) {
				tCod[j] = disciplina.get(j).getCodigo();
			}
			
			String[] totVagas = new String[tamanhoDisc];
			for (int j = 0; j <tamanhoDisc; j++) {
				totVagas[j] = "0";
			}
			
			String[] periotizados = new String[tamanhoDisc];
			for (int j = 0; j <tamanhoDisc; j++) {
				periotizados[j] = "0/0";
			}
			
			((CadastroPedidosTableModel) tabela).getTable().setRowHeight(i, tamanhoDisc*20);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tCod, false), i, 3);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(new CustomInnerTable(tamanhoDisc, tDisc, false), i, 4);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(new CustomInnerTable(tamanhoDisc, totVagas, false),i, 5);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(new CustomInnerTable(tamanhoDisc, periotizados, true), i, 6);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt("-", i, 0);
			
		} else { // caso contrario os retira se estiverem na tabela
			String[] empty = { "" };
			((CadastroPedidosTableModel) tabela).getTable().setRowHeight(i, 20);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(empty, i, 3);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(empty, i, 4);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(empty, i, 5);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt(empty, i, 6);
			((CadastroPedidosTableModel) tabela).getTable().getModel().setValueAt("+", i, 0);
		}
	}
		
		disciplina.clear();
	}

	public JPanel getPainel() {
		return painel;
	}
	
	public void setPainel(JPanel painel) {
		this.painel = painel;
	}
}
