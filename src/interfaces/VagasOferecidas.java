package interfaces;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import timetable.Docente;
import timetable.Turma;

public class VagasOferecidas extends InterfacesTabela{
	
	public VagasOferecidas() {
		super(new VagasOferecidasTableModel(), "Salvar");
		//gera os eventos ao se clicar no botão salvar
		botaoPadrao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
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
