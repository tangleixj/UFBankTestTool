package ufbank.interfacetesttool.ui;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ufbank.interfacetesttool.service.InterfaceTestService;

/**
 * ѡ�����
 * 
 * @author TonyTang
 *
 */
public class SelectItemPanel extends JPanel {
	private JLabel lable;
	private JComboBox<String> selectType;

	/**
	 * ���ñ�ǩ����
	 * 
	 * @param text
	 */
	public void setShowText(String text) {
		lable.setText(text);
	}

	public JComboBox<String> getSelectType() {
		return selectType;
	}

	/**
	 * Create the panel.
	 */
	public SelectItemPanel() {
		lable = new JLabel();
		this.add(lable);
		selectType = new JComboBox<String>();
		selectType.setPreferredSize(new Dimension(180, 25));
		this.add(selectType);
	}

}
