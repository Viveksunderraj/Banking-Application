
public class Account{
	
	private int accountNumber;
	private int branchID;
	private int customerID;
	private int accountType;
	private double accountBalance;
	private String accountName;
	
	
	
	public Account() {
		super();
	}

	public Account(int branchID, int customerID, int accountType, double accountBalance) {
		this.branchID = branchID;
		this.customerID = customerID;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}

	public enum AccountType {
		SAVINGS(1), CURRENT(2), LOAN(3);
		
		private int value;  
		private AccountType(int value){  
		this.value=value;  
		}
		public int getValue() {
			return value;
		}

	}

	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	


	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getBranchID() {
		return branchID;
	}


	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}


	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public int getAccountType() {
		return accountType;
	}


	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}


	public double getAccountBalance() {
		return accountBalance;
	}


	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	 
	 
	 
	 
}
