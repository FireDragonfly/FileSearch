import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class FileSearch {
    public static void main(String[] args) {

//        String filePath = "src/gamlet.txt";
        String filePath;

        try {
            //Создание потока ввода данных
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите путь/файл для поиска");
            filePath = br.readLine();
            System.out.println("Введите слово/фразу для поиска");
            String keyword = br.readLine();

            if(keyword.contains(" ")) {
                keyword = keyword.trim(); //Обрезка пробелов
            }

            //Поиск и "распаковка" результата
            SearchResult searchResult = SearchEngine.find(keyword, filePath);
            HashMap<String, List<int[]>> matches = searchResult.getMatches();
            String[] paths = new String[matches.size()];
            searchResult.getMatches().keySet().toArray(paths);

            System.out.println("\"" + keyword + "\" number of entries: " +
                    searchResult.getNumberOfMatches()
                    + " in "
                    + matches.size() + "files");

            for (int i = 0; i < matches.size(); i++) {
                System.out.print("File: " + paths[i]);
                List<int[]> list = matches.get(paths[i]);
                System.out.println("\t number of entries: " + list.size());
                for (int[] match : list) {
                    System.out.println("line: "
                            + (match[SearchResult.LINE_INDEX] + 1)
                            + ";\t position: "
                            + (match[SearchResult.MATCH_START_INDEX] + 1));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}