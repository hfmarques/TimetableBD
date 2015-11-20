package estruturasAuxiliaresTabelas;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import tabelasInternas.DefaultInternalTable;
import tabelasInternas.TotalVagasInternalTable;
import tabelasInternas.VagasDesperiotizadosInternalTable;
import tabelasInternas.VagasPeriotizadosInternalTable;

@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer{
	
	private ArrayList<Color> coresLinhas;
	
	public CellRenderer(){
		this.coresLinhas = null;
	}
	
	public CellRenderer(ArrayList<Color> coresLinhas){
		this.coresLinhas = coresLinhas;
	}
	
	@Override
	public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(coresLinhas != null){
			this.setBackground(coresLinhas.get(row));
		}
		if(value instanceof DefaultInternalTable){
			DefaultInternalTable c = (DefaultInternalTable) value;
			return c.getTable();
		}
		if(value instanceof TotalVagasInternalTable){
			TotalVagasInternalTable c = (TotalVagasInternalTable) value;
			return c.getTable();
		}
		if(value instanceof VagasPeriotizadosInternalTable){
			VagasPeriotizadosInternalTable c = (VagasPeriotizadosInternalTable) value;
			return c.getTable();
		}
		if(value instanceof VagasDesperiotizadosInternalTable){
			VagasDesperiotizadosInternalTable c = (VagasDesperiotizadosInternalTable) value;
			return c.getTable();
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	public ArrayList<Color> getCoresLinhas() {
		return coresLinhas;
	}

	public void setCoresLinhas(ArrayList<Color> coresLinhas) {
		this.coresLinhas = coresLinhas;
	}
	
	
}
