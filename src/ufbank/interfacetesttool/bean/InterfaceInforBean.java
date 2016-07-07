package ufbank.interfacetesttool.bean;

/**
 * 银行接口信息
 * 
 * @author TonyTang
 *
 */
public class InterfaceInforBean {
	private String tranCode;
	private String bankName;
	private String bankCode;
	private String businessName;
	private String reciveNCData;
	private String sendBankData;
	private String reciveBankData;
	private String sendNCData;

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getReciveNCData() {
		return reciveNCData;
	}

	public void setReciveNCData(String reciveNCData) {
		this.reciveNCData = reciveNCData;
	}

	public String getSendBankData() {
		return sendBankData;
	}

	public void setSendBankData(String sendBankData) {
		this.sendBankData = sendBankData;
	}

	public String getReciveBankData() {
		return reciveBankData;
	}

	public void setReciveBankData(String reciveBankData) {
		this.reciveBankData = reciveBankData;
	}

	public String getSendNCData() {
		return sendNCData;
	}

	public void setSendNCData(String sendNCData) {
		this.sendNCData = sendNCData;
	}

	public InterfaceInforBean(String tranCode, String bankName, String bankCode, String businessName,
			String reciveNCData, String sendBankData, String reciveBankData, String sendNCData) {
		super();
		this.tranCode = tranCode;
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.businessName = businessName;
		this.reciveNCData = reciveNCData;
		this.sendBankData = sendBankData;
		this.reciveBankData = reciveBankData;
		this.sendNCData = sendNCData;
	}

	public InterfaceInforBean() {
		super();
	}

	@Override
	public String toString() {
		return "InterfaceInforBean [tranCode=" + tranCode + ", bankName=" + bankName + ", bankCode=" + bankCode
				+ ", businessName=" + businessName + ", reciveNCData=" + reciveNCData + ", sendBankData=" + sendBankData
				+ ", reciveBankData=" + reciveBankData + ", sendNCData=" + sendNCData + "]";
	}

}
