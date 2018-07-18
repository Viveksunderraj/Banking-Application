import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class BankApplication {
	
	BankDAO bankdao = new BankDAO();
	Bank bank = bankdao.getBankDetails();
	Scanner sc = new Scanner(System.in);
	
	
	public static final String URL = "jdbc:mysql://localhost:3306/BankDB";
	public static final String USER = "root";
	public static final String PASS = "";
	
	public static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public enum MainMenuOptions {
		ADMIN_LOGIN, CUSTOMER_LOGIN, EXIT;
	}
	
	public enum AdminMenuOptions {
		ADD_BRANCH, ADD_CUSTOMER, CREATE_CUSTOMER_ACCOUNT, LOGOUT;
	}
	
	public enum CustomerMenuOptions {
		PERFORM_TRANSACTION, CHECK_ACCOUNT_BALANCE, SHOW_TRANSACTION_HISTORY, ADD_BENEFICIARY, LOGOUT;
	}
	
	
	public static void printSeparator() {
		for(int i=0; i<80; i++) {
			System.out.print("=");
		}
		System.out.println("");
	}
	
	public void showAdminMenu() {
		
		String password;
		System.out.println("Enter Admin Password");
		sc.nextLine();
		password = sc.nextLine();
		
		if(bank.validateAdmin(password)) {
			 System.out.println("Successfully Logged In as Admin");
			 System.out.println(bank.toString());
			 
			 int adminOption;
			 AdminMenuOptions menuOption;
			 do {
				 printSeparator();
				 System.out.println("\nPlease Select an Option\n");
				 System.out.println("1) Add Branch 2) Add Customer 3) Create Customer Account 4) Logout\n");
				 printSeparator();
				 adminOption = sc.nextInt();
				 
				 menuOption = AdminMenuOptions.values()[adminOption-1];
				 
				 switch(menuOption) {
				 
				 case ADD_BRANCH:
					 MenuMethods.addBranch();
					 break;
				 
				 case ADD_CUSTOMER:
					 MenuMethods.addCustomer();
					 break;
				 
				 case CREATE_CUSTOMER_ACCOUNT:
					 MenuMethods.createCustomerAccount();
					 break;
				 
				 case LOGOUT:
					 System.out.println("Successfully Logged Out");
					 break;
				 
				 default:
					 System.out.println("Enter a valid option");
					 break;
				 }
			 }while(menuOption != AdminMenuOptions.LOGOUT);
		}
		else {
			System.out.println("Incorrect Password");
			return;
		}
	}
	
	public void showCustomerMenu() {
		int customerID;
		String customerPassword;
		System.out.println("Enter Customer ID");
		customerID = sc.nextInt();
		System.out.println("Enter Customer Password");
		sc.nextLine();
		customerPassword = sc.nextLine();
		
		CustomerDAO customerdao = new CustomerDAO();
		if(!customerdao.validateCustomerPassword(customerID, customerPassword)) {
			System.out.println("Invalid UserId/Password");
			return;
		}
		Customer loggedinCust = customerdao.getCustomerDetails(customerID);
		System.out.println("Successfully Logged In as Customer!!!! WELCOME");
		System.out.println(loggedinCust.toString());
		
		int customerOption;
		do {
			printSeparator();
			System.out.println("\nPlease Select an Option\n");
			System.out.println("1) Perform Transaction\n2) Check Account Balance\n3) Show Transaction History\n4) Add Beneficiary\n5) Logout");
			printSeparator();
			customerOption = sc.nextInt();
			
			CustomerMenuOptions customerMenuOption = CustomerMenuOptions.values()[customerOption-1];
			
			switch(customerMenuOption) {
			
			
			case PERFORM_TRANSACTION:
				MenuMethods.showTransactionMenu(customerID);
				break;
			case CHECK_ACCOUNT_BALANCE:
				MenuMethods.checkAccountBalance(customerID);
				break;
			case SHOW_TRANSACTION_HISTORY:
				MenuMethods.displayTransactionHistory(customerID);
				break;
			case ADD_BENEFICIARY:
				MenuMethods.addBeneficiary(customerID);
				break;
			case LOGOUT:
				break;
			default:
				System.out.println("Enter a Valid Option");
				break;
			}
		}while(Character.toUpperCase(customerOption) != 'H');
	}
	
	
	public void showMenu() {
		int option;
		MainMenuOptions menuOption;
		System.out.println("<<<<<<<<<< WELCOME TO ZOHO BANK >>>>>>>>>>");
		
		do {
			printSeparator();
			System.out.println("\nPlease Select an option\n");
			System.out.println("1) Admin Login 2) Customer Login 3) Exit\n");
			printSeparator();
			option = sc.nextInt();
			
			menuOption = MainMenuOptions.values()[option-1];
			switch(menuOption) {
			
			case ADMIN_LOGIN: 
				showAdminMenu();
				break;
			
			case CUSTOMER_LOGIN:
				showCustomerMenu();
				break;
			
			case EXIT:
				System.out.println("<<<<<<<<<<<< EXITING ZOHO BANK >>>>>>>>>>>>");
				break;
				
			default:
				System.out.println("Enter a valid option");
				break;
			}
		}while(menuOption != MainMenuOptions.EXIT);
	}
	
	public static void main(String args[])
	{
		BankApplication bankApplicationObj = new BankApplication();
		bankApplicationObj.showMenu();
	}

}
