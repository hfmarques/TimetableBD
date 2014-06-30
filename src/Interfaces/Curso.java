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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Héber
 */
public class Curso {

    private JPanel painel;
    private CursoTableModel tabela;
    private JScrollPane scroll;
    private JButton botaoInserir;
    private JButton botaoSalvar;
    private int cursosAdicionados;
    

    public Curso() {
        JPanel painelBotao = new JPanel();
//        DefaultTableModel modeloTabela = new DefaultTableModel();

        botaoInserir = new JButton("Inserir Curso");
        botaoSalvar = new JButton("Salvar");
        painel = new JPanel();
//        tabela = new JTable(modeloTabela);
        scroll = new JScrollPane();
        tabela = new CursoTableModel();

        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        painel.setLayout(gridBag);

        GridBagLayout btnGridBag = new GridBagLayout();
        painelBotao.setLayout(btnGridBag);

//        modeloTabela.addColumn("Nome");
//        modeloTabela.addColumn("Codigo");
//        modeloTabela.addColumn("Turno");
//        tabela.setBorder(new LineBorder(Color.black));
//        tabela.setGridColor(Color.black);
//        tabela.setShowGrid(true);
        scroll.getViewport().setBorder(null);
        scroll.getViewport().add(tabela);
        scroll.setSize(450, 450);

        painelBotao.add(botaoInserir);
        painelBotao.add(botaoSalvar);
        painel.add(scroll);
        painel.add(painelBotao);

        ////////////////// arrumar a posição dos botões
        LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(0, 0, 0, 40);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        btnGridBag.setConstraints(botaoSalvar, constraints);

        LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(0, 980, 0, 0);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        btnGridBag.setConstraints(botaoInserir, constraints);

        LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        gridBag.setConstraints(painel, constraints);

        LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
        constraints.insets = new Insets(30, 20, 20, 20);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        gridBag.setConstraints(scroll, constraints);

        LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
        constraints.insets = new Insets(2, 2, 20, 2);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.SOUTH;
        gridBag.setConstraints(painelBotao, constraints);

        botaoInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Object> linha = new ArrayList();
                CursoTableModel.MyTableModel model = (CursoTableModel.MyTableModel) tabela.getTable().getModel();
                                
                for (int i = 0; i < model.getData().get(0).size(); i++) {
                    linha.add("");
                }                
                
                model.addRow(linha);
                for (int i = 0; i < model.getData().get(0).size(); i++) {
                    if (model.getData().size() - 1 < 0) {
                        model.fireTableCellUpdated(0, i);
                    } else {
                        model.fireTableCellUpdated(model.getData().size() - 1, i);
                    }
                }
                cursosAdicionados++;
            }
        });

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CursoTableModel.MyTableModel model = (CursoTableModel.MyTableModel) tabela.getTable().getModel();
                for(int i=0;i<cursosAdicionados;i++){
                   timetablebd.Curso curso;
                   curso = new timetablebd.Curso(i,
                                                tabela.getTable().getValueAt((model.getData().size()-1) - (i), 0).toString(),
                                                tabela.getTable().getValueAt((model.getData().size()-1) - (i), 1).toString(),
                                                tabela.getTable().getValueAt((model.getData().size()-1) - (i), 2).toString(),
                                                i);
                   
                   System.out.println(curso.getCodigo()+"\n"+curso.getNome()+"\n"+curso.getTurno());
                }
                cursosAdicionados = 0;
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
