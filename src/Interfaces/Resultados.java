/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import timetablebd.hibernate.util.HibernateUtil;

/**
 *
 * @author Héber
 */
public class Resultados {

  private JPanel painel; // painel principal
  private ResultadosTableModel tabela; // modelo da tabela principal
  private JScrollPane scroll; //componente para se adicionar a tabela
  private JButton botaoUpdate;

  public Resultados() {
    // inicializa as variáveis
    JPanel painelBotao = new JPanel();
    botaoUpdate = new JButton("Atualizar Planilha");
    painel = new JPanel();
    scroll = new JScrollPane();
    tabela = new ResultadosTableModel();
    
    //inicializa as turmas dos professores da tabela
    inicializaTurmas();

    // monta a jaela principal
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    painel.setLayout(gridBag);
    GridBagLayout btnGridBag = new GridBagLayout();
    painelBotao.setLayout(btnGridBag);

    scroll.getViewport().setBorder(null);
    scroll.getViewport().add(tabela);
    scroll.setSize(450, 450);

    painelBotao.add(botaoUpdate);
    painel.add(scroll);
    painel.add(painelBotao);

    //seta a posição do botão update
    LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
    constraints.insets = new Insets(0, 1100, 0, 0);
    constraints.fill = GridBagConstraints.NONE;
    constraints.anchor = GridBagConstraints.WEST;
    btnGridBag.setConstraints(botaoUpdate, constraints);

    //seta a posição do painel onde se localiza as tabelas
    LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    gridBag.setConstraints(painel, constraints);

    //seta o tamanho da tabela
    LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
    constraints.insets = new Insets(30, 20, 20, 20);
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    gridBag.setConstraints(scroll, constraints);

    //seta a posição do painel onde se localiza os botões
    LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
    constraints.insets = new Insets(2, 2, 20, 2);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.anchor = GridBagConstraints.SOUTH;
    gridBag.setConstraints(painelBotao, constraints);

  }

  public JPanel getPainel() {
    return painel;
  }

  public void setPainel(JPanel painel) {
    this.painel = painel;
  }
  
  //metodo para se inicializar os dados de todas as turmas na tabela em conjunto com seu professor
  public void inicializaTurmas(){
    List<?> turma = HibernateUtil.findTurmas();
    
//    ResultadosTableModel.MyTableModel model = (ResultadosTableModel.MyTableModel) tabela.getTable().getModel();

    for (int i = 0; i < tabela.getTable().getModel().getRowCount(); i++) { //procura em todas as linhas da tabela
      if (tabela.getTable().getModel().getValueAt(i, 0).equals("Comprimir")) { //caso o checkbox esteja marcado insere os dados extras dos professores

        ArrayList<String> disc = new ArrayList<String>();
        ArrayList<String> cod = new ArrayList<String>();
        ArrayList<Integer> cred = new ArrayList<Integer>();

        for (int j = 0; j < turma.size(); j++) { //para todas as turmas
          for (int k = 0; k < ((timetablebd.Turma) turma.get(j)).getDocente().size(); k++) { //para todos os professores
            //se existe doscente
            //busca se o professore referente a linha atual da tambela possui o mesmo codigo que o professor que está no loop, se sim adiciona sua turma
            if (!((timetablebd.Turma) turma.get(j)).getDocente().isEmpty() && tabela.getTable().getModel().getValueAt(i, 1).equals(((timetablebd.Turma) turma.get(j)).getDocente().get(k).getCodigo())) {
              disc.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getNome());
              cod.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getNome() + " - " + ((timetablebd.Turma) turma.get(j)).getCodigo());
              cred.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getCreditos());
            }
          }

        }

        if(!disc.isEmpty() || !cod.isEmpty() || !cred.isEmpty()){ //se houver dados no array de disciplina, codigo e creditação para aquela turma
          //adiciona a tabela os dados encontrados
          String[] sDisc = new String[disc.size()];
          for (int j = 0; j < disc.size(); j++) {
            sDisc[j] = disc.get(j);
          }

          String[] sCod = new String[cod.size()];
          for (int j = 0; j < cod.size(); j++) {
            sCod[j] = cod.get(j);
          }

          String[] sCred = new String[cred.size()];
          for (int j = 0; j < cred.size(); j++) {
            sCred[j] = cred.get(j).toString();
          }

          tabela.getTable().getModel().setValueAt(sDisc, i, 3);
          tabela.getTable().getModel().setValueAt(sCod, i, 4);
          tabela.getTable().getModel().setValueAt(sCred, i, 5);

          disc.clear();
          cod.clear();
          cred.clear();
        }
      }
    }
    
//    for (int j = 0; j < turma.size(); j++) { //para todas as turmas
//      if(((timetablebd.Turma)turma.get(j)).getDocente().isEmpty()){
//        ArrayList<Object> row = new ArrayList();
//
//        row.add(true);
//        row.add("");
//        row.add("");
//        row.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getNome()); // disciplina
//        row.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getNome() + " - " + ((timetablebd.Turma) turma.get(j)).getCodigo()); //codigo
//        row.add(((timetablebd.Turma) turma.get(j)).getDisciplina().getCreditos()); // credito da turma
//        row.add("");
//        model.getData().add(row);
//      }
//    }
  }
}
