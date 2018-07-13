import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bank {
	private int bankID;
	private String bankName;
	private String bankAddress;
	private String adminPassword;
	
	
	public Bank() {
		Connection con = null;
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			Statement stmt = con.createStatement();
			String sql = "select * from BankMaster";
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.next();
			this.bankID = rs.getInt("BankID");
			this.bankName = rs.getString("BankName");
			this.adminPassword = rs.getString("Password");
			this.bankAddress = rs.getString("BankAddress");
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

	public boolean validateAdmin(String adminPassword) {
		return (this.adminPassword.equals(adminPassword));
	}

	@Override
	public String toString() {
		return "Bank [\nBank ID = " + bankID + ",\nBank Name = " + bankName + ",\nBank Address = " + bankAddress + "\n]";
	}
	
	
}
