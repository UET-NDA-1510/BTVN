import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public String createPath(String folder, String fileName) {
        // SỬ DỤNG: java.nio.file.Paths để tự động chọn separator (/, \)
        Path path = Paths.get(folder, fileName);
        return path.toString();
    }
}