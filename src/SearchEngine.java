import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SearchEngine {

    public static SearchResult find(String query, String filePath) {

        try {
            List<String> fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SearchResult( 0);
     }

}
