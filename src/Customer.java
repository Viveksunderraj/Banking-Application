
class Customer {
	
	private int customerID;
	private String password;
	private String customerFirstName;
	private String customerLastName;
	private String address;
	private String phoneNumber;
	private String dateOfBirth;
	
	
	
	public Customer() {
		super();
	}

	public Customer(String customerFirstName, String customerLastName, String address,
			String phoneNumber, String dateOfBirth) {
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Customer [\nCustomer ID = " + customerID +",\nCustomer First Name = " + customerFirstName + ",\nCustomer Last Name = " + customerLastName
				+ ",\nAddress = " + address + ",\nPhone Number = " + phoneNumber + ",\nD.O.B = " + dateOfBirth + "\n]";
	}
	
	
}