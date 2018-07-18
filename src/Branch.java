
public class Branch {
	private int branchID;
	private String branchName = null;
	private String branchAddress = null;

	public Branch() {
		super();
	}

	public Branch(String branchName, String branchAddress) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}
	
	public int getBranchID() {
		return branchID;
	}

	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Override
	public String toString() {
		return "Branch [\nBranch Name = " + branchName + ",\nBranch Address = " + branchAddress + "\n]";
	}

}