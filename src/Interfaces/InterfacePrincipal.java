/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
    DiscCurr discCurr = new DiscCurr();
    
    janelaPrincipal = new JFrame("Gerador de Grade");
    abas = new JTabbedPane();
    abas.addTab("Cursos", janelaCurso.getPainel());
    abas.addTab("Curriculo das Diciplinas", discCurr.getPainel());
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
