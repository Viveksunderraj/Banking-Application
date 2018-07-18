
public class Bank {
	private int bankID;
	private String bankName;
	private String bankAddress;
	private String adminPassword;
	
	public boolean validateAdmin(String adminPassword) {
		return (this.adminPassword.equals(adminPassword));
	}

	public int getBankID() {
		return bankID;
	}

	public void setBankID(int bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	@Override
	public String toString() {
		return "Bank [\nBank ID = " + bankID + ",\nBank Name = " + bankName + ",\nBank Address = " + bankAddress + "\n]";
	}
	
	
}
