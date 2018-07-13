import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Branch {
	private String branchName = null;
	private String branchAddress = null;
	
	public Branch(String branchName, String branchAddress) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}

	static HashMap <Integer, Branch> branchList = new HashMap <Integer, Branch> ();
	
	public void addBranchDetails() {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "insert into BranchMaster(BranchID, BankID, BranchName, BranchAddress) values(null, 1, ?,?)";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setString(1, this.branchName);
				pstmt.setString(2, this.branchAddress);
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
	
	
	static protected boolean validateBranchID(int branchID) {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "select BranchID from BranchMaster where BranchID=?";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, branchID);
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
	
	

	@Override
	public String toString() {
		return "Branch [\nBranch Name = " + branchName + ",\nBranch Address = " + branchAddress + "\n]";
	}

}