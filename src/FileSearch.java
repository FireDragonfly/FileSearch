import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSearch {
    public static void main(String[] args) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("src/gamlet.txt"));
            byte arr[] = new byte[in.available()];
            in.read(arr);
            String text = new String(arr);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String keyword;
            keyword = br.readLine();
            if(keyword.contains(" "))
                keyword = keyword.trim();
            if(text.contains(keyword)){
                System.out.println(keyword);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}