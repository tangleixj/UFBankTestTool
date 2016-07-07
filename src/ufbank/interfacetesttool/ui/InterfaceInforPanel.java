package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ufbank.interfacetesttool.bean.InterfaceInforBean;
import ufbank.interfacetesttool.service.InterfaceTestService;
import ufbank.interfacetesttool.util.ViewUtil;

/**
 * 接口信息面板
 * 
 * @author TonyTang
 *
 */
public class InterfaceInforPanel extends JFrame {

	private JPanel contentPane;

	private JComboBox<String> selecctBankName;
	private DefaultComboBoxModel<String> bankNameModel;
	private JComboBox<String> selectBusinessName;
	private DefaultComboBoxModel<String> businessNameModel;
	private JTextField interfaceName;

	private JTextArea reciveNCDataText;
	private JTextArea sendBankDataText;
	private JTextArea reciveBankDataText;
	private JTextArea sendNCDataText;

	private JButton saveButton;
	private JButton cancelButton;
	private JTextField statuText;
	private InterfaceTestService service;
	private InterfaceInforBean tran;

	private boolean isAddNewInterface;

	public void setStatuText(JTextField statuText) {
		this.statuText = statuText;
	}

	public void setService(InterfaceTestService service) {
		this.service = service;
	}

	/**
	 * 初始化基本信息面板
	 * 
	 * @param panel
	 */
	private void initBaseInforPanel(JPanel panel) {
		panel.add(new JLabel(ViewUtil.BANK_TYPE_ZH));
		bankNameModel = new DefaultComboBoxModel<String>();
		selecctBankName = new JComboBox<String>();
		selecctBankName.setModel(bankNameModel);
		selecctBankName.setPreferredSize(new Dimension(120, 25));
		selecctBankName.setEditable(false);
		panel.add(selecctBankName);

		panel.add(new JLabel(""));

		panel.add(new JLabel(ViewUtil.BUSINESS_TYPE_ZH));
		businessNameModel = new DefaultComboBoxModel<String>();
		selectBusinessName = new JComboBox<String>();
		selectBusinessName.setModel(businessNameModel);
		selectBusinessName.setPreferredSize(new Dimension(120, 25));
		selectBusinessName.setEditable(false);
		panel.add(selectBusinessName);

		panel.add(new JLabel(""));

		panel.add(new JLabel(ViewUtil.INTERFACE_TYPE_ZH));
		interfaceName = new JTextField();
		interfaceName.setColumns(10);
		panel.add(interfaceName);
	}

	/**
	 * 初始化工具栏面板
	 */
	private void initOperationPanel(JPanel panel) {
		saveButton = new JButton(ViewUtil.SAVE_ZH);
		panel.add(saveButton);

		cancelButton = new JButton(ViewUtil.CANCEL_ZH);
		panel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isAddNewInterface) {
					String bankName = selecctBankName.getSelectedItem().toString();
					String bankCode = service.getBankFlagByBankName(bankName);
					String businessName = selectBusinessName.getSelectedItem().toString();
					String tranCode = interfaceName.getText().trim();
					String reciveNCData = reciveNCDataText.getText();
					String sendBankData = sendBankDataText.getText();
					String reciveBankData = reciveBankDataText.getText();
					String sendNCData = sendNCDataText.getText();
					InterfaceInforBean tran = new InterfaceInforBean(tranCode, bankName, bankCode, businessName,
							reciveNCData, sendBankData, reciveBankData, sendNCData);
					service.addNewInterface(tran);
					JOptionPane.showMessageDialog(new JPanel(), "添加成功", "提示", JOptionPane.NO_OPTION);
				} else {
					service.updateTran(tran, reciveNCDataText.getText(), sendBankDataText.getText(),
							reciveBankDataText.getText(), sendNCDataText.getText());
					JOptionPane.showMessageDialog(new JPanel(), "保存成功", "提示", JOptionPane.NO_OPTION);
				}
				// setVisible(false);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceInforPanel() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 642, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		ViewUtil.showCenter(this);
		/**
		 * 添加基本信息面板
		 */
		JPanel baseInforPanel = new JPanel();
		contentPane.add(baseInforPanel, BorderLayout.NORTH);
		initBaseInforPanel(baseInforPanel);

		/**
		 * 添加报文面板
		 */
		reciveNCDataText = new JTextArea();
		sendBankDataText = new JTextArea();
		reciveBankDataText = new JTextArea();
		sendNCDataText = new JTextArea();
		JPanel reciveNCPanel = ViewUtil.buildTextAreaPanel(reciveNCDataText, ViewUtil.RECIVE_NC_DATA_ZH);
		JPanel sendBankPanel = ViewUtil.buildTextAreaPanel(sendBankDataText, ViewUtil.SEND_BANK_DATA_ZH);
		JPanel reciveBankPanel = ViewUtil.buildTextAreaPanel(reciveBankDataText, ViewUtil.RECIVE_BANK_DATA_ZH);
		JPanel sendNCPanel = ViewUtil.buildTextAreaPanel(sendNCDataText, ViewUtil.SEND_NC_DATA_ZH);
		contentPane.add(ViewUtil.build(reciveNCPanel, sendBankPanel, reciveBankPanel, sendNCPanel),
				BorderLayout.CENTER);

		/**
		 * 添加工具栏面板
		 */
		JPanel operationPanel = new JPanel();
		contentPane.add(operationPanel, BorderLayout.SOUTH);
		initOperationPanel(operationPanel);
	}

	private void isAddNewInterface(boolean flag) {
		isAddNewInterface = flag;
		selecctBankName.setEnabled(flag);
		selectBusinessName.setEnabled(flag);
		interfaceName.setEditable(flag);
	}

	/**
	 * 初始化银行类别下拉框数据
	 */
	private void initSelectModel(JComboBox<String> select, DefaultComboBoxModel model, List<String> data) {
		model.removeAllElements();
		for (String item : data) {
			model.addElement(item);
		}
		select.setSelectedIndex(-1);
	}

	public void showInterfaceInfor(InterfaceInforBean tran) {
		isAddNewInterface(false);
		this.tran = tran;
		bankNameModel.removeAllElements();
		bankNameModel.addElement(tran.getBankName());
		selecctBankName.setSelectedIndex(0);
		businessNameModel.removeAllElements();
		businessNameModel.addElement(tran.getBusinessName());
		selectBusinessName.setSelectedIndex(0);
		interfaceName.setText(tran.getTranCode());
		reciveNCDataText.setText(tran.getReciveNCData());
		sendBankDataText.setText(tran.getSendBankData());
		reciveBankDataText.setText(tran.getReciveBankData());
		sendNCDataText.setText(tran.getSendNCData());
	}

	public void addNewInterface() {
		isAddNewInterface(true);
		initSelectModel(selecctBankName, bankNameModel, service.getBankTypeList());
		initSelectModel(selectBusinessName, businessNameModel, service.getBusinessList());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceInforPanel frame = new InterfaceInforPanel();
					frame.isAddNewInterface(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
