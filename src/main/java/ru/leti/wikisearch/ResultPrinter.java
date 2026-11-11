package ru.leti.wikisearch;

import java.util.List;

// отвечает только за вывод на экран
public class ResultPrinter {

    public void printWelcome() {
        System.out.println("=== поиск по википедии ===");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println("ошибка: " + message);
    }

    // печатаем нумерованный список статей
    public void printResults(List<SearchResult> results) {
        System.out.println("\nнайдено статей: " + results.size());
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            System.out.println((i + 1) + ". " + result.getTitle());
            System.out.println("   " + cleanSnippet(result.getSnippet()));
        }
    }

    // в кусочке текста от сервера есть html-теги, убираем их
    private String cleanSnippet(String snippet) {
        return snippet.replaceAll("<[^>]+>", "")
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&nbsp;", " ");
    }
}
