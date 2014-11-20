package interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import timetable.Docente;
import timetable.Turma;

public class VagasOferecidas {
	private JPanel painel; // painel principal
	private VagasOferecidasTableModel tabela; // modelo da tabela principal
	private JScrollPane scroll; // componente para se adicionar a tabela
	private JButton botaoSalvar; // salva os dados alterados da tabela

	public VagasOferecidas() {
		// inicializa as vari�veis
		painel = new JPanel();
		scroll = new JScrollPane();
		tabela = new VagasOferecidasTableModel();
		JPanel painelBotao = new JPanel();
		botaoSalvar = new JButton("Salvar");
		
		// monta a janela principal
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		painel.setLayout(gridBag);
		GridBagLayout btnGridBag = new GridBagLayout();
		painelBotao.setLayout(btnGridBag);
		
		
		scroll.getViewport().setBorder(null);
		scroll.getViewport().add(tabela);
		scroll.setSize(450, 450);

		painelBotao.add(botaoSalvar);
		painel.add(scroll);
		painel.add(painelBotao);		

		// seta a posi��o do painel onde se localiza as tabelas
		LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(painel, constraints);

		// seta o tamanho da tabela
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
		constraints.insets = new Insets(30, 20, 20, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(scroll, constraints);
		
		// seta a posi��o do painel onde se localiza os bot�es
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(2, 2, 20, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridBag.setConstraints(painelBotao, constraints);
		
		// seta a posi��o do bot�o salvar
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 40);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoSalvar, constraints);
		
		//gera os eventos ao se clicar no bot�o salvar
		botaoSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<timetable.Turma> turma = (ArrayList<Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);				
			}
		});

	}
	
	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}
}
