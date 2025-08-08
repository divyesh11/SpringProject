package com.spring.project.service;

import com.spring.project.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    public static final String API_URL = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY_NAME";
    public final String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    WeatherService(@Value("${api.keys.weather}") String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String url = API_URL.replace("API_KEY", apiKey).replace("CITY_NAME", city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
