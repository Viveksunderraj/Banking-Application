import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BeneficiaryDAO {
	
	public boolean addBeneficiary(Beneficiary newBeneficiary) {
		Connection con = BankApplication.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into beneficiary(CustomerID, BeneficiaryBankName, BeneficiaryAccountNumber, IFSCcode) values(?,?,?,?)";
		int affectedRows = 0;
		try {
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, newBeneficiary.getCustomerID());
			pstmt.setString(2, newBeneficiary.getBankName());
			pstmt.setInt(3, newBeneficiary.getBeneficiaryAccountNumber());
			pstmt.setString(4, newBeneficiary.getIFSCcode());
			affectedRows = pstmt.executeUpdate();
			
			if(affectedRows == 1) {
				return true;
			}
		
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		finally {
		    try { pstmt.close(); } catch (Exception e) { System.out.println(e); }
		    try { con.close(); } catch (Exception e) { System.out.println(e); }
		}
		return false;
	}
	
	public ArrayList<Beneficiary> getBeneficiary(int customerID) {
		Connection con = BankApplication.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * from beneficiary where customerid=?";
		
		try {
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			rs = pstmt.executeQuery();
			ArrayList<Beneficiary> beneficiaryList = new ArrayList<>();
			while(rs.next()) {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setBankName(rs.getString("BeneficiaryBankName"));
				beneficiary.setBeneficiaryAccountNumber(rs.getInt("BeneficiaryAccountNumber"));
				beneficiary.setCustomerID(rs.getInt("CustomerID"));
				beneficiary.setIFSCcode(rs.getString("IFSCcode"));
				beneficiaryList.add(beneficiary);
			}
			return beneficiaryList;
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		finally {
			try { rs.close(); } catch (Exception e) { System.out.println(e); }
		    try { pstmt.close(); } catch (Exception e) { System.out.println(e); }
		    try { con.close(); } catch (Exception e) { System.out.println(e); }
		}
		return null;
	}
}
