package viselitsa;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WordsLoadingUtil {
    public static List<String> loadWords(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }
}
