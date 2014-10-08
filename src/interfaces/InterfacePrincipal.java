package interfaces;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */
public class InterfacePrincipal {
	private JFrame janelaPrincipal;
	private JTabbedPane abas;

	// main e montaTela

	public void preparaJanela() {
		Curso janelaCurso = new Curso();
		Calouros janelaCalouros = new Calouros();
		Resultados janelaResultados = new Resultados();

		janelaPrincipal = new JFrame("Gerador de Grade");
		abas = new JTabbedPane();
		abas.addTab("Resultado", janelaResultados.getPainel());
		abas.addTab("Cursos", janelaCurso.getPainel());
		abas.addTab("Calouros", janelaCalouros.getPainel());
		janelaPrincipal.add(abas);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// outros metodos prepara...

	public void mostraJanela() {
		janelaPrincipal.pack();
		janelaPrincipal.setSize(1280, 730);
		janelaPrincipal.setVisible(true);
	}
}
