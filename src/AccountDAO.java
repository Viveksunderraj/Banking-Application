import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
	
	
	public boolean addcustomerAccount(Account newAccount) {
		int affectedRows = 0;
		String sql = "insert into Accounts(AccountNumber ,BranchID, CustomerID, AccountType, AccountBalance) values(null,?,?,?,?)";

		try (Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, newAccount.getBranchID());
			pstmt.setInt(2, newAccount.getCustomerID());
			pstmt.setInt(3, newAccount.getAccountType());
			pstmt.setDouble(4, newAccount.getAccountBalance());
			
			affectedRows = pstmt.executeUpdate();
			
			if(affectedRows == 1) {
				return true;
			}
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public Account extractAccountFromReselutSet(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setAccountNumber(rs.getInt("AccountNumber"));
		account.setAccountBalance(rs.getDouble("AccountBalance"));
		account.setAccountName(rs.getString("AccountName"));
		return account;
		
	}
	
	
	public boolean validateAccountNumber(int accountNumber) {
		String sql = "select * from accounts where AccountNumber=?";
		
		try(Connection con = BankApplication.getConnection();
			PreparedStatement pstmt =  con.prepareStatement(sql);) {
			
			pstmt.setInt(1, accountNumber);
			
			try(ResultSet rs = pstmt.executeQuery();) {
				if(rs.next()) {
					return true;
				}
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public ArrayList<Account> getAccountsForCustomer(int customerID, int branchID, 	MenuMethods.GetAccountType accountType) {
		
		String sql = null;
		
		if(accountType == MenuMethods.GetAccountType.SAVINGS_AND_CURRENT) {
			sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance, acc.AccountType from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?) and (acc.AccountType=1 or acc.AccountType=2)";
		}
		else if(accountType == MenuMethods.GetAccountType.LOAN_ACCOUNT) {
			sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance, acc.AccountType from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?) and (acc.AccountType=3)";
		}
		else if(accountType == MenuMethods.GetAccountType.ALL_ACCOUNTS) {
			sql = "SELECT acc.AccountNumber, act.AccountName, acc.AccountBalance from accounts as acc INNER JOIN accountType as act ON acc.accountType=act.accountType where (CustomerID=?) and (BranchID=?)";
		}
		
		ArrayList<Account> accountList = new ArrayList<Account>();
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt =  con.prepareStatement(sql);) {
			
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, branchID);
			
			try(ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				Account account = extractAccountFromReselutSet(rs);
				accountList.add(account);
			}
			return accountList;
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
	}

	public static double getAccountBalance(int accountNumber) {
		String sql = "select accountBalance from Accounts where AccountNumber=?";
		double balance = 0;
		try(Connection con = BankApplication.getConnection();
			PreparedStatement pstmt =  con.prepareStatement(sql);) {
			
			pstmt.setInt(1, accountNumber);
			try(ResultSet rs = pstmt.executeQuery();) {
				rs.next();
				balance = rs.getInt("AccountBalance");
				return balance;
			}	
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return balance;
	}
	
	public static boolean updateAccountBalance(double accountBalance, int accountNumber) {
		String sql = "update Accounts SET AccountBalance=? where AccountNumber=?";
		int affectedRows = 0;
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt =  con.prepareStatement(sql);) {
			
			pstmt.setDouble(1, accountBalance);
			pstmt.setInt(2, accountNumber);
			affectedRows = pstmt.executeUpdate();
			if(affectedRows == 1) {
				return true;
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public static int getAccountType(int accountNumber) {
		int accountType = 0;
		String sql = "select accountType from Accounts where AccountNumber=?";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, accountNumber);
			
			try(ResultSet rs = pstmt.executeQuery();) {
				rs.next();
				accountType = rs.getInt("AccountType");
				return accountType;
			}
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return accountType;
		
	}
	
}
