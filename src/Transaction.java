import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {

	private int transactionID;
	private double transactionAmount;
	private double accountBalance;
	private int customerID;
	private int branchID;
	private int transactionAccountNumber1, transactionAccountNumber2;
	private int transactionType;
	private int transactionSubType;
	private String transactionDateTime;
	private String description;
	private String transactionName;
	
	
	public enum TransactionType {
		DEPOSIT(1), WITHDRAW(2), TRANSFER_WITHIN_ACOOUNTS(3), TRANSFER_BETWEEN_ACCOUNTS(4), REPAY_LOAN_THROUGH_DEPOSIT(5),
		REPAY_LOAN_THROUGH_TRANSFER(6);
		
		private int value;  
		private TransactionType(int value){  
		this.value=value;  
		}
		public int getValue() {
			return value;
		}
	}
	
	public Transaction() {
		super();
	}


	public Transaction(double transactionAmount, double accountBalance, int customerID, int branchID,
			int transactionAccountNumber1, int transactionAccountNumber2, int transactionType, int transactionSubType,
			String transactionDateTime, String description) {
		this.transactionAmount = transactionAmount;
		this.accountBalance = accountBalance;
		this.customerID = customerID;
		this.branchID = branchID;
		this.transactionAccountNumber1 = transactionAccountNumber1;
		this.transactionAccountNumber2 = transactionAccountNumber2;
		this.transactionType = transactionType;
		this.transactionSubType = transactionSubType;
		this.transactionDateTime = transactionDateTime;
		this.description = description;
	}
	
	
	
	public int getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getBranchID() {
		return branchID;
	}

	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}

	public int getTransactionAccountNumber1() {
		return transactionAccountNumber1;
	}

	public void setTransactionAccountNumber1(int transactionAccountNumber1) {
		this.transactionAccountNumber1 = transactionAccountNumber1;
	}

	public int getTransactionAccountNumber2() {
		return transactionAccountNumber2;
	}

	public void setTransactionAccountNumber2(int transactionAccountNumber2) {
		this.transactionAccountNumber2 = transactionAccountNumber2;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public int getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(int transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getTransactionName() {
		return transactionName;
	}


	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}


	public static void displayTransactionHistory(int customerID, int accountNumber) {
		Connection con = null;
	 	int flag = 0;
	 	//String subType =  null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "SELECT * from transactions as tran INNER JOIN transactionType as trantype ON tran.transactionType=trantype.transactionType where (CustomerID=?) and (transactionAccountNumber1=?)";
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, customerID);
				pstmt.setInt(2, accountNumber);
				ResultSet rs = pstmt.executeQuery();
				
				/*if(rs.getInt("TransactionSubType") == 1) {
					subType = "Credit";
				}
				else {
					subType = "Debit";
				}*/
				while(rs.next()) {
					BankApplication.printSeparator();
					System.out.println("Transaction Type = " + rs.getString("TransactionName") + 
									   ", Transaction Sub Type = " + rs.getString("TransactionSubtype") +
									   "\nTransaction Date & Time = " + rs.getString("transactiondatetime") +
									   "\nTransaction Amount = " + rs.getDouble("Amount") +
									   "\nAccount Balance = " + rs.getDouble("CurrentBalance") +
									   "\nMeassage = " + rs.getString("Description"));
					flag = 1;
				}
				if(flag == 0) {
					System.out.println("No Transactions Found!");
				}
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage() + "HELLO");
			}
			
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}		 
	}
	
}
