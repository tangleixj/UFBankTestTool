package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import ufbank.interfacetesttool.util.ViewUtil;

public class TableCellOperatonPanelEditor extends DefaultCellEditor {
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

		logText.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent mouseevent) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent mouseevent) {
				// TODO Auto-generated method stub
				new Thread() {
					@Override
					public void run() {
						try {
							String filePath = "H:\\WorkSpace\\UFBank2010\\VB\\logs\\20160704\\09 ±13∑÷54√Î536∫¡√Î_895_njcbnew_ye.log";
							LogViewFrame log = new LogViewFrame(filePath);
							log.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
	}

	public TableCellOperatonPanelEditor(JTextField textField) {
		super(textField);
		init();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, int row,
			int column) {
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
