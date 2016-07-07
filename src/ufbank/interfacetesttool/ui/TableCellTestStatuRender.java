package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import ufbank.interfacetesttool.bean.TestTaskBean;
import ufbank.interfacetesttool.service.InterfaceTestService;

public class TableCellTestStatuRender implements TableCellRenderer {
	private JPanel panel;
	private JTextField statu;
	private InterfaceTestService service;

	private void init() {
		this.panel = new JPanel(new BorderLayout());
		this.statu = new JTextField();
		statu.setEditable(false);
		statu.setBorder(null);
		panel.add(statu, BorderLayout.WEST);
	}

	public TableCellTestStatuRender(InterfaceTestService service) {
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
		// statu.setForeground(table.getForeground());
		statu.setBackground(table.getBackground());
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
			// statu.setForeground(table.getSelectionForeground());
			statu.setBackground(table.getSelectionBackground());
		}
		TestTaskBean task = service.getTask(row);
		if (task != null) {
			statu.setText(task.getStatu().getShowText());
			if (task.getStatu().equals(TestTaskBean.statuMode.TESTING)) {
				statu.setForeground(Color.GREEN);
			} else if (task.getStatu().equals(TestTaskBean.statuMode.SUCCESS)) {
				statu.setForeground(Color.BLUE);
			} else if (task.getStatu().equals(TestTaskBean.statuMode.FAILED)) {
				statu.setForeground(Color.RED);
			} else {
				statu.setForeground(table.getForeground());
			}
		} else {
			statu.setText(null);
		}
		return panel;
	}

}
