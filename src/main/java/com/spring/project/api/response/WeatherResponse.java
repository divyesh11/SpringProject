package com.spring.project.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    @JsonProperty("request")
    private Request request;

    @JsonProperty("current")
    private Current current;

    @JsonProperty("location")
    private Location location;

    @Data
    public static class Request {

        @JsonProperty("unit")
        private String unit;

        @JsonProperty("query")
        private String query;

        @JsonProperty("language")
        private String language;

        @JsonProperty("type")
        private String type;
    }

    @Data
    public static class Current {

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        @JsonProperty("observation_time")
        private String observationTime;

        @JsonProperty("wind_degree")
        private int windDegree;

        @JsonProperty("visibility")
        private int visibility;

        @JsonProperty("weather_icons")
        private List<String> weatherIcons;

        @JsonProperty("feelslike")
        private int feelslike;

        @JsonProperty("is_day")
        private String isDay;

        @JsonProperty("air_quality")
        private AirQuality airQuality;

        @JsonProperty("wind_dir")
        private String windDir;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("cloudcover")
        private int cloudcover;

        @JsonProperty("astro")
        private Astro astro;

        @JsonProperty("precip")
        private int precip;

        @JsonProperty("uv_index")
        private int uvIndex;

        @JsonProperty("temperature")
        private int temperature;

        @JsonProperty("humidity")
        private int humidity;

        @JsonProperty("wind_speed")
        private int windSpeed;

        @JsonProperty("weather_code")
        private int weatherCode;
    }

    @Data
    public static class Location {

        @JsonProperty("localtime")
        private String localtime;

        @JsonProperty("utc_offset")
        private String utcOffset;

        @JsonProperty("country")
        private String country;

        @JsonProperty("localtime_epoch")
        private int localtimeEpoch;

        @JsonProperty("name")
        private String name;

        @JsonProperty("timezone_id")
        private String timezoneId;

        @JsonProperty("lon")
        private String lon;

        @JsonProperty("region")
        private String region;

        @JsonProperty("lat")
        private String lat;
    }

    @Data
    public static class Astro {

        @JsonProperty("moonset")
        private String moonset;

        @JsonProperty("moon_illumination")
        private int moonIllumination;

        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("moon_phase")
        private String moonPhase;

        @JsonProperty("sunset")
        private String sunset;

        @JsonProperty("moonrise")
        private String moonrise;
    }

    @Data
    public static class AirQuality {

        @JsonProperty("no2")
        private String no2;

        @JsonProperty("o3")
        private String o3;

        @JsonProperty("us-epa-index")
        private String usEpaIndex;

        @JsonProperty("so2")
        private String so2;

        @JsonProperty("pm2_5")
        private String pm25;

        @JsonProperty("pm10")
        private String pm10;

        @JsonProperty("co")
        private String co;

        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;
    }
}