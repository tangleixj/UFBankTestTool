package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import ufbank.interfacetesttool.service.InterfaceTestService;

public class TableCellRadioButtonEditor extends DefaultCellEditor {
	private JPanel panel;
	private JRadioButton select;
	private InterfaceTestService service;
	private int row;

	private void init() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		select = new JRadioButton();
		select.setOpaque(true);
		select.setHorizontalAlignment((int) 0.5f);
		panel.add(select, BorderLayout.CENTER);
	}

	public TableCellRadioButtonEditor(JTextField textField, final InterfaceTestService service) {
		super(textField);
		init();
		this.service = service;
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				// System.out.println("before: " + service.getSelectedSet());
				if (select.isSelected()) {
					service.selectTask(row);
					// service.getTask(row).setSelected(true);
				} else {
					service.cancleTask(row);
					// service.getTask(row).setSelected(false);
				}
				// System.out.println("after: " + service.getSelectedSet());

			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, int row,
			int column) {
		panel.setForeground(table.getForeground());
		panel.setBackground(table.getBackground());
		select.setForeground(table.getForeground());
		select.setBackground(table.getBackground());
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
			select.setForeground(table.getSelectionForeground());
			select.setBackground(table.getSelectionBackground());
		}
		if (service.isSelected(row)) {
			select.setSelected(true);
		} else {
			select.setSelected(false);
		}
//		if (service.getTask(row).isSelected()) {
//			select.setSelected(true);
//		} else {
//			select.setSelected(false);
//		}
		this.row = row;
		return panel;
	}
}
