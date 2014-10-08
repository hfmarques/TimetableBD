package interfaces;

import java.awt.GridBagConstraints;

/**
 *
 * @author H�ber
 */
class LayoutConstraints {
	/**
	 * Define os valores de layout para um componente inserido num GridBagLayout
	 * 
	 * @param gb
	 *            GridBagLayout que cont�m o componente
	 * @param gridx
	 *            Coluna onde o componente ser� inserido
	 * @param gridy
	 *            Linha onde o componente ser� inserido
	 * @param gridwidth
	 *            Quantidade de colunas que o componente ocupar�
	 * @param gridheight
	 *            Quantidade de linhas que o componente ocupar�
	 * @param weightx
	 *            Propor��o que as colunas ocupadas pelo componente ter�o
	 * @param weighty
	 *            Propor��o que as linhas ocupadas pelo componente ter�o
	 */
	static void setConstraints(GridBagConstraints gb, int gridx, int gridy,
			int gridwidth, int gridheight, int weightx, int weighty) {
		// horizontal cordinate on grid
		// escolhe em qual coluna o componente ficar�
		gb.gridx = gridx;
		// vertical cordinate on grid
		// escolhe em qual linha o componente ficar�
		gb.gridy = gridy;
		// how many columns the component will occupy
		// escolhe quantas colunas o comopnente ocupar�
		gb.gridwidth = gridwidth;
		// how many rows the component will occupy
		// escolhe quantas linhas o comopnente ocupar�
		gb.gridheight = gridheight;
		// sets the proportion occupied in the column
		// escolhe a porcentagem que o componente ocupar� em sua coluna
		gb.weightx = weightx;
		// sets the proportion occupied in the row
		// escolhe a porcentagem que o componente ocupar� em sua linha
		gb.weighty = weighty;
	}

}
