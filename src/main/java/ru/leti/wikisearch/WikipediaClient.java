package ru.leti.wikisearch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

// отвечает только за запрос к серверу, json не разбирает
public class WikipediaClient {

    // адрес api, в конце будет запрос пользователя
    private static final String API_URL =
            "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srlimit=10&srsearch=";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // отправляем запрос и возвращаем ответ в виде строки
    public String search(String query) throws WikipediaException {
        // убираем недопустимые символы (percent-encoding)
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL + encodedQuery))
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent", "WikiSearchStudentApp/1.0")
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            // 200 - значит все хорошо
            if (response.statusCode() != 200) {
                throw new WikipediaException("сервер вернул ошибку, код: " + response.statusCode());
            }
            return response.body();
        } catch (IOException e) {
            throw new WikipediaException("не получилось связаться с википедией, проверьте интернет", e);
        } catch (InterruptedException e) {
            // возвращаем флаг прерывания, так положено делать
            Thread.currentThread().interrupt();
            throw new WikipediaException("запрос был прерван", e);
        }
    }
}
