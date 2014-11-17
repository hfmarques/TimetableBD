package interfaces;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import timetable.Horario;

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
		ResultadoProfessor janelaResultados = new ResultadoProfessor();
		Professor janelaProfessor = new Professor();
		Disciplina janelaDisciplina = new Disciplina();
		PlanoDepartamental planoDepartamental = new PlanoDepartamental();
		PedidosCoordenadores pedidosCoordenadores = new PedidosCoordenadores();
		HistoricoAtendimento historicoAtendimento = new HistoricoAtendimento();
		VagasOferecidas vagasOferecidas = new VagasOferecidas();

		janelaPrincipal = new JFrame("Gerador de Grade");
		abas = new JTabbedPane();
		abas.addTab("Plano Departamental", planoDepartamental.getPainel());
		abas.addTab("Resultado", janelaResultados.getPainel());
		abas.addTab("Pedidos Coordenadores", pedidosCoordenadores.getPainel());
		abas.addTab("Vagas Oferecidas e Solicitadas", vagasOferecidas.getPainel());
		abas.addTab("Historico de Vagas Atendidas", historicoAtendimento.getPainel());
		abas.addTab("Docente", janelaProfessor.getPainel());
		abas.addTab("Disciplinas", janelaDisciplina.getPainel());
		abas.addTab("Cursos", janelaCurso.getPainel());
		abas.addTab("Calouros", janelaCalouros.getPainel());
		janelaPrincipal.add(abas);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// outros metodos prepara...

	public void mostraJanela() {
		janelaPrincipal.pack();
		janelaPrincipal.setExtendedState( janelaPrincipal.getExtendedState()|JFrame.MAXIMIZED_BOTH ); //maximiza a janela
		janelaPrincipal.setVisible(true);
	}
}
