import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BankDAO {
	
	
	public Bank getBankDetails() {
		Bank bank = new Bank();
		String sql = "select * from BankMaster";
		
		try(Connection con = BankApplication.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {		
			
			if(rs.next()) {
				bank.setBankID(rs.getInt("BankID"));
				bank.setBankName(rs.getString("BankName"));
				bank.setAdminPassword(rs.getString("Password"));
				bank.setBankAddress(rs.getString("BankAddress"));
				return bank;
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		return null;
	}
}
