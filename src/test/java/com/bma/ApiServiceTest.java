package com.bma;

import com.bma.model.dto.LocationDto;
import com.bma.service.WeatherApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApiServiceTest {
    private static HttpClient mockClient;
    private static WeatherApiService weatherApiService;

    @BeforeAll
    public static void InitSources() {
        mockClient = Mockito.mock(HttpClient.class);
        weatherApiService = new WeatherApiService(mockClient);
    }

    @Test
    public void getLocationsShouldReturnListOfLocations() throws  InterruptedException, IOException {

        String jsonResponse = "[{\"name\":\"New York\",\"lat\":40.7128,\"lon\":-74.0060}]";
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(jsonResponse);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        List<LocationDto> locations = weatherApiService.getLocations("New York");

        Assertions.assertEquals(1, locations.size());
        Assertions.assertEquals("New York", locations.get(0).getName());
    }

    @Test
    public void getLocationsShouldThrowException() throws IOException, InterruptedException {

        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(404);
        when(httpResponse.body()).thenReturn("Not Found");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        Assertions.assertThrows(IOException.class, () -> {
            weatherApiService.getLocations("Krasnoyarsk");
        });
    }

}
