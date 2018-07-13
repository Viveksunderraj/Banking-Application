import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account{
	private int branchID;
	private int customerID;
	private int accountType;
	private double accountBalance;
	
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
	
	public void addCustomerAccount() {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "insert into Accounts(AccountNumber ,BranchID, CustomerID, AccountType, AccountBalance) values(null,?,?,?,?)";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, this.branchID);
				pstmt.setInt(2, this.customerID);
				pstmt.setInt(3, this.accountType);
				pstmt.setDouble(4, this.accountBalance);
				pstmt.executeUpdate();
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
			}
			
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	
	 public static boolean validateAccountNumber(int customerID, int accountNumber) {
		 Connection con = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "select * from accounts where CustomerID!=? and AccountNumber=?";
				
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, customerID);
					pstmt.setInt(2, accountNumber);
					ResultSet rs = pstmt.executeQuery();

					if(rs.next()) {
						return true;
					}
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
			return false;
	}	
	 
	 //DISPLAYS ONLY SAVINGS AND CURRENT ACCOUNTS OF A CUSTOMER
	 public static void displayCustomerAccounts(int customerID, int branchID) {
		 
		 Connection con = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?) and (acc.AccountType!=3)";
				
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, customerID);
					pstmt.setInt(2, branchID);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						System.out.println("Account Number = " + rs.getString("AccountNumber") + ", Account Type = " + rs.getString("AccountName") + ", Account Balance = " + rs.getDouble("AccountBalance"));
					}

				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}		 
	 }
	 
	 //DISPLAYS ONLY SAVINGS AND CURRENT ACCOUNTS OF A CUSTOMER WHERE THE PARAMETERED ACCOUNT NUMBER WILL NOT BE SHOWN(FOR TRANSFER WITHIN)
	 public static void displayCustomerAccounts(int customerID, int branchID, int accountNumber) {
		 
		 Connection con = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?) and (acc.AccountType!=3) and (AccountNumber!=?)";
				
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, customerID);
					pstmt.setInt(2, branchID);
					pstmt.setInt(3, accountNumber);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						System.out.println("Account Number = " + rs.getString("AccountNumber") + ", Account Type = " + rs.getString("AccountName") + ", Account Balance = " + rs.getDouble("AccountBalance"));
					}

				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}		 
	 }
	 
	 //DISPLAYS ONLY THE LOAN ACCOUNTS
	 public static void displayCustomerLoanAccounts(int customerID, int branchID) {
		 
		 	Connection con = null;
		 	int flag = 0;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?) and (acc.AccountType=3)";
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, customerID);
					pstmt.setInt(2, branchID);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						flag = 1;
						System.out.println("Account Number = " + rs.getString("AccountNumber") + ", Account Type = " + rs.getString("AccountName") + ", Account Balance = " + rs.getDouble("AccountBalance")*(-1));
					}
					if(flag == 0) {
						System.out.println("No Accounts Found!");
					}
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}		 
	 }
	 
	 //DISPLAYS ALL ACCOUNTS FOR A GIVEN CUSTOMER ID
	 public static void displayAllAccounts(int customerID) {
		 	Connection con = null;
		 	int flag = 0;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?)";
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, customerID);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						flag = 1;
						System.out.println("Account Number = " + rs.getString("AccountNumber") + ", Account Type = " + rs.getString("AccountName") + ", Account Balance = " + rs.getDouble("AccountBalance"));
					}
					if(flag == 0) {
						System.out.println("No Accounts Found!");
					}
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}		 
	 }

	 public static double getAccountBalance(int accountNumber) {
		 	Connection con = null;
			double balance;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "select accountBalance from Accounts where AccountNumber=?";
				
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, accountNumber);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					balance = rs.getInt("AccountBalance");
					return balance;
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
			return 0;
	 }
	 
	 public static int getAccountType(int accountNumber) {
		 	Connection con = null;
			int accountType;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
				String sql = "select accountType from Accounts where AccountNumber=?";
				
				try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
					pstmt.setInt(1, accountNumber);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					accountType = rs.getInt("AccountType");
					return accountType;
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
			return 0;
	 }
	 
}
