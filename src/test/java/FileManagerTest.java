import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    @Test
    public void testCreatePath() {
        FileManager fm = new FileManager();

        // Output của hàm (luôn bị hardcode là dấu \)
        String result = fm.createPath("data", "report.txt");

        // Giá trị mong đợi ĐÚNG PHẢI LÀ dấu của hệ điều hành đang chạy
        String expected = "data" + File.separator + "report.txt";

        // Tiến hành so sánh
        assertEquals(expected, result, "Đường dẫn bị sai định dạng OS!");
    }
}