package ufbank.interfacetesttool.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import ufbank.common.util.PathUtil;
import ufbank.interfacetesttool.bean.DoubleKeyMap;
import ufbank.interfacetesttool.bean.InterfaceInforBean;
import ufbank.web.BankTestConstant;

/**
 * XML读写工具
 * 
 * @author TonyTang
 *
 */
public class XMLUtil {
	public static final String ROOT_TAG = "banks";
	public static final String NODE_BANK_TAG = "bank";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_BANK_CODE = "bankCode";
	public static final String ATTR_BUSINESS = "business";
	public static final String NODE_TRAN_TAG = "tran";
	public static final String ATTR_TRAN_CODE = "tranCode";
	public static final String NODE_RECIVE_NC_DATA_TAG = "reciveNCData";
	public static final String NODE_SEND_BANK_DATA_TAG = "sendBankData";
	public static final String NODE_RECIVE_BANK_DATA_TAG = "reciveBankData";
	public static final String NODE_SEND_NC_DATA_TAG = "sendNCData";

	public static final String NC_ROOT_TAG = "data";
	public static final String NC_NODE_TAG = "row";

	private static PathUtil pathUtil = new PathUtil();
	private static DoubleKeyMap<String, String> bankMapping = new DoubleKeyMap<String, String>();
	static {
		bankMapping = loadConfigFile();
	}

	public static DoubleKeyMap<String, String> getBankMapping() {
		return bankMapping;
	}

