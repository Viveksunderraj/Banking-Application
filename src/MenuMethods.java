import java.util.ArrayList;
import java.util.Scanner;


public class MenuMethods {
	
	static Scanner sc = new Scanner(System.in);
	
	public enum TransactionMenuOptions {
		DEPOSIT, WITHDRAW, TRANSFER_WITHIN_ACOOUNTS, TRANSFER_TO_OTHER_CUSTOMER_WITHIN_BANK, TRANSFER_TO_OTHER_BANK_ACCOUNT, AVAIL_LOAN, REPAY_LOAN_THROUGH_DEPOSIT,
		REPAY_LOAN_THROUGH_TRANSFER, EXIT;
	}
	
	public enum GetAccountType {
		SAVINGS_AND_CURRENT, LOAN_ACCOUNT, ALL_ACCOUNTS;
	}
	
	public static void addBranch() {
		 String branchName, branchAddress;						 
		 System.out.println("Enter the Branch Name: ");
		 branchName = sc.nextLine();
		 System.out.println("Enter the Branch Address: ");
		 branchAddress = sc.nextLine();
		 
		 Branch newBranch = new Branch(branchName, branchAddress);
		 
		 BranchDAO branchdao = new BranchDAO();
		 if(!branchdao.addBranch(newBranch)) {
			 System.out.println("Adding Branch Falied");
			 return;
		 }
		 System.out.println("Successfully Added Branch");
		 System.out.println(newBranch.toString());
	}
	
	public static  void addCustomerPassword() {
		System.out.println("Enter Customer ID: ");
		int customerID = sc.nextInt();
		CustomerDAO customerdao = new CustomerDAO();
	    Customer cust = customerdao.getCustomerDetails(customerID);
	    if(cust == null) {
		    System.out.println("Invalid Customer ID");
		    return;
	    }
		
	    System.out.println("Enter Customer login Password: ");
		String customerPassword = sc.nextLine();
	    
		customerdao.addCustomerPassword(customerID, customerPassword);
		System.out.println("Successfully Added Password");
	}
	
	public static void addCustomer() {
		 String customerFirstName, customerLastName, address, dateOfBirth, phoneNumber;
		 System.out.println("Enter the Customer First Name: ");
		 customerFirstName = sc.nextLine();
		 System.out.println("Enter the Customer Last Name: ");
		 customerLastName = sc.nextLine();
		 System.out.println("Enter Customer Address: ");
		 address = sc.nextLine();
		 System.out.println("Enter Customer Phone Number: ");
		 phoneNumber = sc.nextLine();
		 System.out.println("Enter the Customer D.O.B in YYYY/MM/DD format: ");
		 dateOfBirth = sc.nextLine();
		 
		 Customer newCustomer = new Customer(customerFirstName, customerLastName, address, phoneNumber, dateOfBirth);
		 
		 CustomerDAO customerdao = new CustomerDAO();
		 if(!customerdao.addCustomer(newCustomer)) {
			 System.out.println("Adding Customer Falied");
			 return;
		 }
		 System.out.println("Successfully Added Customer");
		 
		 addCustomerPassword();
	}
	
