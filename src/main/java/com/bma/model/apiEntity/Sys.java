package com.bma.model.apiEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {
    @JsonProperty("sunrise")
    private Double sunrise;
    @JsonProperty("sunset")
    private Double sunset;
}
