package ufbank.interfacetesttool.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import edu.emory.mathcs.backport.java.util.Arrays;
import ufbank.interfacetesttool.bean.DoubleKeyMap;
import ufbank.interfacetesttool.bean.InterfaceInforBean;
import ufbank.interfacetesttool.bean.TestTaskBean;
import ufbank.interfacetesttool.util.XMLUtil;

public class InterfaceTestService {
	private JTextField statuText;
	private DefaultTableModel tableModel;

	private LinkedList<TestTaskBean> testPlan;
	private Set<Integer> selectedSet;
	private DoubleKeyMap<String, String> bankNameMapping;
	private boolean isSelectAll = false;

	public void setStatuText(JTextField statuText) {
		this.statuText = statuText;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public Set<Integer> getSelectedSet() {
		return selectedSet;
	}

	public InterfaceTestService() {
		super();
		this.testPlan = new LinkedList<TestTaskBean>();
		this.selectedSet = new TreeSet<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2 - o1;
			}

		});
		loadConfig();
	}

	public void addTask(TestTaskBean task) {
		testPlan.add(task);
		tableModel.addRow(new String[] { "", task.getTran().getBankName(), task.getTran().getBusinessName(),
				task.getTran().getTranCode(), task.getStatu().getShowText(), "" });
	}

	public void addMultiTask() {
		Set<InterfaceInforBean> trans = XMLUtil.getTranList();
		for (InterfaceInforBean tran : trans) {
			addTask(new TestTaskBean(tran));
		}
	}

	public void addMultiTask(String bankName) {
		Set<InterfaceInforBean> trans = XMLUtil.getTranList(getBankFlagByBankName(bankName));
		for (InterfaceInforBean tran : trans) {
			addTask(new TestTaskBean(tran));
		}
	}

	public void addMultiTask(String bankName, String businessName) {
		Set<InterfaceInforBean> trans = XMLUtil.getTranList(getBankFlagByBankName(bankName), businessName);
		for (InterfaceInforBean tran : trans) {
			addTask(new TestTaskBean(tran));
		}
	}

	public TestTaskBean getTask(int index) {
		if (index >= 0 && index < testPlan.size()) {
			return testPlan.get(index);
		}
		return null;
	}

	public void multiRemoveTask() {
		for (int index : selectedSet) {
			testPlan.remove(index);
			tableModel.removeRow(index);
		}
		selectedSet.removeAll(selectedSet);
	}

	public boolean isSelected(int index) {
		if (selectedSet.contains(index)) {
			return true;
		} else {
			return false;
		}
	}

	public void selectTask(int index) {
		if (!selectedSet.contains(index)) {
			selectedSet.add(index);
		}
	}

	public void cancleTask(int index) {
		if (selectedSet.contains(index)) {
			selectedSet.remove(index);
		}
	}

	public void operatorAll() {
		if (isSelectAll) {
			isSelectAll = false;
			selectedSet.removeAll(selectedSet);
		} else {
			isSelectAll = true;
			for (int i = 0; i < testPlan.size(); i++) {
				selectedSet.add(i);
			}
		}
		tableModel.fireTableRowsUpdated(0, tableModel.getRowCount());
	}

	/**
	 * 加载config配置
	 */
	private void loadConfig() {
		bankNameMapping = XMLUtil.getBankMapping();
	}

	/**
	 * 根据银行名称获取对应银行简称
	 * 
	 */
	public String getBankFlagByBankName(String bankName) {
		return bankNameMapping.getValueByKey(bankName);
	}

	/**
	 * 添加接口
	 */
	public boolean addNewInterface(InterfaceInforBean tran) {
		return XMLUtil.buildNewTranInfor(tran);
	}

	public List<String> getBankTypeList() {
		return bankNameMapping.keyList();
	}

	public static List<String> getBusinessList() {
		String[] data = new String[] { "ye", "mx", "zf", "zfcx", "dfdk", "dfdkcx", "dfgz", "dfgzcx", "jtgj" };
		return Arrays.asList(data);
	}

	public Set<String> getRegisteredBankNameList() {
		return XMLUtil.getRegisteredBankTypes();
	}

	public Set<String> getRegisteredBusinessNameList(String bankName) {
		return XMLUtil.getRegisteredBusinessType(bankNameMapping.getValueByKey(bankName));
	}

	public Set<String> getRegisteredInterfaceNameList(String bankName, String businessName) {
		return XMLUtil.getRegisteredInterfaceType(bankNameMapping.getValueByKey(bankName), businessName);
	}

	public InterfaceInforBean getTran(String bankName, String tranCode) {
		return XMLUtil.getTran(bankNameMapping.getValueByKey(bankName), tranCode);
	}

	public void updateTran(InterfaceInforBean tran, String reciveNCData, String sendBankData, String reciveBankData,
			String sendNCData) {
		boolean result = false;
		if (!tran.getReciveNCData().equals(reciveNCData)) {
			result = true;
			tran.setReciveNCData(reciveNCData);
		}
		if (!tran.getSendBankData().equals(sendBankData)) {
			result = true;
			tran.setSendBankData(sendBankData);
		}
		if (!tran.getReciveBankData().equals(reciveBankData)) {
			result = true;
			tran.setReciveBankData(reciveBankData);
		}
		if (!tran.getSendNCData().equals(sendNCData)) {
			result = true;
			tran.setSendNCData(sendNCData);
		}
		if (result) {
			XMLUtil.buildNewTranInfor(tran);
		}
	}

	public String getBankResMess(String bankCode, String tranCode) {
		return XMLUtil.getTran(bankCode, tranCode).getReciveBankData();
	}

	public boolean hasSelected() {
		if (selectedSet.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmpty() {
		if (testPlan.size() == 0) {
			return true;
		}
		return false;
	}

	public void executeMultiTask() {
		new Thread() {
			@Override
			public void run() {
				CountDownLatch count = new CountDownLatch(selectedSet.size());
				for (int index : selectedSet) {
					InterfaceTestThread test = new InterfaceTestThread();
					test.setTableModel(tableModel);
					test.setTask(testPlan.get(index));
					test.setTaskNum(index);
					test.setCount(count);
					test.start();
				}
				try {
					count.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(new JPanel(), "测试计划测试完毕", "提醒", JOptionPane.WARNING_MESSAGE);
			}
		}.start();

	}
}
