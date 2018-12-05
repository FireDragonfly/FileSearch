import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class  FileSearch {
public static void main (String[]args) throws IOException {
    Path path = Paths.get("src/gamlet.txt").toAbsolutePath();
    List<String> titles = Files.lines(path).collect(Collectors.toList());

    String searchGame = getInput();

    displayResults(searchGame, titles);
}

public static String getInput() {
        Scanner sc= new Scanner(System.in);
        System.out.print("Write the words you need to find in the book: ");
        String titlename =  sc.nextLine();
        sc.close();

        return titlename;
}

public static void displayResults(String searchName,List<String> titles) throws IOException {
    boolean inFile= titles.stream().anyMatch(p->p.equalsIgnoreCase(searchName));

    if (inFile) {
        System.out.println("\nYes, "+searchName+" this word is IN this book!");
    } else {
        System.out.println("\nNo, "+searchName+" this word is NOT in this book!");

    }
}

}

