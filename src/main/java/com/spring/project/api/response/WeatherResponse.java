package com.spring.project.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {

    @JsonProperty("current")
    private Current current;

    @Data
    public static class Current {
        @JsonProperty("feelslike")
        private int feelslike;

        @JsonProperty("is_day")
        private String isDay;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("cloudcover")
        private int cloudcover;

        @JsonProperty("temperature")
        private int temperature;

        @JsonProperty("humidity")
        private int humidity;
    }
}