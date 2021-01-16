package com.weatherservice.weatherservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.weatherservice.weatherservice.services.WeatherService;

@WebMvcTest(controllers = WeatherApiController.class)
public class WeatherApiControllerTest{

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getWeather_success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/now/UK/London"))
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	
	@Test
	public void getWeatherForecast_success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/tomorrow/UK/London"))
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	
	
}
