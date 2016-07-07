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
 * 视图工具类
 * 
 * @author TonyTang
 *
 */
public class ViewUtil {
	public static final String TITLE_ZH = "网银适配器自动测试工具";
	public static final String BANK_TYPE_ZH = "银行名称";
	public static final String BUSINESS_TYPE_ZH = "业务名称";
	public static final String INTERFACE_TYPE_ZH = "接口名称";
	public static final String VIEW_INFOR_ZH = "查看详情";
	public static final String ADD_INTERFACE_ZH = "新增接口";
	public static final String RECIVE_NC_DATA_ZH = "接受NC报文";
	public static final String SEND_BANK_DATA_ZH = "发送银行报文";
	public static final String RECIVE_BANK_DATA_ZH = "接受银行报文";
	public static final String SEND_NC_DATA_ZH = "发送NC报文";
	public static final String STATU_ZH = "状态";
	public static final String OPERATION_ZH = "操作";
	public static final String SELECT_ZH = "选择";

	public static final String OK_ZH = "确认";
	public static final String SAVE_ZH = "保存";
	public static final String CANCEL_ZH = "取消";

	public static final String TOOL_DIR = "ufbanktestconf";
	public static final String ICON_DIR = "icon";
	public static final String DATA_DIR = "bank";

	public static final String READ_ZH = "就绪";
	public static final String TESTING_ZH = "正在测试";
	public static final String SUCCESS_ZH = "成功";
	public static final String FAILED_ZH = "失败";

	public static final String VIEW_LOG_ZH = "查看日志";
	public static final String ALL_ZH = "所有";

	public static final String ADD_TASK_ZH = "添加测试任务";
	public static final String REMOVE_TASK_ZH = "删除测试任务";
	public static final String OPERATOR_ALL_ZH = "全选/取消";
	public static final String START_EXECUTE_ZH = "开始测试";

	/**
	 * 居中显示
	 */
	public static void showCenter(JFrame frame) {
		int windowWidth = frame.getWidth(); // 获得窗口宽
		int windowHeight = frame.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
	}

	/**
	 * 生成文本编辑区域
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
		ImageIcon ico = new ImageIcon(iconDirPath + File.separator + iconName + ".png"); // 图片
		JButton button = new JButton(text, ico);
		button.setIconTextGap(2);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		return button;
	}

	/**
	 * 四面板四角布局
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
	 * 获取图标目录路径
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
