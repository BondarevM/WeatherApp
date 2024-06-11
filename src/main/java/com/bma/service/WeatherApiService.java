package com.bma.service;

import com.bma.dao.LocationDao;
import com.bma.dao.SessionDao;
import com.bma.model.Location;
import com.bma.model.Session;
import com.bma.model.User;
import com.bma.model.WeatherApiResponse;
import com.bma.model.dto.LocationDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bma.model.dto.WeatherDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WeatherApiService {
    private static final LocationDao locationDao = LocationDao.getInstance();
    private static final SessionDao sessionDao = SessionDao.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_ID = "82fd33e1781ae51a60d4802fd61b69c2";

    private final HttpClient httpClient;

    private WeatherApiService() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

    }

    public WeatherApiService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public List<WeatherDto> getWeathersForCurrentUser(String sessionId) throws IOException, InterruptedException {
        Optional<Session> session = sessionDao.getSessionById(sessionId);
        List<WeatherDto> resultList = new ArrayList<>();
        if (session.isPresent()) {
            User user = session.get().getUser();
            List<Location> locations = locationDao.getLocationsByUser(user);

            List<WeatherApiResponse> listOfWeathersApiResponse = createListOfWeathersApiResponse(locations);

            for (int i = 0; i < locations.size(); i++) {
                WeatherDto weatherDto = WeatherDto.builder()
                        .cityName(locations.get(i).getName())
                        .weather(listOfWeathersApiResponse.get(i).getWeather().get(0).getDescription())
                        .temp(String.format("%.1f", listOfWeathersApiResponse.get(i).getMain().getTemp() - 273.15) + " °С")
                        .pressure(String.format("%.0f", listOfWeathersApiResponse.get(i).getMain().getPressure()) + " GPa")
                        .windSpeed(String.format("%.1f", listOfWeathersApiResponse.get(i).getWind().getSpeed()) + " m/s")
                        .icon(listOfWeathersApiResponse.get(i).getWeather().get(0).getIcon())
                        .latitude(locations.get(i).getLatitude())
                        .longitude(locations.get(i).getLongitude())
                        .build();
                resultList.add(weatherDto);
                System.out.println();
            }
        }
        return resultList;
    }

    private List<WeatherApiResponse> createListOfWeathersApiResponse(List<Location> locations) throws IOException, InterruptedException {
        List<WeatherApiResponse> list = new ArrayList<>();

        for (Location location : locations) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(buildUriToGetWeatherInfoForCurrentCity(location.getLatitude(), location.getLongitude())))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            list.add(objectMapper.readValue(response.body(), WeatherApiResponse.class));
        }
        return list;
    }

    public List<LocationDto> getLocations(String name) throws IOException, InterruptedException {

        String cityName = name.replace(" ", "-");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildUriToSearchLocationByCityName(cityName)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return createListOfLocations(response.body());
    }

    private List<LocationDto> createListOfLocations(String responseBody) throws JsonProcessingException {
        return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, LocationDto.class));
    }

    private static String buildUriToSearchLocationByCityName(String cityName) {
        return "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=7&appid=" + API_ID;
    }

    private static String buildUriToGetWeatherInfoForCurrentCity(Double lat, Double lon) {
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + lat.toString() + "&lon=" + lon.toString() + "&appid=" + API_ID;
    }

    public static WeatherApiService getInstance() {
        return INSTANCE;
    }

    private static final WeatherApiService INSTANCE = new WeatherApiService();

    private static void setHttpClientParams() {

    }
}
