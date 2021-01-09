package com.weatherservice.weatherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeatherAppProperties.class)
public class WeatherserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherserviceApplication.class, args);
	}

}
