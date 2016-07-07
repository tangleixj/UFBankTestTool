package ufbank.interfacetesttool.bean;

import ufbank.interfacetesttool.util.ViewUtil;

/**
 * 测试任务信息
 * 
 * @author TonyTang
 *
 */
public class TestTaskBean {
	private InterfaceInforBean tran;
	private statuMode statu = statuMode.READY;
	private boolean isSelected;

	public static enum statuMode {
		READY(0, ViewUtil.READ_ZH), TESTING(1, ViewUtil.TESTING_ZH), SUCCESS(2, ViewUtil.SUCCESS_ZH), FAILED(3,
				ViewUtil.FAILED_ZH);
		private int num;
		private String showText;

		statuMode(int num, String showText) {
			this.num = num;
			this.showText = showText;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public String getShowText() {
			return showText;
		}

		public void setShowText(String showText) {
			this.showText = showText;
		}
	}

	public InterfaceInforBean getTran() {
		return tran;
	}

	public void setTran(InterfaceInforBean tran) {
		this.tran = tran;
	}

	public statuMode getStatu() {
		return statu;
	}

	public void setStatu(statuMode statu) {
		this.statu = statu;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public TestTaskBean(InterfaceInforBean tran) {
		super();
		this.tran = tran;
		this.isSelected = false;
	}

	public TestTaskBean() {
		super();
	}

	@Override
	public String toString() {
		return "TestTaskBean [tran=" + tran + ", statu=" + statu +",isSelected="+isSelected+ "]";
	}
}
