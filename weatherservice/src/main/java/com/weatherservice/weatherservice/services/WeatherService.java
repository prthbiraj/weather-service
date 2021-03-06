package com.weatherservice.weatherservice.services;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.weatherservice.weatherservice.model.Weather;
import com.weatherservice.weatherservice.model.WeatherForecast;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class WeatherService {

	private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";

	private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q={city},{country}&APPID={key}";

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	private RestTemplate restTemplate;

	@Value("${app.weather.api.key}")
	private String apiKey;

	 public WeatherService(RestTemplateBuilder restTemplateBuilder) {
			this.restTemplate = restTemplateBuilder.build();
			}
	 
	public Weather getWeather(String country, String city) {
		logger.info("Requesting current weather for {}/{}", country, city);
		URI url = new UriTemplate(WEATHER_URL).expand(city, country, this.apiKey);
		return invoke(url, Weather.class);
	}

	public WeatherForecast getWeatherForecast(String country, String city) {
		logger.info("Requesting weather forecast for {}/{}", country, city);

		Instant instant = Instant.now();
		Instant nextDay = instant.plus(1, ChronoUnit.DAYS);

		URI url = new UriTemplate(FORECAST_URL).expand(city, country, this.apiKey);
		WeatherForecast invoke = invoke(url, WeatherForecast.class);

		invoke.setEntries(
				invoke.getEntries().stream()
						.filter(weather -> nextDay.truncatedTo(ChronoUnit.DAYS)
								.equals(weather.getTimestamp().truncatedTo(ChronoUnit.DAYS)))
						.collect(Collectors.toList()));

		return invoke;
	}

	private <T> T invoke(URI url, Class<T> responseType) {
		RequestEntity<?> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
		return exchange.getBody();
	}
}
