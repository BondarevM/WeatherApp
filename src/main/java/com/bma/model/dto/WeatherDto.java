package com.bma.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherDto {
    private String cityName;
    private String weather;
    private Double temp;
    private Double pressure;
    private Double windSpeed;
    private String icon;
    private Double longitude;
    private Double latitude;
}
