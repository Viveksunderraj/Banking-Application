import java.util.*;

public class BankApplication {
	
	Bank bankObj = new Bank();
	Scanner sc = new Scanner(System.in);
	
	
	public enum MainMenuOptions {
		ADMIN_LOGIN, CUSTOMER_LOGIN, EXIT;
	}
	
	public enum AdminMenuOptions {
		ADD_BRANCH, ADD_CUSTOMER, CREATE_CUSTOMER_ACCOUNT, LOGOUT;
	}
	
	public enum CustomerMenuOptions {
		PERFORM_TRANSACTION, CHECK_ACCOUNT_BALANCE, SHOW_TRANSACTION_HISTORY, LOGOUT;
	}
	
	public enum TransactionMenuOptions {
		DEPOSIT, WITHDRAW, TRANSFER_WITHIN_ACOOUNTS, TRANSFER_BETWEEN_ACCOUNTS, AVAIL_LOAN, REPAY_LOAN_THROUGH_DEPOSIT,
		REPAY_LOAN_THROUGH_TRANSFER, EXIT;
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
		
		if(bankObj.validateAdmin(password)) {
			 System.out.println("Successfully Logged In as Admin");
			 System.out.println(bankObj.toString());
			 
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
					 String branchName, branchAddress;						 
					 System.out.println("Enter the Branch Name: ");
					 sc.nextLine();
					 branchName = sc.nextLine();
					 System.out.println("Enter the Branch Address: ");
					 branchAddress = sc.nextLine();
					 
					 Branch newBranch = new Branch(branchName, branchAddress);
					 
					 newBranch.addBranchDetails();
					 System.out.println("Successfully Added Branch");
					 System.out.println(newBranch.toString());
					 break;
				 
				 case ADD_CUSTOMER:
					 String customerFirstName, customerLastName, address, dateOfBirth, phoneNumber, customerPassword;
					 System.out.println("Enter the Customer First Name: ");
					 sc.nextLine();
					 customerFirstName = sc.nextLine();
					 System.out.println("Enter the Customer Last Name: ");
					 customerLastName = sc.nextLine();
					 System.out.println("Enter Customer Address: ");
					 address = sc.nextLine();
					 System.out.println("Enter Customer Phone Number: ");
					 phoneNumber = sc.nextLine();
					 System.out.println("Enter the Customer D.O.B in YYYY/MM/DD format: ");
					 dateOfBirth = sc.nextLine();
					 System.out.println("Enter Customer login Password: ");
					 customerPassword = sc.nextLine();
					 
					 Customer newCustomer = new Customer(customerPassword, customerFirstName, customerLastName, address, phoneNumber, dateOfBirth);
					 
					 newCustomer.addCustomerDetails();
					 System.out.println("Successfully Added Customer");
					 System.out.println(newCustomer.toString());
					 break;
				 
				 case CREATE_CUSTOMER_ACCOUNT:
					 System.out.println("Enter the Customer ID in which account has to be created: ");
					 int customerID = sc.nextInt();
					 if(!Customer.validateCustomerID(customerID)) {
							System.out.println("Invalid Customer ID");
							break;
						}
					 Customer.displayCustomerDetails(customerID);
					 int continueOption;
					 
					 do {
						 int branchID, accountType;
						 double accountBalance = 0 ;
						 System.out.println("\nEnter the Branch ID in which account has to be created: ");
						 branchID = sc.nextInt();
						 if(!Branch.validateBranchID(branchID)) {
							 System.out.println("Invalid Branch ID");
							 break;
						   }
		
						 System.out.println("Please Select the Account Type which you want to create: ");
						 System.out.println("1) Savings Account 2) Current Account 3) Loan Account");
						 accountType = sc.nextInt();
						 
						 Account.AccountType account = Account.AccountType.values()[accountType-1];
						 
						 switch(account) {
							 case SAVINGS:
								 accountBalance = 1000;
								 break;
							 case CURRENT:
								 accountBalance = 0;
								 break;
							 case LOAN:
								 accountBalance = 0;
								 break;
							 default:
								 System.out.println("Enter a valid Option");
								 break;
						 }
						 
						 Account newAccount = new Account(branchID, customerID, accountType, accountBalance);
						 newAccount.addCustomerAccount();
						 System.out.println("Successfully created " + account + " account");
						 
						 System.out.println("Do you want to continue:\n1) YES 2)NO");
						 continueOption = sc.next().charAt(0);
					 }while(continueOption != '2');
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
	
	
	public void showTransactionMenu(int tempCustomerID) {
		
		int transactionType;
		double transactionAmount, accountBalance = 0;
		int customerID = tempCustomerID, branchID, transactionAccountNumber1 = 0, transactionAccountNumber2 = 0, transactionSubType = 0;
		String description;
		//QUERY BRANCH AND PUT HERE
		System.out.println("Enter the Branch Id");
		branchID = sc.nextInt();
		
		printSeparator();
		System.out.println("\nPlease Select an Option\n");
		System.out.println("1) Deposit\n2) Withdraw\n3) Transfer Within Own Accounts\n4) Transfer between Accounts\n5) Avail Loan\n6) Repay Loan Through Deposit\n7) Repay Loan Through Transfer\n8) Exit\n");
		printSeparator();
		
		transactionType = sc.nextInt();
		
		TransactionMenuOptions transactionOption = TransactionMenuOptions.values()[transactionType-1];
		
		switch(transactionOption) {
		
		case DEPOSIT:
			
			System.out.println("Select an Account Number:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();		
			
			//validate entered account here
			
			System.out.println("Enter the amount that you want to deposit");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			Deposit newDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!newDeposit.deposit()) {
				System.out.println("Deposit Failed !!!!!!");
				break;
			}
			System.out.println("Deposit Success !!!!!!");
			break;
		
		case WITHDRAW:
			
			System.out.println("Select an Account Number:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();		
			
			//validate entered account here
			
			System.out.println("Enter the amount that you want to withdraw");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			Withdraw newWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!newWithdrawal.withDraw()) {
				System.out.println("WithDraw Failed !!!!!!");
				break;
			}
			System.out.println("WithDraw Success !!!!!!");
			break;
			
		case TRANSFER_WITHIN_ACOOUNTS:
			System.out.println("Select an Account Number From Which you want To Transfer:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			//validate entered account here
			System.out.println("Select an Account Number To Which you want To Transfer:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID, transactionAccountNumber1);
			printSeparator();
			transactionAccountNumber2 = sc.nextInt();
			//validate entered account here
			System.out.println("Enter the amount that you want to withdraw");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			transactionSubType = 2;
			Withdraw transferWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!transferWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			transactionSubType = 1;
			Deposit transferDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber2, transactionAccountNumber1, transactionType, transactionSubType, null, description);
			if(!transferDeposit.deposit()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			break;
			
		case TRANSFER_BETWEEN_ACCOUNTS:
			System.out.println("Select an Account Number From Which you want To Transfer:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Enter the Account Number To Which you want To Transfer:");
			transactionAccountNumber2 = sc.nextInt();
			if(!Account.validateAccountNumber(customerID, transactionAccountNumber2)) {
				System.out.println("Invalid Account Number !!!!");
				break;
			}
			
			System.out.println("Enter the amount that you want to withdraw");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			transactionSubType = 2;
			Withdraw transferBetweenWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!transferBetweenWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			transactionSubType = 1;
			Deposit transferBetweenDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber2, transactionAccountNumber1, transactionType, transactionSubType, null, description);
			if(!transferBetweenDeposit.deposit()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			break;
			
		case AVAIL_LOAN:
			System.out.println("Select a Loan Account Number:");
			printSeparator();
			Account.displayCustomerLoanAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Enter the amount that you want to Avail as Loan");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			Loan newLoan = new Loan(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			
			if(!newLoan.availLoan()) {
				System.out.println("Avail Loan Failed !!!!!!");
				break;
			}
			break;
		
		case REPAY_LOAN_THROUGH_DEPOSIT:
			System.out.println("Select a Loan Account Number to which you want to Repay:");
			printSeparator();
			Account.displayCustomerLoanAccounts(customerID, branchID);
			printSeparator();
			
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Enter the amount that you want to Repay");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			Deposit repayLoanDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!repayLoanDeposit.deposit()) {
				System.out.println("Repay Failed !!!!!!");
				break;
			}
			System.out.println("Repay Success !!!!!!");
			
			break;
			
		case REPAY_LOAN_THROUGH_TRANSFER:
			System.out.println("Select a Savings Account or Current Account Number from which you want to transfer:");
			printSeparator();
			Account.displayCustomerAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Select a Loan Account Number to which you want to Repay:");
			printSeparator();
			Account.displayCustomerLoanAccounts(customerID, branchID);
			printSeparator();
			transactionAccountNumber2 = sc.nextInt();
			
			System.out.println("Enter the amount that you want to Repay");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			transactionSubType = 2;
			Withdraw repayTransferLoanWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!repayTransferLoanWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			transactionSubType = 1;
			Deposit repayTransferLoanDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber2, transactionAccountNumber1, transactionType, transactionSubType, null, description);
			if(!repayTransferLoanDeposit.deposit()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			break;
			
		case EXIT:
			break;
			
		default:
			System.out.println("Enter a Valid Option");
			break;
		}
	}
	
	
	public void checkAccountBalance(int customerID) {
		System.out.println("Select an Account Number for which you want to check balance:");
		
		printSeparator();
		Account.displayAllAccounts(customerID);
		printSeparator();
		int accountNumber = sc.nextInt();
		
		double balance = Account.getAccountBalance(accountNumber);
		
		System.out.println("Your " + accountNumber + " account balance is " + balance);
		
	}
	
	
	public void displayTransactionHistory(int customerID) {
		System.out.println("Select an Account Number for which you want to check balance:");
		
		printSeparator();
		Account.displayAllAccounts(customerID);
		printSeparator();
		
		int accountNumber = sc.nextInt();
		
		Transaction.displayTransactionHistory(customerID, accountNumber);
		
		
	}
	
	public void showCustomerMenu() {
		int customerID;
		String customerPassword;
		System.out.println("Enter Customer ID");
		customerID = sc.nextInt();
		System.out.println("Enter Customer Password");
		sc.nextLine();
		customerPassword = sc.nextLine();
		
		if(!Customer.validateCustomerPassword(customerID, customerPassword)) {
			System.out.println("Invalid UserId/Password");
			return;
		}
		System.out.println("Successfully Logged In as Customer!!!! WELCOME");
		Customer.displayCustomerDetails(customerID);
		
		int customerOption;
		do {
			printSeparator();
			System.out.println("\nPlease Select an Option\n");
			System.out.println("1) Perform Transaction\n2) Check Account Balance\n3) Show Transaction History\n4) Logout");
			printSeparator();
			customerOption = sc.nextInt();
			
			CustomerMenuOptions customerMenuOption = CustomerMenuOptions.values()[customerOption-1];
			
			switch(customerMenuOption) {
			
			
			case PERFORM_TRANSACTION:
				showTransactionMenu(customerID);
				break;
			case CHECK_ACCOUNT_BALANCE:
				checkAccountBalance(customerID);
				break;
			case SHOW_TRANSACTION_HISTORY:
				displayTransactionHistory(customerID);
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
