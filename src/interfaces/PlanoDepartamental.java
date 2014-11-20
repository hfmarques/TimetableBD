package interfaces;

import java.awt.Color;
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

public class PlanoDepartamental {
	private JPanel painel; // painel principal
	private PlanoDepartamentalTableModel tabela; // modelo da tabela principal
	private JScrollPane scroll; // componente para se adicionar a tabela
	private JButton botaoSalvar; // salva os dados alterados da tabela

	public PlanoDepartamental() {
		// inicializa as variáveis
		painel = new JPanel();
		scroll = new JScrollPane();
		tabela = new PlanoDepartamentalTableModel();
		JPanel painelBotao = new JPanel();
		botaoSalvar = new JButton("Salvar");
		
		insereProfComboBox(tabela.getTable(), tabela.getTable().getColumnModel().getColumn(6));
		
		///TEMPORARIO
		
		ArrayList<ArrayList<Object>> cores = new ArrayList<ArrayList<Object>>();
		
		ArrayList<Object> linha = new ArrayList<Object>();
		
		linha.add(0);
		linha.add(Color.GREEN);
		
		cores.add(linha);
		
		ArrayList<Object> linha2 = new ArrayList<Object>();
		
		linha2.add(1);
		linha2.add(Color.CYAN);
		
		cores.add(linha2);
		
		for(int i=2;i<5;i++){
			ArrayList<Object> linha3 = new ArrayList<Object>();			
			linha3.add(i);
			linha3.add(Color.RED);
			cores.add(linha3);
		}
		
		for(int i=6;i<10;i++){
			ArrayList<Object> linha3 = new ArrayList<Object>();			
			linha3.add(i);
			linha3.add(Color.GRAY);
			cores.add(linha3);
		}
		
		
		int[] rows = {0,3};
		for(int i = 0; i<tabela.getTable().getColumnCount(); i++){
			this.tabela.getTable().getColumnModel().getColumn(i).setCellRenderer(new CorLinhaCellRenderer(cores));
		}
		
		///TEMPORARIO
		
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

		// seta a posição do painel onde se localiza as tabelas
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
		
		// seta a posição do painel onde se localiza os botões
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(2, 2, 20, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridBag.setConstraints(painelBotao, constraints);
		
		// seta a posição do botão salvar
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 40);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoSalvar, constraints);
		
		//gera os eventos ao se clicar no botão salvar
		botaoSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<timetable.Turma> turma = (ArrayList<Turma>) hibernate.HibernateUtil.findAll(timetable.Turma.class);				
			}
		});

	}

	public void insereProfComboBox(JTable table, TableColumn ComboColumn) {
		ArrayList<timetable.Docente> prof = (ArrayList<Docente>) hibernate.HibernateUtil.findAll(timetable.Docente.class);
		JComboBox<String> comboBox = new JComboBox();
		Iterator<?> it = prof.iterator();
		while(it.hasNext()){
			comboBox.addItem(((timetable.Docente)it.next()).getNomeCompleto());
		}
		ComboColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		ComboColumn.setCellRenderer(renderer);
	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}

}
