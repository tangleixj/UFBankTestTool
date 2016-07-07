package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ufbank.interfacetesttool.service.InterfaceTestService;
import ufbank.interfacetesttool.util.ViewUtil;

/**
 * 主界面
 * 
 * @author TonyTang
 *
 */
public class MainView {

	private JFrame frame;
	private JTable testPlanTable;
	private DefaultTableModel tableModel;
	private JTextField statuText;
	private JButton addTestTaskButton;
	private JButton removeTestTaskButton;
	private JButton selecctButton;
	private JButton startButton;

	private InterfaceTestService service;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * 初始化工具栏
	 */
	private void initToolBar(JToolBar toolBar) {
		addTestTaskButton = ViewUtil.buildToolbarButton("add", ViewUtil.ADD_TASK_ZH);
		toolBar.add(addTestTaskButton);

		addTestTaskButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SelectInterfaceFrame window = new SelectInterfaceFrame();
				window.setStatuText(statuText);
				window.setService(service);
				window.setVisible(true);
			}
		});

		removeTestTaskButton = ViewUtil.buildToolbarButton("delete", ViewUtil.REMOVE_TASK_ZH);
		toolBar.add(removeTestTaskButton);

		removeTestTaskButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				if (service.hasSelected()) {
					testPlanTable.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
					service.multiRemoveTask();
					testPlanTable.setFocusable(false);
				} else {
					JOptionPane.showMessageDialog(new JPanel(), "未选择测试任务", "提醒", JOptionPane.WARNING_MESSAGE);
				}
				testPlanTable.setFocusable(false);
				testPlanTable.setRowSelectionInterval(0, 0);

			}
		});

		selecctButton = ViewUtil.buildToolbarButton("select", ViewUtil.OPERATOR_ALL_ZH);
		toolBar.add(selecctButton);

		selecctButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				if (service.isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "当前测试计划中无测试任务", "提醒", JOptionPane.WARNING_MESSAGE);
				} else {
					service.operatorAll();
					testPlanTable.setFocusable(false);
				}
			}
		});
		startButton = ViewUtil.buildToolbarButton("start", ViewUtil.START_EXECUTE_ZH);
		toolBar.add(startButton);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				if (service.hasSelected()) {
					service.executeMultiTask();
					testPlanTable.setFocusable(false);
				} else {
					JOptionPane.showMessageDialog(new JPanel(), "未选择测试任务", "提醒", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	/**
	 * 初始化测试计划表
	 */
	@SuppressWarnings("serial")
	private void initTestPlanTablePanel(JPanel panel) {
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		tableModel = new DefaultTableModel(new String[] { ViewUtil.SELECT_ZH, ViewUtil.BANK_TYPE_ZH,
				ViewUtil.BUSINESS_TYPE_ZH, ViewUtil.INTERFACE_TYPE_ZH, ViewUtil.STATU_ZH, ViewUtil.OPERATION_ZH }, 0) {
			@Override
			public boolean isCellEditable(int i, int j) {
				if (j > 0 && j < 5) {
					return false;
				}
				return true;
			}
		};
		testPlanTable = new JTable(tableModel);
		service.setTableModel(tableModel);
		testPlanTable.getTableHeader().setReorderingAllowed(false);
		testPlanTable.getTableHeader().setResizingAllowed(false);
		testPlanTable.setRowHeight(25);
		scrollPane.setViewportView(testPlanTable);

		TableColumn selectColumn = testPlanTable.getColumnModel().getColumn(0);
		selectColumn.setCellRenderer(new TableCellRadioButtonRender(service));
		selectColumn.setCellEditor(new TableCellRadioButtonEditor(new JTextField(), service));
		selectColumn.setPreferredWidth(10);

		TableColumn statuColumn = testPlanTable.getColumnModel().getColumn(4);
		statuColumn.setCellRenderer(new TableCellTestStatuRender(service));

		TableColumn operColumn = testPlanTable.getColumnModel().getColumn(5);
		operColumn.setCellRenderer(new TableCellOperatonPanelRender());
		operColumn.setCellEditor(new TableCellOperatonPanelEditor(new JTextField()));
	}

	/**
	 * 初始化状态栏
	 */
	private void initStatuPanel(JPanel panel) {
		statuText = new JTextField();
		statuText.setEditable(false);
		panel.add(statuText, BorderLayout.WEST);
		service.setStatuText(statuText);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		String iconDirPath = ViewUtil.getIconPath();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(iconDirPath + File.separator + "icon.png"));
		frame.setBounds(100, 100, 773, 547);
		ViewUtil.showCenter(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle(ViewUtil.TITLE_ZH);

		service = new InterfaceTestService();

		/**
		 * 状态栏
		 */
		// JPanel statuPanel = new JPanel(new BorderLayout());
		// frame.getContentPane().add(statuPanel, BorderLayout.SOUTH);
		// initStatuPanel(statuPanel);
		/**
		 * 添加工具栏
		 */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		initToolBar(toolBar);

		/**
		 * 测试计划表
		 */
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		initTestPlanTablePanel(panel);

	}

}
