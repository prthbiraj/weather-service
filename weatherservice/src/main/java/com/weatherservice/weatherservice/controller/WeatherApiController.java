package com.weatherservice.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherservice.weatherservice.model.Weather;
import com.weatherservice.weatherservice.model.WeatherForecast;
import com.weatherservice.weatherservice.services.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {

	@Autowired
    private WeatherService weatherService;
  
    @RequestMapping("/now/{country}/{city}")
    public ResponseEntity<Weather> getWeather(@PathVariable String country,
                              @PathVariable String city) {
        return new ResponseEntity<>(this.weatherService.getWeather(country, city), HttpStatus.OK);
    }

    @RequestMapping("/tomorrow/{country}/{city}")
    public WeatherForecast getWeatherForecast(@PathVariable String country,
                                              @PathVariable String city) {
        return this.weatherService.getWeatherForecast(country, city);
    }
}
