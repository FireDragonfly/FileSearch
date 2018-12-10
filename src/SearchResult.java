public class SearchResult {

    private int numberOfEntries;

    public SearchResult(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }

    public boolean isFound() {
        return numberOfEntries > 0;
    }
}
