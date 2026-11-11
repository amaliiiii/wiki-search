package ru.leti.wikisearch;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

// отвечает только за открытие ссылки в браузере
public class BrowserOpener {

    public void open(String url) throws WikipediaException {
        // проверяем, что на этом компьютере можно открыть браузер
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new WikipediaException("не могу открыть браузер, откройте ссылку сами: " + url);
        }
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            throw new WikipediaException("не получилось открыть браузер, ссылка: " + url, e);
        }
    }
}
