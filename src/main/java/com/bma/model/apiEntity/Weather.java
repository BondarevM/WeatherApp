package com.bma.model.apiEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;

}