	public static void createCustomerAccount() {
		 System.out.println("Enter the Customer ID in which account has to be created: ");
		 int customerID = sc.nextInt();
		 CustomerDAO customerdao = new CustomerDAO();
		 Customer cust = customerdao.getCustomerDetails(customerID);
		 if(cust == null) {
			 System.out.println("Invalid Customer ID");
			 return;
		 }
		 System.out.println(cust.toString());
		 int continueOption;
		 
		 do {
			 int branchID, accountType;
			 double accountBalance = 0 ;
			 System.out.println("\nEnter the Branch ID in which account has to be created: ");
			 branchID = sc.nextInt();
			 BranchDAO branchdao = new BranchDAO();
			 Branch branch = branchdao.getBranchDetails(branchID);
			 if(branch == null) {
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
			 AccountDAO accountdao = new AccountDAO();
			 
			 if(!accountdao.addcustomerAccount(newAccount)) {
				 System.out.println("Account Creation failure!!");
				 return;
			 }
			 System.out.println("Successfully created " + account + " account");
			 
			 System.out.println("Do you want to continue:\n1) YES 2)NO");
			 continueOption = sc.next().charAt(0);
		 }while(continueOption != '2');
	}
	
		public static void displayAccounts(ArrayList<Account> accountList) {
			
			for(Account account : accountList) {
				System.out.println("Account Number = " + account.getAccountNumber() + ", Account Type = " + account.getAccountName() + ", Account Balance = " + account.getAccountBalance());
			}
		}
		
		public static void displayBeneficiaryAccounts(ArrayList<Beneficiary> beneficiaryList) {
			for(Beneficiary beneficiary : beneficiaryList) {
				System.out.println("Bank Name = " + beneficiary.getBankName() + ", Account Number = " + beneficiary.getBeneficiaryAccountNumber() + ", IFSC Code = " + beneficiary.getIFSCcode());
			}
		}
		
		public static void displayTransactionReportHeader(TransactionReportHeader header) {
			BankApplication.printSeparator();
			System.out.println("\n\n");
			System.out.println("BANK NAME : " + header.getBankName() + ", BRANCH NAME : " + header.getBranchName() +
			", ACCOUNT NAME : " + header.getAccountName() + ", ACCOUNT NUMBER : " + header.getAccountNumber() + ", CLOSING BALANCE = " + header.getAccountBalance());
		}
		
		public static void displayTransactions(ArrayList<Transaction> transactionHistory) {
			
			for(Transaction transaction : transactionHistory) {
				BankApplication.printSeparator();
				System.out.println("TRANSACTION DATE AND TIME    : " + transaction.getTransactionDateTime());
				System.out.println("TRANSACTION REFERENCE NUMBER : " + transaction.getTransactionID());
				System.out.println("TRANSACTION TYPE             : " + transaction.getTransactionName());
				System.out.println("NARRATION                    : " + transaction.getDescription());
				System.out.println("TRANSACTION AMOUNT           : " + transaction.getTransactionAmount());
				System.out.println("CLOSING BALANCE              : " + transaction.getAccountBalance());
			}
			
		}
		
		public static void showTransactionMenu(int tempCustomerID) {
		
		int transactionType;
		double transactionAmount, accountBalance = 0;
		int customerID = tempCustomerID, branchID, transactionAccountNumber1 = 0, transactionAccountNumber2 = 0, transactionSubType = 0;
		String description, description1, description2;
		//QUERY BRANCH AND PUT HERE
		System.out.println("Enter the Branch Id");
		branchID = sc.nextInt();
		
		AccountDAO accountdao = new AccountDAO();
		ArrayList<Account> accountList;
		BankApplication.printSeparator();
		System.out.println("\nPlease Select an Option\n");
		System.out.println("1) Deposit\n2) Withdraw\n3) Transfer Within Own Accounts\n4) Transfer to Other Customer Within Bank\n5) Transfer To other Bank Account\n6) Avail Loan\n7) Repay Loan Through Deposit\n8) Repay Loan Through Transfer\n9) Exit\n");
		BankApplication.printSeparator();
		
		transactionType = sc.nextInt();
		
		TransactionMenuOptions transactionOption = TransactionMenuOptions.values()[transactionType-1];
		
		switch(transactionOption) {
		
		case DEPOSIT:
			
			System.out.println("Select an Account Number:");
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			transactionAccountNumber1 = sc.nextInt();		
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
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			transactionAccountNumber1 = sc.nextInt();					
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
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Select an Account Number To Which you want To Transfer:");
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			transactionAccountNumber2 = sc.nextInt();
			if(transactionAccountNumber1 == transactionAccountNumber2) {
				System.out.println("From Account and To account Cannot be the same");
				return;
			}
			
			System.out.println("Enter the amount that you want to transfer");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			description1 = description +" TR.To A\\c No: " + transactionAccountNumber2;
			transactionSubType = 2;
			Withdraw transferWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description1);
			if(!transferWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			transactionSubType = 1;
			description2 = description +" TR.From A\\c No: " + transactionAccountNumber1;
			Deposit transferDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber2, transactionAccountNumber1, transactionType, transactionSubType, null, description2);
			if(!transferDeposit.deposit()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			break;
			
		case TRANSFER_TO_OTHER_CUSTOMER_WITHIN_BANK:
			System.out.println("Select an Account Number From Which you want To Transfer:");
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Enter the Account Number To Which you want To Transfer:");
			transactionAccountNumber2 = sc.nextInt();
			if(!accountdao.validateAccountNumber(transactionAccountNumber2)) {
				System.out.println("Invalid Account Number !!!!");
				break;
			}
			
			System.out.println("Enter the amount that you want to transfer");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			description1 = description +" TR.To A\\c No: " + transactionAccountNumber2;
			transactionSubType = 2;
			Withdraw transferBetweenWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description1);
			if(!transferBetweenWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			
			transactionSubType = 1;
			description2 = description +" TR.From A\\c No: " + transactionAccountNumber2;;
			Deposit transferBetweenDeposit = new Deposit(transactionAmount, accountBalance, customerID, branchID,transactionAccountNumber2, transactionAccountNumber1, transactionType, transactionSubType, null, description);
			if(!transferBetweenDeposit.deposit()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			System.out.println("Successfully transfered");
			break;
		
			
		case TRANSFER_TO_OTHER_BANK_ACCOUNT:
			
			System.out.println("Select a Account Number From Which you want To Transfer:");
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			transactionAccountNumber1 = sc.nextInt();
			
			BeneficiaryDAO beneficiarydao = new BeneficiaryDAO();
			System.out.println("Select the Beneficiary Account Number To Which you want To Transfer:");
			BankApplication.printSeparator();
			ArrayList<Beneficiary> beneficiaryList = beneficiarydao.getBeneficiary(customerID);
			if(beneficiaryList == null) {
				System.out.println("No Benefitiary Accounts Found, Please add Benefitiary Account");
			}
			displayBeneficiaryAccounts(beneficiaryList);
			transactionAccountNumber2 = sc.nextInt();
			
			System.out.println("Enter the amount that you want to Transfer");
			transactionAmount = sc.nextDouble();
			
			System.out.println("Enter description");
			sc.nextLine();
			description = sc.nextLine();
			
			description = description + " NEFT TR.To Beneficiary A\\c No: " + transactionAccountNumber2;
			transactionSubType = 2;
			Withdraw transferToOtherBankWithdrawal = new Withdraw(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2, transactionType, transactionSubType, null, description);
			if(!transferToOtherBankWithdrawal.withDraw()) {
				System.out.println("Transfer Failed !!!!!!");
				break;
			}
			System.out.println("Successfully transfered");
			break;
		
		case AVAIL_LOAN:
			System.out.println("Select a Loan Account Number:");
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.LOAN_ACCOUNT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Enter the amount that you want to Avail as Loan");
			transactionAmount = sc.nextDouble() * (-1);
			
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
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.LOAN_ACCOUNT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			
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
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.SAVINGS_AND_CURRENT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			transactionAccountNumber1 = sc.nextInt();
			
			System.out.println("Select a Loan Account Number to which you want to Repay:");
			BankApplication.printSeparator();
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.LOAN_ACCOUNT);
			displayAccounts(accountList);
			BankApplication.printSeparator();
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
		
		
		public static void checkAccountBalance(int customerID) {
			
			System.out.println("Enter the Branch:");
			int branchID = sc.nextInt();
			
			System.out.println("Select an Account Number for which you want to check balance:");
			
			BankApplication.printSeparator();
			AccountDAO accountdao = new AccountDAO();
			ArrayList<Account> accountList;
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.ALL_ACCOUNTS);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			int accountNumber = sc.nextInt();
			
			double balance = AccountDAO.getAccountBalance(accountNumber);
			
			System.out.println("Your " + accountNumber + " account balance is " + balance);
			
		}
		
		public static void displayTransactionHistory(int customerID) {
			System.out.println("Enter the Branch:");
			int branchID = sc.nextInt();
			
			System.out.println("Select an Account Number for which you want to check transaction history:");
			
			BankApplication.printSeparator();
			AccountDAO accountdao = new AccountDAO();
			ArrayList<Account> accountList;
			accountList = accountdao.getAccountsForCustomer(customerID, branchID, GetAccountType.ALL_ACCOUNTS);
			displayAccounts(accountList);
			BankApplication.printSeparator();
			
			int accountNumber = sc.nextInt();
			TransactionDAO transactiondao = new TransactionDAO();
			TransactionReportHeader transactionReportHeader = transactiondao.getTransactionHeader(accountNumber);
			ArrayList<Transaction> transactionHistory = transactiondao.getTransactionHistory(customerID, accountNumber);
			displayTransactionReportHeader(transactionReportHeader);
			displayTransactions(transactionHistory);
			
			
			
				
		}
		
		public static void addBeneficiary(int customerID) {
			String bankName, IFSCcode;
			int beneficiaryAccountNumber;
			
			System.out.println("Enter the Beneficiary Bank Name:");
			bankName = sc.nextLine();
			System.out.println("Enter the Beneficiary Account Number:");
			beneficiaryAccountNumber = sc.nextInt();
			System.out.println("Enter the IFSC Code:");
			sc.nextLine();
			IFSCcode = sc.nextLine();
			
			Beneficiary newBeneficiary = new Beneficiary(customerID, bankName, beneficiaryAccountNumber, IFSCcode);
			BeneficiaryDAO beneficiarydao = new BeneficiaryDAO();
			if(!beneficiarydao.addBeneficiary(newBeneficiary)) {
				System.out.println("Could not add Beneficiary");
				return;
			}
			System.out.println("Successfully added Beneficiary");
		}
	
}
