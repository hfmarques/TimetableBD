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
                
//                for(int i=0;i< tabela.getTable().getModel().getRowCount();i++){ //procura em todas as linhas da tabela
//                    if((boolean)tabela.getTable().getModel().getValueAt(i, 0) == true){ //caso o checkbox esteja marcado insere os dados extras dos professores
//                        String[] phones = {"1232412421","12312412412","12312421412"};
//                        tabela.getTable().getModel().setValueAt(phones, i, 1);
//                    }
//                    
//                    else{ //caso contrario os retira
//                        String[] phones = {""};
//                        tabela.getTable().getModel().setValueAt(phones, i, 1);
//                    }
//                }               
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
