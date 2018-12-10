import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSearch {
    public static void main(String[] args) {

        String filePath = "src/gamlet.txt";

        try {
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream("src/gamlet.txt"));
//            byte arr[] = new byte[in.available()];
//            in.read(arr);
//            String text = new String(arr);

            //Создание потока ввода данных
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String keyword = br.readLine();

            if(keyword.contains(" ")) {
                keyword = keyword.trim(); //Обрезка пробелов
            }

//            if(text.contains(keyword)) { //Если фраза найдена в тексте
//                System.out.println(keyword);
//            }

            SearchResult searchResult = SearchEngine.find(keyword, filePath);

            System.out.println("\"" + keyword + "\" number of entries: " +
                    searchResult.getNumberOfEntries());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}