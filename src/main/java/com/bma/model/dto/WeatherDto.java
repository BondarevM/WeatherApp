package com.bma.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("main")
    private String weather;
    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("speed")
    private Double windSpeed;
}
