/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Héber
 */
public class Curso {
    private JPanel painel;
    private JTable tabela;
    JScrollPane scroll;
    private JButton botaoCarregar;
    private JButton botaoSair;
    
    
    public Curso() {
        JPanel painelBotao = new JPanel();
        DefaultTableModel modeloTabela = new DefaultTableModel();
        
        botaoCarregar = new JButton("Inserir Curso");
        botaoSair = new JButton("Sair");
        painel = new JPanel();        
        tabela = new JTable(modeloTabela);
        scroll = new JScrollPane();
        
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        painel.setLayout(gridBag);
        
        GridBagLayout btnGridBag = new GridBagLayout();
        painelBotao.setLayout(btnGridBag);
        
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Codigo");
        modeloTabela.addColumn("Turno");
        tabela.setBorder(new LineBorder(Color.black));
        tabela.setGridColor(Color.black);
        tabela.setShowGrid(true);
        scroll.getViewport().setBorder(null);
        scroll.getViewport().add(tabela); 
        scroll.setSize(450, 450);        
        
        painelBotao.add(botaoCarregar);
        painelBotao.add(botaoSair);
        painel.add(scroll);
        painel.add(painelBotao);
        
        ////////////////// arrumar a posição dos botões
        
        LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(0, 0, 0, 50);
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.EAST;
        btnGridBag.setConstraints(botaoSair, constraints);
        
        LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(0, 1020, 0, 0);
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.WEST;
        btnGridBag.setConstraints(botaoCarregar, constraints);
        
        LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.NORTHWEST;
        gridBag.setConstraints(painel, constraints);
        
        LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
        constraints.insets = new Insets(30,20,20,20);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.NORTHWEST;
        gridBag.setConstraints(scroll, constraints);
        
        LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(2,2,20,2);
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.anchor=GridBagConstraints.SOUTH;
        gridBag.setConstraints(painelBotao, constraints);
        
        botaoCarregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ArrayList<timetablebd.Curso> listCursos = new ArrayList<>();  
               for(int i=0;i<100;i++){                                
                    listCursos.add(new timetablebd.Curso(1, "ab", "dcc0", "diurno", 1));
               }
               CursoTableModel ntm = new CursoTableModel(listCursos);
               tabela.setModel(ntm);
               tabela.getColumnModel().getColumn(0).setHeaderValue("Codigo");
               tabela.getColumnModel().getColumn(1).setHeaderValue("Nome");
               tabela.getColumnModel().getColumn(2).setHeaderValue("Turno");
               tabela.getTableHeader().resizeAndRepaint();
            }
        });
        
        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
