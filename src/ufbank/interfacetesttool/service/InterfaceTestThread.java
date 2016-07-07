package ufbank.interfacetesttool.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;

import ufbank.common.util.TextReader;
import ufbank.interfacetesttool.bean.TestTaskBean;
import ufbank.interfacetesttool.util.XMLUtil;
import ufbank.web.bean.InterfaceValidationBean;

public class InterfaceTestThread extends Thread {
	private int taskNum;
	private DefaultTableModel tableModel;
	private TestTaskBean task;
	private CountDownLatch count;

	public void run() {
		task.setStatu(TestTaskBean.statuMode.TESTING);
		tableModel.fireTableCellUpdated(taskNum, 4);
		InterfaceValidationBean ivb = new InterfaceValidationBean();
		ivb.setContent(task.getTran().getReciveNCData());
		try {
			ivb.validateInterface();
			String resContent = ivb.getReturnContent();
			if (isSuccess(resContent)) {
				task.setStatu(TestTaskBean.statuMode.SUCCESS);
				tableModel.fireTableCellUpdated(taskNum, 4);
			} else {
				task.setStatu(TestTaskBean.statuMode.FAILED);
				tableModel.fireTableCellUpdated(taskNum, 4);
			}
			count.countDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ≈–∂œ «∑Ò≥…π¶
	 * 
	 * @param resContent
	 * @return
	 */
	private boolean isSuccess(String resContent) {
		boolean result = false;
		try {
			Document doc = TextReader.ReadXmlContent(resContent);
			Element root = doc.getRootElement();
			String rootName = root.getName();
			if (rootName.equals(XMLUtil.NC_ROOT_TAG)) {
				List<Element> rows = root.elements(XMLUtil.NC_NODE_TAG);
				if (rows.size() > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setTask(TestTaskBean task) {
		this.task = task;
	}

	public void setCount(CountDownLatch count) {
		this.count = count;
	}

}
