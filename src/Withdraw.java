
public class Withdraw extends Transaction {

	public Withdraw(double transactionAmount, double accountBalance, int customerID, int branchID,
			int transactionAccountNumber1, int transactionAccountNumber2, int transactionType, int transactionSubType,
			String transactionDateTime, String description) {
		super(transactionAmount, accountBalance, customerID, branchID, transactionAccountNumber1, transactionAccountNumber2,
				transactionType, transactionSubType, transactionDateTime, description);
	}

	/*Method to withdraw money from the account*/
	public boolean withDraw() {
		
		double balance, newBalance;
		int accountType;
		
		balance = AccountDAO.getAccountBalance(getTransactionAccountNumber1());		
		accountType = AccountDAO.getAccountType(getTransactionAccountNumber1());
		newBalance = balance - getTransactionAmount();
		
		if(accountType == Account.AccountType.SAVINGS.getValue()) {
			if(newBalance < 1000.0) {
				System.out.println("Minimum Balance of Rs. 1000 must be maintained.Savings Account Balance: "+ balance);
				return false;
			    }
			}
			else if(accountType == Account.AccountType.CURRENT.getValue()){
				if(newBalance < 0) {
					System.out.println("Sufficient fund not available in Current account.Current Account Balance: "+ balance);
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
		
		System.out.println("An amount of Rs. "+ getTransactionAmount() + " has been withdrawn from your " + getTransactionAccountNumber1() + " account" );
		System.out.println("Your " + getTransactionAccountNumber1() + " account Balance is : Rs."+ getAccountBalance());
		return true;
			}
			
		}
		
