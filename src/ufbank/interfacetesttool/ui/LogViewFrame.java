package ufbank.interfacetesttool.ui;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import ufbank.interfacetesttool.util.ViewUtil;

/**
 * 日志展现面板
 * 
 * @author TonyTang
 *
 */
public class LogViewFrame extends JFrame {

	private JTextArea logContent;
	private String logPath;

	/**
	 * Create the frame.
	 */
	public LogViewFrame(String filePath) {
		this.setTitle(filePath);

		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 398);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		logContent = new JTextArea();
		scrollPane.setViewportView(logContent);
		loadContent(filePath);
		ViewUtil.showCenter(this);
	}

	/**
	 * 载入日志内容
	 * 
	 * @param path
	 *            文件路径
	 */
	private void loadContent(String path) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = reader.readLine()) != null) {
				logContent.append(line + "\n");
				// logContent.paintImmediately(logContent.getBounds());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
