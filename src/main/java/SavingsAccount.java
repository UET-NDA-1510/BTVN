import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Đại diện cho tài khoản tiết kiệm (Savings Account).
 * Lớp này thực thi các quy định nghiêm ngặt về hạn mức rút tiền và duy trì số dư tối thiểu.
 */
public class SavingsAccount extends Account {
    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);
    private static final double MAX_WITHDRAW_AMOUNT = 1000.0;
    private static final double MIN_BALANCE_REQUIREMENT = 5000.0;

    /**
     * Khởi tạo một tài khoản tiết kiệm mới.
     *
     * @param accountNumber Số tài khoản.
     * @param balance       Số dư ban đầu.
     */
    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }
    @Override
    public void deposit(double amount) {
        LOGGER.info("Bắt đầu giao dịch nạp tiền - Tài khoản: {}, Số tiền: {}", getAccountNumber(), amount);
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS,
                    amount,
                    initialBalance,
                    finalBalance
            );
            addTransaction(transaction);
            LOGGER.info("Nạp tiền thành công - Tài khoản: {}, Số dư mới: {}", getAccountNumber(), finalBalance);
        } catch (InvalidFundingAmountException e) {
            LOGGER.warn("Lỗi nạp tiền do số tiền không hợp lệ - Tài khoản: {}, Yêu cầu: {}", getAccountNumber(), amount, e);
        }
    }

    @Override
    public void withdraw(double amount) {
        LOGGER.info("Bắt đầu giao dịch rút tiền - Tài khoản: {}, Số tiền yêu cầu: {}", getAccountNumber(), amount);
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW_AMOUNT) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE_REQUIREMENT) {
                throw new InsufficientFundsException(amount);
            }
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS,
                    amount,
                    initialBalance,
                    finalBalance
            );
            addTransaction(transaction);
            LOGGER.info("Rút tiền thành công - Tài khoản: {}, Số dư còn lại: {}", getAccountNumber(), finalBalance);
        } catch (InvalidFundingAmountException e) {
            LOGGER.warn("Từ chối rút tiền (Vượt hạn mức hoặc số âm) - Tài khoản: {}, Yêu cầu: {}", getAccountNumber(), amount, e);
        } catch (InsufficientFundsException e) {
            LOGGER.warn("Từ chối rút tiền (Vi phạm số dư tối thiểu) - Tài khoản: {}, Yêu cầu: {}, Số dư hiện tại: {}", getAccountNumber(), amount, initialBalance, e);
        }
    }
}