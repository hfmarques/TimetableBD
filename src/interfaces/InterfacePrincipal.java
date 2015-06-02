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
		Turmas janelaTurmas = new Turmas();
		PlanoDepartamental planoDepartamental = new PlanoDepartamental();
		PedidosCoordenadores pedidosCoordenadores = new PedidosCoordenadores();
		HistoricoAtendimento historicoAtendimento = new HistoricoAtendimento();
		VagasOferecidas vagasOferecidas = new VagasOferecidas();
		CadastroPedidos janelaCadastro = new CadastroPedidos();

		janelaPrincipal = new JFrame("Gerador de Grade");
		abas = new JTabbedPane();
		abas.addTab("Plano Departamental", planoDepartamental.getPainel());
		abas.addTab("Resultado", janelaResultados.getPainel());
		abas.addTab("Cadastro de Pedidos", janelaCadastro.getPainel());
		abas.addTab("Pedidos Coordenadores", pedidosCoordenadores.getPainel());
		abas.addTab("Vagas Oferecidas", vagasOferecidas.getPainel());
		abas.addTab("Historico de Vagas Atendidas", historicoAtendimento.getPainel());
		abas.addTab("Docente", janelaProfessor.getPainel());
		abas.addTab("Disciplinas", janelaDisciplina.getPainel());
		abas.addTab("Turmas", janelaTurmas.getPainel());
		abas.addTab("Cursos", janelaCurso.getPainel());
		abas.addTab("Calouros", janelaCalouros.getPainel());
		
		janelaPrincipal.add(abas);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		abas.addChangeListener(new javax.swing.event.ChangeListener() {  
            public void stateChanged(javax.swing.event.ChangeEvent e) {
            	
            	if (abas.getSelectedComponent() == abas.getComponent(0)) {
                    ((PlanoDepartamentalTableModel) planoDepartamental.getTabela()).loadTableValues();
    				((PlanoDepartamentalTableModel) planoDepartamental.getTabela()).loadDataTable();
    				planoDepartamental.atualizaComboBox();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(1)) {
                    ((ResultadosProfessorTableModel) janelaResultados.getTabela()).loadTableValues();
    				janelaResultados.inicializaTurmas();
					((ResultadosProfessorTableModel) janelaResultados.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(2)) {
                    ((PedidosCoordenadoresTableModel) pedidosCoordenadores.getTabela()).loadTableValues();
    				((PedidosCoordenadoresTableModel) pedidosCoordenadores.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(3)) {
                    ((VagasOferecidasTableModel) vagasOferecidas.getTabela()).loadTableValues();
    				((VagasOferecidasTableModel) vagasOferecidas.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(4)) {
                    ((HistoricoAtendimentoTableModel) historicoAtendimento.getTabela()).loadTableValues();
    				((HistoricoAtendimentoTableModel) historicoAtendimento.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(5)) {
                    ((ProfessorTableModel) janelaProfessor.getTabela()).loadTableValues();
    				((ProfessorTableModel) janelaProfessor.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(6)) {
                    ((DisciplinaTableModel) janelaDisciplina.getTabela()).loadTableValues();
    				((DisciplinaTableModel) janelaDisciplina.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(7)) {
                    ((TurmasTableModel) janelaTurmas.getTabela()).loadTableValues();
    				((TurmasTableModel) janelaTurmas.getTabela()).loadDataTable();
                }
            	
            	if (abas.getSelectedComponent() == abas.getComponent(8)) {
                    ((CursoTableModel) janelaCurso.getTabela()).loadTableValues();
    				((CursoTableModel) janelaCurso.getTabela()).loadDataTable();
    				
                }                
                
                if (abas.getSelectedComponent() == abas.getComponent(9)) {
                    ((CalourosTableModel) janelaCalouros.getTabela()).loadTableValues();
    				((CalourosTableModel) janelaCalouros.getTabela()).loadDataTable();
                }
                
                if (abas.getSelectedComponent() == abas.getComponent(10)) {
                    ((CadastroPedidosTableModel) janelaCadastro.getTabela()).loadTableValues();
                    janelaCadastro.inicializaDisciplinas();
    				((CadastroPedidosTableModel) janelaCadastro.getTabela()).loadDataTable();
                }
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
