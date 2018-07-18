import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
	
	public boolean addCustomer(Customer newCustomer) {
		
		String sql = "insert into CustomerMaster(CustomerID, CustomerFName, CustomerLName, CustomerAddress, PhoneNumber, DateOfBirth) values(null,?,?,?,?,?)";
		int affectedRows = 0;
		
		try(Connection con = BankApplication.getConnection();
			PreparedStatement pstmt =  con.prepareStatement(sql);) {
			
			pstmt.setString(1, newCustomer.getCustomerFirstName());
			pstmt.setString(2, newCustomer.getCustomerLastName());
			pstmt.setString(3, newCustomer.getAddress());
			pstmt.setString(4, newCustomer.getPhoneNumber());
			pstmt.setString(5, newCustomer.getDateOfBirth());
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
	
	
	public boolean addCustomerPassword(int customerID, String password) {
		
		
		String sql = "insert into customerpassword(CustomerID, password) values(?,?)";
		int affectedRows = 0;
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {  
			pstmt.setInt(1, customerID);
			pstmt.setString(2, password);
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
	
	public Customer getCustomerDetails(int customerID) {
		String sql = "select * from CustomerMaster where customerID=?";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			
			try(ResultSet rs = pstmt.executeQuery();) {
				Customer cust = new Customer();
				
				if(rs.next()) {
					cust.setCustomerID(rs.getInt("customerID"));
					cust.setCustomerFirstName(rs.getString("customerfname"));
					cust.setCustomerLastName(rs.getString("customerlname"));
					cust.setAddress(rs.getString("customeraddress"));
					cust.setPhoneNumber(rs.getString("phonenumber"));
					cust.setDateOfBirth(rs.getString("dateofbirth"));
					return cust;
				}
			}

		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	public boolean validateCustomerID(int customerID) {
		
		String sql = "select CustomerID from CustomerMaster where CustomerID=?";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) { 
			pstmt.setInt(1, customerID);
			
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
	
	public boolean validateCustomerPassword(int customerID, String customerPassword) {
		
		String sql = "select * from CustomerPassword where CustomerID=? and password=?";
		
		try(Connection con = BankApplication.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setString(2, customerPassword);
			
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

}
