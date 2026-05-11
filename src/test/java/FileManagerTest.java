import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    @Test
    public void testCreatePathCrossPlatform() {
        FileManager fm = new FileManager();
        String result = fm.createPath("data", "report.txt");

        // Sử dụng File.separator để lấy dấu gạch chéo đúng của môi trường hiện tại
        String expected = "data" + File.separator + "report.txt";

        assertEquals(expected, result, "Đường dẫn phải tương thích với OS hiện tại");
    }
}