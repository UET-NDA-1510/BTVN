public class FileManager {
    public String createPath(String folder, String fileName) {
        // LỖI: Dùng cứng dấu gạch chéo ngược của Windows
        return folder + "\\" + fileName;
    }
}