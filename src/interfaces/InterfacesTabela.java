package interfaces;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class InterfacesTabela {

	protected JPanel painel; // painel principal
	protected JPanel tabela; // modelo da tabela principal
	protected JScrollPane scroll; // componente para se adicionar a tabela
	protected JButton botaoSalvar; // salva os dados alterados da tabela
	protected JPanel painelBotao;
	protected GridBagLayout gridBag;
	protected GridBagConstraints constraints;
	protected GridBagLayout btnGridBag;

	public InterfacesTabela(JPanel tabela, String textoBotao) {
		// inicializa as variáveis
		painel = new JPanel();
		scroll = new JScrollPane();
		this.tabela = tabela;
		painelBotao = new JPanel();
		botaoSalvar = new JButton(textoBotao);
		
		// monta a janela principal
		gridBag = new GridBagLayout();
		constraints = new GridBagConstraints();
		painel.setLayout(gridBag);
		btnGridBag = new GridBagLayout();
		painelBotao.setLayout(btnGridBag);
		
		
		scroll.getViewport().setBorder(null);
		scroll.getViewport().add((Component) tabela);
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

	}

	public JPanel getPainel() {
		return painel;
	}

	public void setPainel(JPanel painel) {
		this.painel = painel;
	}

}
