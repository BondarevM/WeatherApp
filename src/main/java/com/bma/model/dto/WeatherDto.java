package com.bma.model.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherDto {
    private String cityName;
    private String weather;
    private String temp;
    private String pressure;
    private String windSpeed;
    private String icon;
    private Double longitude;
    private Double latitude;
}
