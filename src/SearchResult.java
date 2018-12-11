import java.util.HashMap;
import java.util.List;

public class SearchResult {

    public static final int LINE_INDEX = 0;
    public static final int MATCH_START_INDEX = 1;

    private HashMap<String, List<int[]>> matches;

    private String query;

    public SearchResult() {
        this.matches = null;
    }

    public SearchResult(HashMap<String, List<int[]>> entries) {
        this.matches = entries;
    }

    public boolean isFound() {
        return !matches.isEmpty();
    }

    public int getNumberOfMatches() {
        //Извлечение ключей в масив
        String[] filesPath = new String[matches.size()];
        matches.keySet().toArray(filesPath);

        //Подсчет вхождений
        int count = 0;
        for (int i = 0; i < filesPath.length; i++) {
            count += matches.get(filesPath[i]).size();
        }
        return count;
    }
    public HashMap<String, List<int[]>> getMatches() {
        return matches;
    }

    public String getQuery() {
        return query;
    }
}
