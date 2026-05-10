import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Bank {
    private static final Logger LOGGER = Logger.getLogger(Bank.class.getName());
    private static final String ID_REGEX = "\\d{9}";
    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Thiết lập danh sách khách hàng mới.
     * Nếu danh sách truyền vào là null, khởi tạo một danh sách rỗng.
     *
     * @param customerList Danh sách khách hàng cần thiết lập.
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc danh sách khách hàng và tài khoản từ luồng dữ liệu.
     *
     * @param inputStream Luồng dữ liệu đầu vào chứa thông tin khách hàng.
     */
    public void readCustomerList(InputStream inputStream) {
        LOGGER.info("Bat dau doc du lieu...");
        if (inputStream == null) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer currentCustomer = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex <= 0) {
                    continue;
                }
                String token = line.substring(lastSpaceIndex + 1).trim();
                if (token.matches(ID_REGEX)) {
                    String name = line.substring(0, lastSpaceIndex).trim();
                    currentCustomer = new Customer(Long.parseLong(token), name);
                    customerList.add(currentCustomer);
                } else if (currentCustomer != null) {
                    // Tách khối lệnh phức tạp ra một hàm nhỏ riêng biệt (Extract Method)
                    parseAccountAndAddToCustomer(line, currentCustomer);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Lỗi truy xuất dữ liệu từ luồng (I/O Error)", e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Lỗi sai định dạng số trong dữ liệu", e);
        }
    }
    /**
     * Phân tích dòng dữ liệu tài khoản và thêm vào khách hàng hiện hành.
     *
     * @param line     Dòng dữ liệu tài khoản
     * @param customer Khách hàng nhận tài khoản
     */
    private void parseAccountAndAddToCustomer(String line, Customer customer) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
            return;
        }
        try {
            long accountNumber = Long.parseLong(parts[0]);
            String accountType = parts[1];
            double balance = Double.parseDouble(parts[2]);
            if (Account.CHECKING_TYPE.equals(accountType)) {
                customer.addAccount(new CheckingAccount(accountNumber, balance));
            } else if (Account.SAVINGS_TYPE.equals(accountType)) {
                customer.addAccount(new SavingsAccount(accountNumber, balance));
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Dữ liệu dòng tài khoản sai định dạng số, bỏ qua dòng: " + line, e);
        }
    }
    public String getCustomersInfoByIdOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        // Sử dụng Lambda (Method Reference) thay vì Anonymous Class
        sortedList.sort(Comparator.comparingLong(Customer::getIdNumber));
        return formatCustomerList(sortedList);
    }
    /**
     * Lấy danh sách thông tin khách hàng, sắp xếp theo Tên (Alphabet).
     * Nếu trùng tên sẽ xét theo ID tăng dần.
     *
     * @return Chuỗi thông tin của toàn bộ khách hàng.
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        sortedList.sort((c1, c2) -> {
            int compareName = c1.getFullName().compareTo(c2.getFullName());
            if (compareName != 0) {
                return compareName;
            }
            return Long.compare(c1.getIdNumber(), c2.getIdNumber()
            );
        });
        return formatCustomerList(sortedList);
    }
    /**
     * Phương thức hỗ trợ định dạng danh sách khách hàng thành một chuỗi duy nhất.
     * Chống lặp code giữa các hàm lấy thông tin.
     *
     * @param list Danh sách khách hàng đã được sắp xếp.
     * @return Chuỗi kết quả gom nhóm.
     */
    private String formatCustomerList(List<Customer> list) {
        // Dùng Java Stream API thay cho vòng lặp + String Concatenation truyền thống
        return list.stream()
                .map(Customer::getCustomerInfo)
                .collect(Collectors.joining("\n"));
    }
}