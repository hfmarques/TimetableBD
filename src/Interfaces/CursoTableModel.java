/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Héber
 */
public class CursoTableModel extends JPanel /*extends AbstractTableModel*/ {
    private final boolean DEBUG = false;
    private JTable table;
    
    public CursoTableModel() {
        super(new GridLayout(1,0));

        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);        
        table.setDefaultEditor(Integer.class, new IntegerEditor(0, 100));
        add(scrollPane);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Nome", "Código", "Turma"};
        
        private ArrayList<ArrayList<Object>> data;

        public MyTableModel() {
            data = new ArrayList(); //row
            data.add(new ArrayList()); //col
            
            data.get(0).add("aaa");
            data.get(0).add("aaa");
            data.get(0).add("aaa");
            
        }

        public String[] getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(String[] columnNames) {
            this.columnNames = columnNames;
        }

        public ArrayList<ArrayList<Object>> getData() {
            return data;
        }
        
        public void addRow(ArrayList<Object> row){
            data.add(row);
        }

        public void setData(ArrayList<ArrayList<Object>> data) {
            this.data = data;
        }
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data.get(row).get(col);
        }

       @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col < 0) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data.get(row).set(col, value);
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data.get(i).get(j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
}
