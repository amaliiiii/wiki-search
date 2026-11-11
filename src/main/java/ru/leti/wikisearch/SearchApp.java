package ru.leti.wikisearch;

import java.util.List;

// главный сценарий программы, сам ничего не умеет, только вызывает остальные классы
public class SearchApp {

    private final ConsoleInput input;
    private final WikipediaClient client;
    private final SearchResponseParser parser;
    private final ResultPrinter printer;
    private final BrowserOpener opener;

    public SearchApp(ConsoleInput input, WikipediaClient client, SearchResponseParser parser,
                     ResultPrinter printer, BrowserOpener opener) {
        this.input = input;
        this.client = client;
        this.parser = parser;
        this.printer = printer;
        this.opener = opener;
    }

    // крутимся в цикле, пока пользователь не захочет выйти
    public void run() {
        printer.printWelcome();
        while (true) {
            String query = input.readQuery();
            if (query == null) {
                break;
            }
            searchOnce(query);
        }
        printer.printMessage("до свидания!");
    }

    // один поиск от запроса до открытия статьи
    private void searchOnce(String query) {
        try {
            String json = client.search(query);                  // 2. запрос к серверу
            List<SearchResult> results = parser.parse(json);     // 3. разбор ответа
            if (results.isEmpty()) {
                printer.printMessage("ничего не найдено, попробуйте другой запрос");
                return;
            }
            printer.printResults(results);                       // 4. вывод результатов
            int choice = input.readChoice(results.size());
            if (choice != 0) {
                SearchResult selected = results.get(choice - 1);
                printer.printMessage("открываю: " + selected.getTitle());
                opener.open(selected.getUrl());                  // 5. открываем в браузере
            }
        } catch (WikipediaException e) {
            // любую нашу ошибку показываем и идем дальше, программа не падает
            printer.printError(e.getMessage());
        }
    }
}
