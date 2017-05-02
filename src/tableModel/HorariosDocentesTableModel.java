package tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.hibernate.HibernateException;

import hibernate.HorariosDocentesDAO;
import timetable.Docente;
import timetable.HorariosDocentes;

public class HorariosDocentesTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COL_DIA = 0;
	private static final int COL_OITO = 1;
	private static final int COL_NOVE = 2;
	private static final int COL_DEZ = 3;
	private static final int COL_ONZE = 4;
	private static final int COL_DOZE = 5;
	private static final int COL_TREZE = 6;
	private static final int COL_QUATORZE = 7;
	private static final int COL_QUINZE = 8;
	private static final int COL_DEZESEIS = 9;
	private static final int COL_DEZESSETE = 10;
	private static final int COL_DEZOITO = 11;
	private static final int COL_DEZENOVE = 12;
	private static final int COL_VINTE = 13;
	private static final int COL_VINTE_UM = 14;
	private static final int COL_VINTE_DOIS = 15;
	private String[] colunas = new String[]{"Dia", "8 horas", "9 horas", "10 horas", "11 horas", "12 horas", "13 horas", "14 horas", "15 horas", "16 horas", "17 horas", "18 horas", "19 horas", "20 horas", "21 horas", "22 horas"};
	private ArrayList<HorariosDocentes> linhas;
	private HorariosDocentesDAO horariosDocentesDAO;
	
	public HorariosDocentesTableModel(Docente d) {
		horariosDocentesDAO = new HorariosDocentesDAO();
		linhas = new ArrayList<HorariosDocentes>();
		try {
			linhas = (ArrayList<HorariosDocentes>) horariosDocentesDAO.encontraHorariosPorDocente(d.getCodigo());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getColumnCount() {
		return this.colunas.length;
	}

	@Override
	public int getRowCount() {
		return this.linhas.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) { 
		return colunas[columnIndex]; 
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HorariosDocentes hd = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_DIA:
			return hd.getDia();
		case COL_OITO:
			return hd.isOito();
		case COL_NOVE:
			return hd.isNove();
		case COL_DEZ:
			return hd.isDez();
		case COL_ONZE:
			return hd.isOnze();
		case COL_DOZE:
			return hd.isDoze();
		case COL_TREZE:
			return hd.isTreze();
		case COL_QUATORZE:
			return hd.isQuatorze();
		case COL_QUINZE:
			return hd.isQuinze();
		case COL_DEZESEIS:
			return hd.isDezesseis();
		case COL_DEZESSETE:
			return hd.isDezessete();
		case COL_DEZOITO:
			return hd.isDezoito();
		case COL_DEZENOVE:
			return hd.isDezenove();
		case COL_VINTE:
			return hd.isVinte();
		case COL_VINTE_UM:
			return hd.isVinteUm();
		case COL_VINTE_DOIS:
			return hd.isVinteDois();
		default:
			System.out.println("Coluna inválida");
			return null;
		}
			
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		HorariosDocentes hd = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_DIA:
			hd.setDia(value.toString());
			break;
		case COL_OITO:
			hd.setOito((Boolean) value);
			break;
		case COL_NOVE:
			hd.setNove((Boolean) value);
			break;
		case COL_DEZ:
			hd.setDez((Boolean) value);
			break;
		case COL_ONZE:
			hd.setOnze((Boolean) value);
			break;
		case COL_DOZE:
			hd.setDoze((Boolean) value);
			break;
		case COL_TREZE:
			hd.setTreze((Boolean) value);
			break;
		case COL_QUATORZE:
			hd.setQuatorze((Boolean) value);
			break;
		case COL_QUINZE:
			hd.setQuinze((Boolean) value);
			break;
		case COL_DEZESEIS:
			hd.setDezesseis((Boolean) value);
			break;
		case COL_DEZESSETE:
			hd.setDezessete((Boolean) value);
			break;
		case COL_DEZOITO:
			hd.setDezoito((Boolean) value);
			break;
		case COL_DEZENOVE:
			hd.setDezenove((Boolean) value);
			break;
		case COL_VINTE:
			hd.setVinte((Boolean) value);
			break;
		case COL_VINTE_UM:
			hd.setVinteUm((Boolean) value);
			break;
		case COL_VINTE_DOIS:
			hd.setVinteDois((Boolean) value);
			break;
		default:
			System.out.println("Coluna inválida");
		}
		if(hd.getDia() != null)
			horariosDocentesDAO.salvaOuEdita(hd);
	}
	
	@Override
	public Class<?> getColumnClass(int col) {  
        if (col != COL_DIA)  
            return Boolean.class;  
        else  
            return super.getColumnClass(col);  
    }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_DIA)
			return false;
		return true;
	}
}
