package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ufbank.interfacetesttool.service.InterfaceTestService;

/**
 * 单选框表格列
 * 
 * @author TonyTang
 *
 */
public class TableCellRadioButtonRender implements TableCellRenderer {
	private JPanel panel;
	private JRadioButton select;
	private InterfaceTestService service;

	private void init() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		select = new JRadioButton();
		select.setHorizontalAlignment((int) 0.5f);
		panel.add(select, BorderLayout.CENTER);
	}

	public TableCellRadioButtonRender(InterfaceTestService service) {
		super();
		init();
		this.service = service;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus,
			int row, int column) {
		// TODO Auto-generated method stub
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
		// if (service.getTask(row).isSelected()) {
		// select.setSelected(true);
		// } else {
		// select.setSelected(false);
		// }
		return panel;
	}

}
