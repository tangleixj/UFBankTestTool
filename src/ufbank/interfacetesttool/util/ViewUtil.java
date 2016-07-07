package ufbank.interfacetesttool.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import ufbank.common.util.PathUtil;
import ufbank.web.BankTestConstant;

/**
 * ��ͼ������
 * 
 * @author TonyTang
 *
 */
public class ViewUtil {
	public static final String TITLE_ZH = "�����������Զ����Թ���";
	public static final String BANK_TYPE_ZH = "��������";
	public static final String BUSINESS_TYPE_ZH = "ҵ������";
	public static final String INTERFACE_TYPE_ZH = "�ӿ�����";
	public static final String VIEW_INFOR_ZH = "�鿴����";
	public static final String ADD_INTERFACE_ZH = "�����ӿ�";
	public static final String RECIVE_NC_DATA_ZH = "����NC����";
	public static final String SEND_BANK_DATA_ZH = "�������б���";
	public static final String RECIVE_BANK_DATA_ZH = "�������б���";
	public static final String SEND_NC_DATA_ZH = "����NC����";
	public static final String STATU_ZH = "״̬";
	public static final String OPERATION_ZH = "����";
	public static final String SELECT_ZH = "ѡ��";

	public static final String OK_ZH = "ȷ��";
	public static final String SAVE_ZH = "����";
	public static final String CANCEL_ZH = "ȡ��";

	public static final String TOOL_DIR = "ufbanktestconf";
	public static final String ICON_DIR = "icon";
	public static final String DATA_DIR = "bank";

	public static final String READ_ZH = "����";
	public static final String TESTING_ZH = "���ڲ���";
	public static final String SUCCESS_ZH = "�ɹ�";
	public static final String FAILED_ZH = "ʧ��";

	public static final String VIEW_LOG_ZH = "�鿴��־";
	public static final String ALL_ZH = "����";

	public static final String ADD_TASK_ZH = "��Ӳ�������";
	public static final String REMOVE_TASK_ZH = "ɾ����������";
	public static final String OPERATOR_ALL_ZH = "ȫѡ/ȡ��";
	public static final String START_EXECUTE_ZH = "��ʼ����";

	/**
	 * ������ʾ
	 */
	public static void showCenter(JFrame frame) {
		int windowWidth = frame.getWidth(); // ��ô��ڿ�
		int windowHeight = frame.getHeight(); // ��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
		Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; // ��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; // ��ȡ��Ļ�ĸ�
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// ���ô��ھ�����ʾ
	}

	/**
	 * �����ı��༭����
	 * 
	 * @param textArea
	 * @param text
	 * @return
	 */
	public static JPanel buildTextAreaPanel(JTextArea textArea, String text) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(text));
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}

	public static JButton buildToolbarButton(String iconName, String text) {
		String iconDirPath = ViewUtil.getIconPath();
		ImageIcon ico = new ImageIcon(iconDirPath + File.separator + iconName + ".png"); // ͼƬ
		JButton button = new JButton(text, ico);
		button.setIconTextGap(2);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		return button;
	}

	/**
	 * ������Ľǲ���
	 * 
	 * @param leftTopPanle
	 * @param rightTopPanel
	 * @param leftBottomPanel
	 * @param rigthBottomPanel
	 * @return
	 */
	public static JSplitPane build(JPanel leftTopPanle, JPanel rightTopPanel, JPanel leftBottomPanel,
			JPanel rigthBottomPanel) {
		JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		hSplit.setResizeWeight(0.5);

		JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		leftSplit.setTopComponent(leftTopPanle);
		leftSplit.setBottomComponent(leftBottomPanel);
		leftSplit.setResizeWeight(0.5);

		JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightSplit.setTopComponent(rightTopPanel);
		rightSplit.setBottomComponent(rigthBottomPanel);
		rightSplit.setResizeWeight(0.5);

		hSplit.setLeftComponent(leftSplit);
		hSplit.setRightComponent(rightSplit);

		return hSplit;
	}

	/**
	 * ��ȡͼ��Ŀ¼·��
	 * 
	 * @return
	 */
	public static String getIconPath() {
		String path = null;
		PathUtil util = new PathUtil();
		try {
			path = util.getUpWebRoot() + BankTestConstant.CONFIG + File.separator + TOOL_DIR + File.separator
					+ ICON_DIR;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}
