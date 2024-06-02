package com.bma.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WeatherApiService {
    private static final String API_URI = "123";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=Krasnoyarsk&appid=82fd33e1781ae51a60d4802fd61b69c2"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }

    public static WeatherApiService getInstance(){
        return INSTANCE;
    }

    private static final WeatherApiService INSTANCE = new WeatherApiService();
    private static void setHttpClientParams() {

    }
}
