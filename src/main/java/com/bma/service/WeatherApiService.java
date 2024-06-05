package com.bma.service;

import com.bma.model.Location;
import com.bma.model.dto.LocationDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WeatherApiService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private  static final String API_ID = "82fd33e1781ae51a60d4802fd61b69c2";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

//    public static void main(String[] args) throws IOException, InterruptedException {
//
//
//        WeatherApiService instance = getInstance();
//        instance.getLocations("Krasnoyarsk");
//    }
    public List<LocationDto> getLocations(String cityName) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildUriToSearchLocationByCityName(cityName)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return createListOfLocations(response.body());
    }

    private List<LocationDto> createListOfLocations(String requestBody) throws JsonProcessingException {
        List<LocationDto> locations = objectMapper.readValue(requestBody, objectMapper.getTypeFactory().constructCollectionType(List.class, LocationDto.class));
        for (LocationDto location : locations){
            System.out.println(location);
        }

        return locations;
    }

    private static String buildUriToSearchLocationByCityName(String cityName){
        return "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=6&appid=" + API_ID;
    }

    public static WeatherApiService getInstance(){
        return INSTANCE;
    }

    private static final WeatherApiService INSTANCE = new WeatherApiService();
    private static void setHttpClientParams() {

    }
}
