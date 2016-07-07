package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;

import ufbank.interfacetesttool.bean.InterfaceInforBean;
import ufbank.interfacetesttool.bean.TestTaskBean;
import ufbank.interfacetesttool.service.InterfaceTestService;
import ufbank.interfacetesttool.util.ViewUtil;

/**
 * 选择接口面板
 * 
 * @author TonyTang
 *
 */
public class SelectInterfaceFrame extends JFrame {

	private JPanel contentPane;

	private JButton submitButton;
	private JButton cancleButton;
	private JButton newInterfaceButton;
	private JTextField showInforButton;

	private JComboBox<String> selectBankType;
	private DefaultComboBoxModel<String> bankTypeModel;
	private JComboBox<String> selectBusinessType;
	private DefaultComboBoxModel<String> businessTypeModel;
	private JComboBox<String> selectInterfaceType;
	private DefaultComboBoxModel<String> interfaceTypeModel;
	private JTextField statuText;
	private InterfaceTestService service;
	private InterfaceInforBean tran;

	public void setStatuText(JTextField statuText) {
		this.statuText = statuText;
	}

	public void setService(InterfaceTestService service) {
		this.service = service;
	}

	/**
	 * 初始化选择项
	 * 
	 */
	private void initSelectPanel(JPanel panel) {
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		SelectItemPanel bankTypePanel = new SelectItemPanel();
		bankTypePanel.setShowText(ViewUtil.BANK_TYPE_ZH);
		bankTypeModel = new DefaultComboBoxModel<String>();
		selectBankType = bankTypePanel.getSelectType();
		selectBankType.setModel(bankTypeModel);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(bankTypePanel, gbc);

		SelectItemPanel businessTypePanel = new SelectItemPanel();
		businessTypePanel.setShowText(ViewUtil.BUSINESS_TYPE_ZH);
		businessTypeModel = new DefaultComboBoxModel<String>();
		selectBusinessType = businessTypePanel.getSelectType();
		selectBusinessType.setModel(businessTypeModel);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(businessTypePanel, gbc);

		SelectItemPanel interfaceTypePanel = new SelectItemPanel();
		interfaceTypePanel.setShowText(ViewUtil.INTERFACE_TYPE_ZH);
		interfaceTypeModel = new DefaultComboBoxModel<String>();
		selectInterfaceType = interfaceTypePanel.getSelectType();
		selectInterfaceType.setModel(interfaceTypeModel);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(interfaceTypePanel, gbc);

		showInforButton = new JTextField(ViewUtil.VIEW_INFOR_ZH);
		showInforButton.setBorder(null);
		showInforButton.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(showInforButton, gbc);

		selectBankType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				int index = selectBankType.getSelectedIndex();
				if (index != -1) {
					String bankName = selectBankType.getSelectedItem().toString();
					if (!bankName.equals(ViewUtil.ALL_ZH)) {
						initModel(selectBusinessType, businessTypeModel,
								service.getRegisteredBusinessNameList(bankName));
						businessTypeModel.addElement(ViewUtil.ALL_ZH);
					} else {
						businessTypeModel.removeAllElements();
						businessTypeModel.addElement(ViewUtil.ALL_ZH);
						selectBusinessType.setSelectedIndex(0);
					}
				}
			}
		});

		selectBusinessType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				int index = selectBusinessType.getSelectedIndex();
				if (index != -1) {
					String businessName = selectBusinessType.getSelectedItem().toString();
					if (!businessName.equals(ViewUtil.ALL_ZH)) {
						String bankName = selectBankType.getSelectedItem().toString();
						initModel(selectInterfaceType, interfaceTypeModel,
								service.getRegisteredInterfaceNameList(bankName, businessName));
						interfaceTypeModel.addElement(ViewUtil.ALL_ZH);
					} else {
						interfaceTypeModel.removeAllElements();
						interfaceTypeModel.addElement(ViewUtil.ALL_ZH);
						selectInterfaceType.setSelectedIndex(0);
					}
				}
			}
		});

		selectInterfaceType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				int index = selectInterfaceType.getSelectedIndex();
				if (index != -1) {
					String tranCode = selectInterfaceType.getSelectedItem().toString();
					if (!tranCode.equals(ViewUtil.ALL_ZH)) {
						String bankName = selectBankType.getSelectedItem().toString();
						tran = service.getTran(bankName, tranCode);
					}
				}
			}
		});

		showInforButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (selectBankType.getSelectedIndex() == -1 || selectInterfaceType.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(new JPanel(), "请选择一个接口", "错误", JOptionPane.WARNING_MESSAGE);
				} else {
					InterfaceInforPanel panel = new InterfaceInforPanel();
					panel.setStatuText(statuText);
					panel.setService(service);
					panel.showInterfaceInfor(tran);
					panel.setVisible(true);
				}
			}
		});
	}

	/**
	 * 初始化按钮栏
	 * 
	 */
	private void initOperationPanel(JPanel panel) {
		submitButton = new JButton(ViewUtil.OK_ZH);
		panel.add(submitButton);

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (selectBankType.getSelectedIndex() == -1 || selectInterfaceType.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(new JPanel(), "请选择一个接口", "错误", JOptionPane.WARNING_MESSAGE);
				} else {
					String bankName = selectBankType.getSelectedItem().toString();
					String businessName = selectBusinessType.getSelectedItem().toString();
					String tranCode = selectInterfaceType.getSelectedItem().toString();
					if (bankName.equals(ViewUtil.ALL_ZH) && businessName.equals(ViewUtil.ALL_ZH)
							&& tranCode.equals(ViewUtil.ALL_ZH)) {
						service.addMultiTask();
					} else if (!bankName.equals(ViewUtil.ALL_ZH) && businessName.equals(ViewUtil.ALL_ZH)
							&& tranCode.equals(ViewUtil.ALL_ZH)) {
						service.addMultiTask(bankName);
					} else if (!bankName.equals(ViewUtil.ALL_ZH) && !businessName.equals(ViewUtil.ALL_ZH)
							&& tranCode.equals(ViewUtil.ALL_ZH)) {
						service.addMultiTask(bankName, businessName);
					} else {
						service.addTask(new TestTaskBean(tran));
					}
				}
			}
		});
		cancleButton = new JButton(ViewUtil.CANCEL_ZH);
		panel.add(cancleButton);

		cancleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});

		newInterfaceButton = new JButton(ViewUtil.ADD_INTERFACE_ZH);
		panel.add(newInterfaceButton);
		newInterfaceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InterfaceInforPanel panel = new InterfaceInforPanel();
				panel.setStatuText(statuText);
				panel.setService(service);
				panel.addNewInterface();
				panel.setVisible(true);
			}
		});
	}

	private void initModel(JComboBox select, DefaultComboBoxModel model, Set<String> list) {
		model.removeAllElements();
		for (String item : list) {
			model.addElement(item);
		}
		select.setSelectedIndex(-1);
	}

	/**
	 * Create the frame.
	 */
	public SelectInterfaceFrame() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		ViewUtil.showCenter(this);
		setTitle("选择银行接口");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		/**
		 * 选择参数面板
		 */
		JPanel selectPanel = new JPanel();
		contentPane.add(selectPanel, BorderLayout.CENTER);
		initSelectPanel(selectPanel);
		/**
		 * 按钮面板
		 */
		JPanel operationPanel = new JPanel();
		contentPane.add(operationPanel, BorderLayout.SOUTH);
		initOperationPanel(operationPanel);

		addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				initModel(selectBankType, bankTypeModel, service.getRegisteredBankNameList());
				bankTypeModel.addElement(ViewUtil.ALL_ZH);
			}
		});
	}

	public static void main(String[] args) {
		SelectInterfaceFrame window = new SelectInterfaceFrame();
		window.setVisible(true);
	}
}
