package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import ufbank.interfacetesttool.util.ViewUtil;

public class TableCellOperatonPanelRender implements TableCellRenderer {
	private JPanel panel;
	private JTextField logText;

	private void init() {
		panel = new JPanel();
		panel.setOpaque(true);
		panel.setLayout(new BorderLayout());
		logText = new JTextField(ViewUtil.VIEW_LOG_ZH);
		logText.setBorder(null);
		logText.setEditable(false);
		panel.add(logText, BorderLayout.WEST);
	}

	public TableCellOperatonPanelRender() {
		super();
		init();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean flag1, int i,
			int j) {
		// TODO Auto-generated method stub
		panel.setForeground(table.getForeground());
		panel.setBackground(table.getBackground());
		logText.setForeground(table.getForeground());
		logText.setBackground(table.getBackground());
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
			logText.setForeground(table.getSelectionForeground());
			logText.setBackground(table.getSelectionBackground());
		}
		return panel;
	}

}
