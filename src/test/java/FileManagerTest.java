import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    @Test
    public void testCreatePath() {
        FileManager fm = new FileManager();
        // Test này mong đợi định dạng Windows
        String result = fm.createPath("data", "report.txt");
        assertEquals("data\\report.txt", result);
    }
}