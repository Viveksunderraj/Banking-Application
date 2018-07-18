
public class Beneficiary {
	
	private int customerID;
	private String bankName;
	private int beneficiaryAccountNumber;
	private String IFSCcode;
	
	
	public Beneficiary() {
		super();
	}

	public Beneficiary(int customerID, String bankName, int beneficiaryAccountNumber, String iFSCcode) {
		this.customerID = customerID;
		this.bankName = bankName;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		IFSCcode = iFSCcode;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(int beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getIFSCcode() {
		return IFSCcode;
	}

	public void setIFSCcode(String iFSCcode) {
		IFSCcode = iFSCcode;
	}

	@Override
	public String toString() {
		return "Beneficiary [\nCustomerID = " + customerID + ",\nBank Name = " + bankName + ",\nBeneficiary AccountNumber = "
				+ beneficiaryAccountNumber + ",\nIFSCcode = " + IFSCcode + "]";
	}
	
	
	

}
