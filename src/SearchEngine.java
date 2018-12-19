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

        //Создаем все колекции и ищем файлы по казанному пути
        HashMap<String, List<int[]>> matches = new HashMap<>(INITIAL_CAPACITY);
        ArrayList<Path> files = new ArrayList<>();
        filesToList(Paths.get(filePath).toFile(), files);

        //для каждого найденного файла
        for (Path file : files) {
            //Подбираем кодировку
            for (Charset charset : CHARSETS) {
                try {
                    //Пробуем считать строки из файла в заданной кодировке
                    List<String> fileLines = Files.readAllLines(file, charset);
                    //если кодирока подошла и ошибки не возникло, то ищем
                    // совпадения в файле
                    List<int[]> entries = getEntries(fileLines, query);

                    //если совпадения найдены
                    if (!entries.isEmpty()){
                        //ложим имя файла и результат поиска в колекцию
                        matches.put(file.toString(), getEntries(fileLines, query));
                    }
                } catch (MalformedInputException e) {
                    //Если ни одна кодировка не подошла печатаем стек ощибок.
                    if (charset == CHARSETS[CHARSETS.length-1]){
                        e.printStackTrace();
                    }
                    //продолжаем подбор если не последняя кодирока
                    continue;
                }
                //прекращаем подбор если файл успешно ткрыт.
                break;
            }
        }

        return new SearchResult(matches);
    }

    //Нахождение вхождений строки в подстроку.
    private static List<int[]> getEntries(List<String> lines, String query) {

        //создаем пустой лист
        List<int[]> entries = new ArrayList<>(INITIAL_CAPACITY);

        if (lines.isEmpty()) {
            return entries; //возвращвем пустой лист если строк в файле нет
            // (файл пустой)
        }

        //Ищем совпадения в каждой строке.
        for (int lineNumber = 0, index = 0; lineNumber < lines.size();
             index = 0, lineNumber++) {

            while ((index = lines.get(lineNumber).indexOf(query, index)) != -1) {
                //если совпадение найдено
                //добавляем new int[]{<номер строки>, <индекс начала вхождения>}
                entries.add(new int[]{lineNumber,index});
                index += query.length();
            }
        }
        return entries;
    }

    //проверяет строку на пустоту
    private static boolean isEmpty(String str) {
        return str == null ;
    }

    //Рекурсивный метод создания списка файлов находяшихся по указанному пути.
    //Если указан файл а не папка, то в списке будет один файл.
    //Если указана папка, то в списке будут все файлы из нее и вложенных папок.
    private static void filesToList(File file, List<Path> list) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                filesToList(f,list);
            }
        } else {
            list.add(file.toPath());
        }
    }}
