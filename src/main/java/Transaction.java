import java.util.Locale;

/**
 * Đại diện cho một giao dịch ngân hàng.
 * Lưu trữ thông tin chi tiết về loại giao dịch, số tiền và sự thay đổi của số dư.
 */
public class Transaction {

    public static final int TYPE_DEPOSIT_CHECKING = 1;
    public static final int TYPE_WITHDRAW_CHECKING = 2;
    public static final int TYPE_DEPOSIT_SAVINGS = 3;
    public static final int TYPE_WITHDRAW_SAVINGS = 4;

    private int type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    /**
     * Khởi tạo một giao dịch mới.
     *
     * @param type           Loại giao dịch (sử dụng các hằng số TYPE_*).
     * @param amount         Số tiền giao dịch.
     * @param initialBalance Số dư ban đầu trước khi thực hiện giao dịch.
     * @param finalBalance   Số dư cuối cùng sau khi thực hiện giao dịch.
     */
    public Transaction(int type, double amount, double initialBalance, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    /**
     * Chuyển đổi mã định danh của loại giao dịch thành chuỗi mô tả chi tiết.
     *
     * @param transactionType Mã loại giao dịch cần chuyển đổi.
     * @return Chuỗi mô tả loại giao dịch, hoặc "Không rõ" nếu mã không hợp lệ.
     */
    public static String getTypeString(int transactionType) {
        switch (transactionType) {
            case TYPE_DEPOSIT_CHECKING:
                return "Nạp tiền vãng lai";
            case TYPE_WITHDRAW_CHECKING:
                return "Rút tiền vãng lai";
            case TYPE_DEPOSIT_SAVINGS:
                return "Nạp tiền tiết kiệm";
            case TYPE_WITHDRAW_SAVINGS:
                return "Rút tiền tiết kiệm";
            default:
                return "Không rõ";
        }
    }
    /**
     * Trích xuất bản tóm tắt chi tiết của giao dịch dưới dạng chuỗi văn bản.
     * Được định dạng chuẩn để dễ dàng in ấn hoặc hiển thị trên giao diện.
     *
     * @return Chuỗi tóm tắt thông tin giao dịch.
     */
    public String getTransactionSummary() {
        String typeDescription = getTypeString(this.type);
        return String.format(
                Locale.US,
                "- Kiểu giao dịch: %s. Số dư ban đầu: $%.2f. Số tiền: $%.2f. Số dư cuối: $%.2f.",
                typeDescription,
                initialBalance,
                amount,
                finalBalance
        );
    }
}