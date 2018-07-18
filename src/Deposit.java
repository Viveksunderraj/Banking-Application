
public class Deposit extends Transaction {
	
	public Deposit(double transactionAmount, double accountBalance, int customerID, int branchID,
			int transactionAccountNumber1, int transactionAccountNumber2, int transactionType, int transactionSubType,
			String transactionDateTime, String description) {
		super(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2,
				transactionType, transactionSubType, transactionDateTime, description);
	}
	
	/*Method to deposit money into the account*/
	public boolean deposit() {
				
		double balance, newBalance;
		int accountType;
		balance = AccountDAO.getAccountBalance(getTransactionAccountNumber1());
		accountType = AccountDAO.getAccountType(getTransactionAccountNumber1());
		newBalance = balance + getTransactionAmount();
		
		if(accountType == Account.AccountType.LOAN.getValue()) {
			if(newBalance > 0) {
				System.out.println("Loan Balance is "+ balance + "\nRepay amount is greater than loan balance, Enter a Repay amount less than (or) equal to the loan Balance");
				return false;
			}
		}
		
		setAccountBalance(newBalance);
		
		if(!TransactionDAO.addTransactionDetails(this)) {
			return false;
		}
		if(!AccountDAO.updateAccountBalance(getAccountBalance(), getTransactionAccountNumber1())) {
			return false;
		}
		
		
		if(getTransactionType() < TransactionType.TRANSFER_WITHIN_ACOOUNTS.getValue() || getTransactionType() == TransactionType.REPAY_LOAN_THROUGH_DEPOSIT.getValue()) {
		System.out.println("An amount of Rs. "+ getTransactionAmount() + " has been deposited to " + getTransactionAccountNumber1() + " account" +"\nBalance is: " + getAccountBalance() );
		   }
		return true;	
	}
	
}
