package interfaces;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import interfaceTabelas.CadastroPedidosCoordenadores;
import interfaceTabelas.Calouros;
import interfaceTabelas.Curso;
import interfaceTabelas.Disciplina;
import interfaceTabelas.Docente;
import interfaceTabelas.PedidosCoordenadores;
import interfaceTabelas.PlanoDepartamental;
import interfaceTabelas.ResultadoProfessor;
import interfaceTabelas.Turma;

/**
 *
 * @author Héber
 */
public class InterfacePrincipal {
	private static final int ABA_HOME = 0;
	private static final int ABA_PLANO_DEPARTAMENTAL = 1;
	private static final int ABA_RESULTADO = 2;
	private static final int ABA_CADASTRO_PEDIDOS = 3;
	private static final int ABA_PEDIDO_COORDENADORES = 4;
//	private static final int ABA_VAGAS_OFERECIDAS = 4;
//	private static final int ABA_HISTORICO_VAGAS_ATENDIDAS = 5;
	private static final int ABA_DOCENTE = 5;
	private static final int ABA_DISCIPLINA = 6;
	private static final int ABA_TURMAS = 7;
	private static final int ABA_CURSOS = 8;
	private static final int ABA_CALOUROS = 9;
	
	private JFrame janelaPrincipal;
	private JTabbedPane abas;
	
	private Curso janelaCurso;
	private Calouros janelaCalouros;
	private ResultadoProfessor janelaResultados;
	private Docente janelaDocente;
	private Disciplina janelaDisciplina;
	private Turma janelaTurma;
	private PlanoDepartamental janelaPlanoDepartamental;
	private CadastroPedidosCoordenadores janelaCadastro;
	private PedidosCoordenadores janelaPedidosCoordenadores;
	private Home janelaHome;
//	private HistoricoAtendimento historicoAtendimento;
//	private VagasOferecidas vagasOferecidas;	

	public void preparaJanela() {		
		janelaCurso = new Curso();
		janelaCalouros = new Calouros();
		janelaResultados = new ResultadoProfessor();
		janelaDocente = new Docente();
		janelaDisciplina = new Disciplina();
		janelaTurma = new Turma();
		janelaPlanoDepartamental = new PlanoDepartamental();
		janelaCadastro = new CadastroPedidosCoordenadores();
		janelaPedidosCoordenadores = new PedidosCoordenadores();
		janelaHome = new Home();
//		historicoAtendimento = new HistoricoAtendimento();
//		vagasOferecidas = new VagasOferecidas();
		
		
		janelaPrincipal = new JFrame("Gerador de Grade");
		
		abas = new JTabbedPane();
		abas.addTab("Home", janelaHome.getPainel());
		abas.addTab("Plano Departamental", janelaPlanoDepartamental.getPainel());
		abas.addTab("Resultado", janelaResultados.getPainel());
		abas.addTab("Cadastro de Pedidos", janelaCadastro.getPainel());
		abas.addTab("Pedidos Coordenadores", janelaPedidosCoordenadores.getPainel());
//		abas.addTab("Vagas Oferecidas", vagasOferecidas.getPainel());
//		abas.addTab("Historico de Vagas Atendidas", historicoAtendimento.getPainel());
		abas.addTab("Docente", janelaDocente.getPainel());
		abas.addTab("Disciplinas", janelaDisciplina.getPainel());
		abas.addTab("Turmas", janelaTurma.getPainel());
		abas.addTab("Cursos", janelaCurso.getPainel());
		abas.addTab("Calouros", janelaCalouros.getPainel());
		
		janelaPrincipal.add(abas);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		abas.addChangeListener(new javax.swing.event.ChangeListener() {  
            public void stateChanged(javax.swing.event.ChangeEvent e) {   
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_HOME)){}
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_PLANO_DEPARTAMENTAL))
            		janelaPlanoDepartamental.updateTable();
                            	
            	if(abas.getSelectedComponent() == abas.getComponent(ABA_RESULTADO))
            		janelaResultados.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CADASTRO_PEDIDOS))
            		janelaCadastro.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_PEDIDO_COORDENADORES))
            		janelaPedidosCoordenadores.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_DOCENTE))
            		janelaDocente.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_DISCIPLINA))
            		janelaDisciplina.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_TURMAS))
            		janelaTurma.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CURSOS))
            		janelaCurso.updateTable();
                           	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CALOUROS))
            		janelaCalouros.updateTable();  	
                
            }  
        });
	}

	// outros metodos prepara...

	public void mostraJanela() {
		janelaPrincipal.pack();
		janelaPrincipal.setExtendedState( janelaPrincipal.getExtendedState()|JFrame.MAXIMIZED_BOTH ); //maximiza a janela
		janelaPrincipal.setVisible(true);
	}
}