	/**
	 * 获取测试工具配置目录
	 */
	public static String getTestToolDir() {
		String path = null;
		try {
			path = pathUtil.getUpWebRoot() + BankTestConstant.CONFIG + File.separator + "ufbanktestconf";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 从config.xml中获取银行名称和银行简称
	 */
	private static DoubleKeyMap<String, String> loadConfigFile() {
		DoubleKeyMap<String, String> result = new DoubleKeyMap<String, String>();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(pathUtil.getUpWebRoot() + File.separator + "config.xml"));
			Element root = doc.getRootElement();
			Element bankList = root.element("bankList");
			List<Element> banks = bankList.elements();
			String bankName = null;
			String bankCode = null;
			for (Element bank : banks) {
				bankName = bank.attributeValue("displayName");
				bankCode = bank.attributeValue("name");
				result.put(bankName == null ? "" : bankName, bankCode == null ? "" : bankCode);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// private static void addTranNode(Element bankNode, InterfaceInforBean
	// tran) {
	// Element tranNode = bankNode.addElement(NODE_TRAN_TAG);
	// tranNode.addAttribute(ATTR_NAME, tran.getTranCode());
	// tranNode.addAttribute(ATTR_BUSINESS_TAG, tran.getBusinessName());
	// }
	//
	// private static void addBankNode(Element root, InterfaceInforBean tran) {
	// Element bankNode = root.addElement(NODE_BANK_TAG);
	// bankNode.addAttribute(ATTR_NAME, tran.getBankName());
	// bankNode.addAttribute(ATTR_CODE, tran.getBankCode());
	// addTranNode(bankNode, tran);
	// }

	/**
	 * 遍历配置文件查询该接口对应的银行业务节点
	 * 
	 */
	// private static void addNewNode(Element root, InterfaceInforBean tran) {
	// boolean result = false;
	// List<Element> banks = root.elements();
	// for (Element bankNode : banks) {
	// String bankName = bankNode.attributeValue(ATTR_NAME);
	// if (bankName.equals(tran.getBankName())) {
	// List<Element> tranList = bankNode.elements();
	// for (Element tranNode : tranList) {
	// String tranName = tranNode.attributeValue(ATTR_NAME);
	// if (tranName.equals(tran.getTranCode())) {
	// bankNode.remove(tranNode);
	// addTranNode(bankNode, tran);
	// result = true;
	// break;
	// }
	// }
	// if (!result) {
	// addTranNode(bankNode, tran);
	// result = true;
	// }
	// break;
	// }
	// }
	// if (!result) {
	// addBankNode(root, tran);
	// }
	// }

	/**
	 * 校验父级目录
	 */
	public static void checkParemtDirPath(String filePath) {
		filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		File parentDir = new File(filePath);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

	/**
	 * 写入XML文件
	 */
	private static void write2XML(String filePath, Document doc) {
		// 设置文件编码
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent(" ");
		XMLWriter writer = null;
		try {
			checkParemtDirPath(filePath);
			// 将文件按照UTF-8打开
			PrintWriter out = new PrintWriter(filePath, "UTF-8");
			writer = new XMLWriter(out, xmlFormat);
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// /**
	// * 注册新接口
	// */
	// public static boolean registerNewTran(InterfaceInforBean tran) {
	// boolean result = false;
	// SAXReader reader = null;
	// XMLWriter writer = null;
	// try {
	// String path = getTestToolDir() + File.separator + "interface.xml";
	// reader = new SAXReader();
	// Document doc = reader.read(new File(path));
	// Element root = doc.getRootElement();
	// if (root == null) {
	// root = doc.addElement(ROOT_TAG);
	// }
	// addNewNode(root, tran);
	// write2XML(path, doc);
	// result = true;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// if (writer != null) {
	// try {
	// writer.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// return result;
	// }

	/**
	 * 生成新银行接口信息文件
	 */
	public static boolean buildNewTranInfor(InterfaceInforBean tran) {
		boolean result = false;
		try {
			String path = getTestToolDir() + File.separator + "bank" + File.separator + tran.getBankCode()
					+ File.separator + tran.getBusinessName() + "_" + tran.getTranCode() + ".xml";
			Document doc = DocumentHelper.createDocument();
			Element bankNode = doc.addElement(NODE_BANK_TAG);
			bankNode.addAttribute(ATTR_NAME, tran.getBankName());
			bankNode.addAttribute(ATTR_BANK_CODE, tran.getBankCode());
			bankNode.addAttribute(ATTR_BUSINESS, tran.getBusinessName());
			bankNode.addAttribute(ATTR_TRAN_CODE, tran.getTranCode());
			Element reciveNCDataNode = bankNode.addElement(NODE_RECIVE_NC_DATA_TAG);
			reciveNCDataNode.setText(tran.getReciveNCData());
			Element sendBankDataNode = bankNode.addElement(NODE_SEND_BANK_DATA_TAG);
			sendBankDataNode.setText(tran.getSendBankData());
			Element reciveBankDataNode = bankNode.addElement(NODE_RECIVE_BANK_DATA_TAG);
			reciveBankDataNode.setText(tran.getReciveBankData());
			Element sendNCDataNode = bankNode.addElement(NODE_SEND_NC_DATA_TAG);
			sendNCDataNode.setText(tran.getSendNCData());

			write2XML(path, doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取已注册的银行
	 */
	public static Set<String> getRegisteredBankTypes() {
		Set<String> result = new HashSet<String>();
		String path = getTestToolDir() + File.separator + "bank";
		File dir = new File(path);
		for (File child : dir.listFiles()) {
			String bankCode = child.getName();
			String bankName = bankMapping.getKeyByValue(bankCode);
			result.add(bankName);
		}
		return result;
	}

	/**
	 * 从文件名中截取业务名称
	 */
	private static String getBusinessName(String fileName) {
		return fileName.substring(0, fileName.indexOf("_"));
	}

	/**
	 * 从文件名中截取交易码
	 * 
	 */
	private static String getTranCode(String fileName) {
		return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
	}

	/**
	 * 获取已注册的银行业务
	 */
	public static Set<String> getRegisteredBusinessType(String bankCode) {
		Set<String> result = new HashSet<String>();
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode;
		File dir = new File(path);
		for (File child : dir.listFiles()) {
			String fileName = child.getName();
			String businessName = getBusinessName(fileName);
			result.add(businessName);
		}
		return result;
	}

	/**
	 * 获取已注册的接口交易码
	 */
	public static Set<String> getRegisteredInterfaceType(String bankCode, String businessName) {
		Set<String> result = new HashSet<String>();
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode;
		File dir = new File(path);
		for (File child : dir.listFiles()) {
			String fileName = child.getName();
			if (fileName.startsWith(businessName)) {
				String tranCode = getTranCode(fileName);
				result.add(tranCode);
			}
		}
		return result;
	}

	/**
	 * 从接口信息文件中提取接口信息
	 * 
	 */
	private static InterfaceInforBean buildInterfaceInforBean(String path) {
		InterfaceInforBean tran = new InterfaceInforBean();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(path));
			Element bank = doc.getRootElement();
			String bankName = bank.attributeValue(ATTR_NAME);
			String reciveNCData = bank.element(NODE_RECIVE_NC_DATA_TAG).getText();
			String sendBankData = bank.element(NODE_SEND_BANK_DATA_TAG).getText();
			String reciveBankData = bank.elementText(NODE_RECIVE_BANK_DATA_TAG);
			String sendNCData = bank.elementText(NODE_SEND_NC_DATA_TAG);
			String tranCode = bank.attributeValue(ATTR_TRAN_CODE);
			String bankCode = bank.attributeValue(ATTR_BANK_CODE);
			String businessName = bank.attributeValue(ATTR_BUSINESS);
			tran.setTranCode(tranCode);
			tran.setBankCode(bankCode);
			tran.setBankName(bankName);
			tran.setBusinessName(businessName);
			tran.setReciveNCData(reciveNCData);
			tran.setSendBankData(sendBankData);
			tran.setReciveBankData(reciveBankData);
			tran.setSendNCData(sendNCData);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tran;
	}

	/**
	 * 获取接口信息
	 */
	public static InterfaceInforBean getTran(String bankCode, String businessName, String tranCode) {
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode + File.separator
				+ businessName + "_" + tranCode + ".xml";
		return buildInterfaceInforBean(path);
	}

	/**
	 * 获取接口信息
	 */
	public static InterfaceInforBean getTran(String bankCode, String tranCode) {
		InterfaceInforBean tran = null;
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode;
		File dir = new File(path);
		for (File child : dir.listFiles()) {
			String fileName = child.getName();
			if (fileName.contains(tranCode)) {
				tran = buildInterfaceInforBean(path + File.separator + fileName);
				break;
			}
		}
		return tran;
	}

	public static Set<InterfaceInforBean> getTranList() {
		Set<InterfaceInforBean> result = new HashSet<InterfaceInforBean>();
		String path = getTestToolDir() + File.separator + "bank";
		File dir = new File(path);
		InterfaceInforBean tran = null;
		for (File child : dir.listFiles()) {
			for (File tranFile : child.listFiles()) {
				tran = buildInterfaceInforBean(
						path + File.separator + child.getName() + File.separator + tranFile.getName());
				result.add(tran);
			}
		}
		return result;
	}

	/**
	 * 获取接口信息
	 */
	public static Set<InterfaceInforBean> getTranList(String bankCode) {
		Set<InterfaceInforBean> result = new HashSet<InterfaceInforBean>();
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode;
		File dir = new File(path);
		InterfaceInforBean tran = null;
		for (File child : dir.listFiles()) {
			String fileName = child.getName();
			tran = buildInterfaceInforBean(path + File.separator + fileName);
			result.add(tran);
		}
		return result;
	}

	/**
	 * 获取接口信息
	 */
	public static Set<InterfaceInforBean> getTranList(String bankCode, String businessName) {
		Set<InterfaceInforBean> result = new HashSet<InterfaceInforBean>();
		String path = getTestToolDir() + File.separator + "bank" + File.separator + bankCode;
		File dir = new File(path);
		InterfaceInforBean tran = null;
		for (File child : dir.listFiles()) {
			String fileName = child.getName();
			if (fileName.startsWith(businessName)) {
				tran = buildInterfaceInforBean(path + File.separator + fileName);
				result.add(tran);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		InterfaceInforBean tran = new InterfaceInforBean("782", "中国银行", "cb", "mx",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><banks></banks>", "", "", "");
		// buildNewTranInfor(tran);
		// System.out.println(getTran("cb", "780"));
		System.out.println(getTranList("cb", "ye").size());
		// System.out.println(getRegisteredBusinessType("cb"));
		// System.err.println(getRegisteredInterfaceType("cb", "ye"));
		// System.out.println(getInterfaceInforBean("cb", "ye", "780"));
		// System.out.println(registeredBusinessType("cb"));
		// System.out.println(getRegisteredInterfaceType("cb", "mx"));
		// System.out.println(getInterfaceInforBean("cb", "mx", "789"));
		// System.out.println(getRegisteredBankTypes());
		// System.out.println(getRegisteredBankTypes());
	}
}
