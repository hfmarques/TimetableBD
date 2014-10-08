package interfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */
public class BotaoTabela extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor, ActionListener {

	JTable table;
	JButton renderButton;
	JButton editButton;
	String text;

	public BotaoTabela(JTable table, int column) {
		super();
		this.table = table;
		renderButton = new JButton();

		editButton = new JButton();
		editButton.setFocusPainted(false);

		editButton.addActionListener(this);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);

	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		renderButton.setText((value == null) ? "" : value.toString());
		return renderButton;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		text = (value == null) ? "" : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Object getCellEditorValue() {
		return text;
	}

	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();
		// System.out.println(e.getActionCommand() + " : " +
		// table.getSelectedRow());

		List<?> turma = HibernateUtil.findTurmas();

		// ResultadosTableModel.MyTableModel model =
		// ((ResultadosTableModel.MyTableModel) tabela.getTable().getModel());
		if (table.getModel().getValueAt(table.getSelectedRow(), 0)
				.equals("Expandir")) { // caso o checkbox esteja marcado insere
										// os dados extras dos professores

			ArrayList<String> disc = new ArrayList<String>();
			ArrayList<String> cod = new ArrayList<String>();
			ArrayList<Integer> cred = new ArrayList<Integer>();

			for (int j = 0; j < turma.size(); j++) { // para todas as turmas
				for (int k = 0; k < ((timetable.Turma) turma.get(j))
						.getDocente().size(); k++) { // para todos os
														// professores
					// se existe doscente
					// busca se o professore referente a linha atual da tambela
					// possui o mesmo codigo que o professor que está no loop,
					// se sim adiciona sua turma
					if (!((timetable.Turma) turma.get(j)).getDocente()
							.isEmpty()
							&& table.getModel()
									.getValueAt(table.getSelectedRow(), 1)
									.equals(((timetable.Turma) turma.get(j))
											.getDocente().get(k).getCodigo())) {
						disc.add(((timetable.Turma) turma.get(j))
								.getDisciplina().getNome());
						cod.add(((timetable.Turma) turma.get(j))
								.getDisciplina().getNome()
								+ " - "
								+ ((timetable.Turma) turma.get(j)).getCodigo());
						cred.add(((timetable.Turma) turma.get(j))
								.getDisciplina().getCreditos());
					}
				}

			}

			if (!disc.isEmpty() || !cod.isEmpty() || !cred.isEmpty()) { // se
																		// houver
																		// dados
																		// no
																		// array
																		// de
																		// disciplina,
																		// codigo
																		// e
																		// creditação
																		// para
																		// aquela
																		// turma
				// adiciona a tabela os dados encontrados
				String[] sDisc = new String[disc.size()];
				for (int j = 0; j < disc.size(); j++) {
					sDisc[j] = disc.get(j);
				}

				String[] sCod = new String[cod.size()];
				for (int j = 0; j < cod.size(); j++) {
					sCod[j] = cod.get(j);
				}

				String[] sCred = new String[cred.size()];
				for (int j = 0; j < cred.size(); j++) {
					sCred[j] = cred.get(j).toString();
				}

				table.getModel().setValueAt(sDisc, table.getSelectedRow(), 3);
				table.getModel().setValueAt(sCod, table.getSelectedRow(), 4);
				table.getModel().setValueAt(sCred, table.getSelectedRow(), 5);

				disc.clear();
				cod.clear();
				cred.clear();
			}
			table.getModel().setValueAt("Comprimir", table.getSelectedRow(), 0);
		} else { // caso contrario os retira se estiverem na tabela
			String[] empty = { "" };
			table.getModel().setValueAt(empty, table.getSelectedRow(), 3);
			table.getModel().setValueAt(empty, table.getSelectedRow(), 4);
			table.getModel().setValueAt(empty, table.getSelectedRow(), 5);
			table.getModel().setValueAt("Expandir", table.getSelectedRow(), 0);
		}
	}

	public JButton getRenderButton() {
		return renderButton;
	}
}
