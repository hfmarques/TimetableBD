package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import estruturasAuxiliaresTabelas.TableMouseListener;
import tableModel.HorariosDocentesTableModel;

@SuppressWarnings("serial")
public class HorariosDocentes extends InterfacesTabela implements ActionListener{	    
	public HorariosDocentes(timetable.Docente docente){
		super(new HorariosDocentesTableModel(docente));
		
		table.addMouseListener(new TableMouseListener(table));        		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTable() {
		// TODO Auto-generated method stub
		
	}	
}
