package com.spring.project.service;

import com.spring.project.api.response.WeatherResponse;
import com.spring.project.cache.AppCache;
import com.spring.project.constants.Placeholders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class WeatherService {
    private AppCache appCache;
    public final String apiKey;
    private final RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;

    @Autowired
    WeatherService(@Value("${api.keys.weather}") String apiKey, RestTemplate restTemplate, AppCache appCache) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    private String getCacheKey(String city) {
        return "weather:" + city;
    }

    public WeatherResponse getWeather(String city) {
        WeatherResponse cachedWeather = redisService.getValue(getCacheKey(city), WeatherResponse.class);
        if (cachedWeather != null) {
            log.info("Returning cached weather data for city: {}", city);
            return cachedWeather;
        }
        String url = appCache.get("weather_api").replace(Placeholders.apiKey, apiKey).replace(Placeholders.cityName, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            WeatherResponse weatherResponse = response.getBody();
            redisService.setValue(getCacheKey(city), weatherResponse, 1000);
        }
        return response.getBody();
    }
}
