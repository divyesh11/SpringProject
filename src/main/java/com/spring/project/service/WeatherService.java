package com.spring.project.service;

import com.spring.project.api.response.WeatherResponse;
import com.spring.project.cache.AppCache;
import com.spring.project.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private AppCache appCache;
    public final String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    WeatherService(@Value("${api.keys.weather}") String apiKey, RestTemplate restTemplate, AppCache appCache) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    public WeatherResponse getWeather(String city) {
        String url = appCache.get("weather_api").replace(Placeholders.apiKey, apiKey).replace(Placeholders.cityName, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
