/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import timetablebd.hibernate.util.HibernateUtil;

/**
 *
 * @author Héber
 */
public class Resultados {
    private JPanel painel;
    private ResultadosTableModel tabela;
    private JScrollPane scroll;
    private int cursosAdicionados;
    private JButton botaoUpdate;

    public Resultados() {
        JPanel painelBotao = new JPanel();
        
        botaoUpdate = new JButton("Atualizar Planilha");
        painel = new JPanel();
        scroll = new JScrollPane();
        tabela = new ResultadosTableModel();

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
        
               
        botaoUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                for(int i=0;i< tabela.getTable().getModel().getRowCount();i++){ //procura em todas as linhas da tabela
                    if((boolean)tabela.getTable().getModel().getValueAt(i, 0) == true){ //caso o checkbox esteja marcado insere os dados extras dos professores
                        List<?> lista = HibernateUtil.findAll(timetablebd.Turma.class);
                        ArrayList<String> disc = new ArrayList<String>();
                        ArrayList<String> cod = new ArrayList<String>();
                        ArrayList<Integer> cred = new ArrayList<Integer>();
                        
                        for(int j=0;j<lista.size();j++){
                            for(int k=0; k<((timetablebd.Turma)lista.get(j)).getDocente().size();k++){     
                            System.out.println("aaaa");                  
                                if(tabela.getTable().getModel().getValueAt(i, 1).equals(((timetablebd.Turma)lista.get(j)).getDocente().get(k).getCodigo())){
                                   disc.add(((timetablebd.Turma)lista.get(j)).getDisciplina().getNome());
                                   cod.add(((timetablebd.Turma)lista.get(j)).getDisciplina().getNome() + " - " + ((timetablebd.Turma)lista.get(j)).getCodigo());
                                   cred.add(((timetablebd.Turma)lista.get(j)).getDisciplina().getCreditos());
                                }
                            }
                        }
                        
                        String[] sDisc = new String[disc.size()];
                        for(int j=0;j<disc.size();j++){
                            sDisc[j] = disc.get(j);
                        }
                        
                        String[] sCod = new String[cod.size()];
                        for(int j=0;j<cod.size();j++){
                            sCod[j] = cod.get(j);
                        }
                        
                        String[] sCred = new String[cred.size()];
                        for(int j=0;j<cred.size();j++){
                            sCred[j] = cred.get(j).toString();
                        }
                        
                        tabela.getTable().getModel().setValueAt(sDisc, i, 3);
                        tabela.getTable().getModel().setValueAt(sCod, i, 4);
                        tabela.getTable().getModel().setValueAt(sCred, i, 5);
                    }
                    
                    else{ //caso contrario os retira
                        String[] empty = {""};
                        tabela.getTable().getModel().setValueAt(empty, i, 3);
                        tabela.getTable().getModel().setValueAt(empty, i, 4);
                        tabela.getTable().getModel().setValueAt(empty, i, 5);
                    }
                }               
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
