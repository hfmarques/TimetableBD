/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Héber
 */
public class CursoTableModel extends AbstractTableModel {
   private final List<timetablebd.Curso> listCursos;
 
   public CursoTableModel(List<timetablebd.Curso> listCursos) {
     this.listCursos = listCursos;
   }
 
   @Override
   public int getColumnCount() {
     return 3;
   }
 
   @Override
   public int getRowCount() {
     return listCursos.size();
   }
 
   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
     timetablebd.Curso n = listCursos.get(rowIndex);
     
     switch (columnIndex) {
     case 0:
       return n.getCodigo();
     case 1:
       return n.getNome();
     case 2:
       return n.getTurno();
     }
     return null;
   }
}
