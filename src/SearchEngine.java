import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SearchEngine {

    private static final int INITIAL_CAPACITY = 0;

    private static final Charset[] CHARSETS = {
            StandardCharsets.UTF_8
            ,StandardCharsets.UTF_16
            ,StandardCharsets.ISO_8859_1
            ,StandardCharsets.US_ASCII
            ,StandardCharsets.UTF_16BE
            ,StandardCharsets.UTF_16LE
    };

    public static SearchResult find(String query, String filePath)
            throws IOException {

        HashMap<String, List<int[]>> matches = new HashMap<>(INITIAL_CAPACITY);
        ArrayList<Path> files = new ArrayList<>();
        filesToList(Paths.get(filePath).toFile(), files);

        for (Path file : files) {
            for (Charset charset : CHARSETS) {
                try {
                    List<String> fileLines = Files.readAllLines(file, charset);
                    List<int[]> entries = getEntries(fileLines, query);

                    if (!entries.isEmpty()){
                        matches.put(file.toString(), getEntries(fileLines, query));
                    }
                } catch (MalformedInputException e) {
                    if (charset == CHARSETS[CHARSETS.length-1]){
                        e.printStackTrace();
                    }
                    continue;
                }
                //Debug out
//                System.out.println(file.toString() + "opened with encoding "
//                        + charset.toString());
                break;
            }

        }

        return new SearchResult(matches);
    }

    //Нахождение вхождений строки в подстроку.
    private static List<int[]> getEntries(List<String> lines, String query) {

        List<int[]> entries = new ArrayList<>(INITIAL_CAPACITY);

        if (lines.isEmpty()) {
            return entries;
        }

        for (int lineNumber = 0, index = 0; lineNumber < lines.size();
             index = 0, lineNumber++) {

            while ((index = lines.get(lineNumber).indexOf(query, index)) != -1) {
                //добавляем new int[]{<номер строки>, <индекс начала вхождения>}
                entries.add(new int[]{lineNumber,index});
                index += query.length();
            }
        }
        return entries;
    }

    private static boolean isEmpty(String str) { //проверяет строку на пустоту
            return str == null || str.length() == 0;
    }

    private static void filesToList(File file, List<Path> list) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                filesToList(f,list);
            }
        } else {
            list.add(file.toPath());
        }
    }
}
