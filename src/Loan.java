
public class Loan extends Transaction{

	public Loan(double transactionAmount, double accountBalance, int customerID, int branchID,
			int transactionAccountNumber1, int transactionAccountNumber2, int transactionType, int transactionSubType,
			String transactionDateTime, String description) {
		super(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2,
				transactionType, transactionSubType, transactionDateTime, description);
	}
	
	
	public boolean availLoan() {
		double balance, newBalance;
		balance = AccountDAO.getAccountBalance(getTransactionAccountNumber1());
		
		if(balance < 0) {
			System.out.println("An Existing loan of Rs." + (balance) + " is Pending, Please Repay the existing loan to avail a new loan");
			return false;
		}
		else {
		newBalance = getTransactionAmount();
		setAccountBalance(newBalance);
		
		if(!TransactionDAO.addTransactionDetails(this)) {
			return false;
		}
		if(!AccountDAO.updateAccountBalance(getAccountBalance(), getTransactionAccountNumber1())) {
			return false;
		}
		
		System.out.println("A loan of Rs." + newBalance + " has been Availed");
		return true;
		}
	}

}
