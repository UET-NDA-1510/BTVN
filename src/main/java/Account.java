import java.util.ArrayList;
import java.util.List;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các thuộc tính và phương thức cốt lõi để quản lý số dư và giao dịch.
 */
public abstract class Account {

    public static final String CHECKING_TYPE = "CHECKING";
    public static final String SAVINGS_TYPE = "SAVINGS";

    private long accountNumber;
    private double balance;
    protected List<Transaction> transactions;

    /**
     * Khởi tạo một tài khoản ngân hàng mới.
     *
     * @param accountNumber Số tài khoản.
     * @param balance       Số dư ban đầu.
     */
    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionList() {
        return transactions;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        if (transactionList == null) {
            this.transactions = new ArrayList<>();
        } else {
            this.transactions = transactionList;
        }
    }

    /**
     * Gửi tiền vào tài khoản.
     *
     * @param amount Số tiền cần gửi.
     */
    public abstract void deposit(double amount);

    /**
     * Rút tiền khỏi tài khoản.
     *
     * @param amount Số tiền cần rút.
     */
    public abstract void withdraw(double amount);

    /**
     * Thực hiện logic cộng tiền vào số dư.
     *
     * @param amount Số tiền gửi.
     * @throws InvalidFundingAmountException nếu số tiền nhỏ hơn hoặc bằng 0.
     */
    protected void doDepositing(double amount) throws InvalidFundingAmountException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        balance += amount;
    }

    /**
     * Thực hiện logic trừ tiền khỏi số dư.
     *
     * @param amount Số tiền rút.
     * @throws InvalidFundingAmountException nếu số tiền rút nhỏ hơn hoặc bằng 0.
     * @throws InsufficientFundsException    nếu số dư không đủ để rút.
     */
    protected void doWithdrawing(double amount) throws InvalidFundingAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount);
        }
        balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactions.add(transaction);
        }
    }

    /**
     * Trích xuất lịch sử các giao dịch của tài khoản.
     *
     * @return Chuỗi văn bản chứa thông tin tóm tắt của tất cả giao dịch.
     */
    public String getTransactionHistory() {
        StringBuilder historyBuilder = new StringBuilder();
        historyBuilder.append("Lịch sử giao dịch của tài khoản ")
                .append(accountNumber)
                .append(":\n");

        for (int i = 0; i < transactions.size(); i++) {
            historyBuilder.append(transactions.get(i).getTransactionSummary());
            if (i < transactions.size() - 1) {
                historyBuilder.append("\n");
            }
        }

        return historyBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        return this.accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return (int) (accountNumber ^ (accountNumber >>> 32));
    }
}