package ru.leti.wikisearch;

// одна найденная статья, поля совпадают с названиями в json
public class SearchResult {

    // начало ссылки на статью, в конце добавляем pageid
    private static final String ARTICLE_URL = "https://ru.wikipedia.org/w/index.php?curid=";

    private String title;    // название статьи
    private long pageid;     // номер статьи
    private String snippet;  // короткий кусочек текста

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet == null ? "" : snippet;
    }

    // собираем ссылку на статью по номеру
    public String getUrl() {
        return ARTICLE_URL + pageid;
    }
}
