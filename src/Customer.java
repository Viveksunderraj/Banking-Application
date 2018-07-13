import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Customer {
	
	private String password;
	private String customerFirstName;
	private String customerLastName;
	private String address;
	private String phoneNumber;
	private String dateOfBirth;
	
	public Customer(String password, String customerFirstName, String customerLastName, String address,
			String phoneNumber, String dateOfBirth) {
		this.password = password;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	public void addCustomerDetails() {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "insert into CustomerMaster(CustomerID, CustomerFName, CustomerLName, Password, CustomerAddress, PhoneNumber, DateOfBirth) values(null,?,?,?,?,?,?)";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setString(1, this.customerFirstName);
				pstmt.setString(2, this.customerLastName);
				pstmt.setString(3, this.password);
				pstmt.setString(4, this.address);
				pstmt.setString(5, this.phoneNumber);
				pstmt.setString(6, this.dateOfBirth);
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
	
	public static boolean validateCustomerID(int customerID) {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "select CustomerID from CustomerMaster where CustomerID=?";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, customerID);
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
	
	
	public static boolean validateCustomerPassword(int customerID, String customerPassword) {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "select * from CustomerMaster where CustomerID=? and password=?";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, customerID);
				pstmt.setString(2, customerPassword);
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
	
	
	public static void displayCustomerDetails(int customerID) {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","");
			String sql = "select * from CustomerMaster where customerID=?";
			
			try(PreparedStatement pstmt =  con.prepareStatement(sql)) {
				pstmt.setInt(1, customerID);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				
				System.out.println( "Customer ID = " + rs.getInt("customerID") + "\nCustomer First Name = " + rs.getString("customerfname") + ",\nCustomer Last Name = " + rs.getString("customerlname") + 
					",\nAddress = " + rs.getString("customeraddress") + ",\nPhone Number = " + rs.getString("phonenumber") + ",\nD.O.B = " + rs.getString("dateofbirth"));

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

	@Override
	public String toString() {
		return "Customer [\nCustomer First Name = " + customerFirstName + ",\nCustomer Last Name = " + customerLastName
				+ ",\nAddress = " + address + ",\nPhone Number = " + phoneNumber + ",\nD.O.B = " + dateOfBirth + "\n]";
	}
	
	
}