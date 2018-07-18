import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionDAO {
	
	public static boolean addTransactionDetails(Transaction newTransaction) {
		
		String sql = "insert into Transactions(TransactionID, TransactionType, BranchID, CustomerID, TransactionAccountNumber1, TransactionAccountNumber2, Amount, CurrentBalance, TransactionDateTIme, TransactionSubType, Description) values(null,?,?,?,?,?,?,?,NOW(),?,?)";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, newTransaction.getTransactionType());
			pstmt.setInt(2, newTransaction.getBranchID());
			pstmt.setInt(3, newTransaction.getCustomerID());
			pstmt.setInt(4, newTransaction.getTransactionAccountNumber1());
			pstmt.setInt(5, newTransaction.getTransactionAccountNumber2());
			pstmt.setDouble(6, newTransaction.getTransactionAmount());
			pstmt.setDouble(7, newTransaction.getAccountBalance());
			pstmt.setInt(8, newTransaction.getTransactionSubType());
			pstmt.setString(9, newTransaction.getDescription());
			
			int affectedRows = pstmt.executeUpdate();
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
	
	public TransactionReportHeader getTransactionHeader(int accountNumber) {
		String sql = "SELECT banktable.bankname, branchtable.branchname, acctype.accountname, acc.accountNumber, acc.accountBalance from accounts as acc INNER JOIN  AccountType as acctype ON acc.accounttype=acctype.accounttype INNER JOIN BranchMaster as branchtable ON acc.branchID=branchtable.branchID INNER JOIN Bankmaster as banktable ON branchtable.bankID=banktable.bankID where acc.accountnumber=?";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, accountNumber);
			
			try(ResultSet rs = pstmt.executeQuery();) {
				TransactionReportHeader transactionReportHeader = new TransactionReportHeader();
				if(rs.next()) {
					transactionReportHeader.setBankName(rs.getString("Bankname"));
					transactionReportHeader.setBranchName(rs.getString("branchname"));
					transactionReportHeader.setAccountName(rs.getString("accountname"));
					transactionReportHeader.setAccountNumber(rs.getInt("accountNumber"));
					transactionReportHeader.setAccountBalance(rs.getDouble("AccountBalance"));
					return transactionReportHeader;
				}
			}
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage() + "HELLO");
		}
		return null;
		
	}
	
	public ArrayList<Transaction> getTransactionHistory(int customerID, int accountNumber) {
		String sql = "SELECT * from transactions as tran INNER JOIN transactionType as trantype ON tran.transactionType=trantype.transactionType where (CustomerID=?) and (transactionAccountNumber1=?) ORDER BY TransactionDateTIme DESC, TransactionID DESC";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, accountNumber);
			try(ResultSet rs = pstmt.executeQuery();) {
				ArrayList<Transaction> transactionHistory = new ArrayList<>();

				while(rs.next()) {
					Transaction transaction = new Transaction();
					transaction.setTransactionID(rs.getInt("transactionID"));
					transaction.setAccountBalance(rs.getDouble("CurrentBalance"));
					transaction.setBranchID(rs.getInt("branchid"));
					transaction.setCustomerID(rs.getInt("customerid"));
					transaction.setDescription(rs.getString("Description"));
					transaction.setTransactionAccountNumber1(rs.getInt("TransactionAccountNumber1"));
					transaction.setTransactionAccountNumber2(rs.getInt("TransactionAccountNumber2"));
					transaction.setTransactionAmount(rs.getDouble("Amount"));
					transaction.setTransactionDateTime(rs.getString("TransactionDateTIme"));
					transaction.setTransactionSubType(rs.getInt("TransactionSubType"));
					transaction.setTransactionType(rs.getInt("TransactionType"));
					transaction.setTransactionName(rs.getString("transactionname"));
					
					transactionHistory.add(transaction);
				}
				
				return transactionHistory;
			}
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage() + "HELLO");
		}
		return null;
	}

}
