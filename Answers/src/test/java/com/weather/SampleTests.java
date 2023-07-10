package com.weather;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.model.Location;
import com.weather.model.Weather;
import com.weather.repository.WeatherRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class SampleTests{

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void setup() throws Exception {
        Location location = new Location("Nashville", "Tennessee", 36.1189f, -86.6892f);
        Weather weather = new Weather(11l, new Date(), location, Arrays.asList(37.3, 36.8, 36.4, 36.0, 35.6, 35.3, 35.0, 34.9, 35.8, 38.0, 40.2, 42.3, 43.8, 44.9, 45.5, 45.7, 44.9, 43.0, 41.7, 40.8, 39.9, 39.2, 38.6, 38.1));
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        weather.setDateRecorded(simpleDateFormat.parse("1985-01-01"));

        String requestJson = objectMapper.writeValueAsString(weather);
        mockMvc.perform(post("/weather")
                .contentType("application/json")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldDeleteAll() throws Exception {
        //checking status
        mockMvc.perform(delete("/weather/erase"))
                .andDo(print())
                .andExpect(status().isOk());

        //checking size of items
        mockMvc.perform(get("/weather")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(
                        status().isOk());

        assertEquals(0, weatherRepository.findAll().size());
    }

    @Test
    public void shouldCreateRecord() throws Exception {
        Location location = new Location("Nashville", "Tennessee", 36.1189f, -86.6892f);
        Weather weather = new Weather(22L, new Date(), location, Arrays.asList(37.3, 36.8, 36.4, 36.0, 35.6, 35.3, 35.0, 34.9, 35.8, 38.0, 40.2, 42.3, 43.8, 44.9, 45.5, 45.7, 44.9, 43.0, 41.7, 40.8, 39.9, 39.2, 38.6, 38.1));
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        weather.setDateRecorded(simpleDateFormat.parse("1985-01-01"));

        String requestJson = objectMapper.writeValueAsString(weather);
        mockMvc.perform(post("/weather")
                .contentType("application/json")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());

        assertNotNull(weatherRepository.findById(22L));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/weather")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(
                        status().isOk());
    }

    @Test
    public void getByDate() throws Exception {
        mockMvc.perform(get("/weather?start=1985-01-01"))
                .andDo(print())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(
                        status().isOk());
    }

    @Test
    public void getByLocation() throws Exception {
        mockMvc.perform(get("/weather")
                .param("lat", "36.1189")
                .param("lon", "-86.6892"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(
                        status().isOk());
    }
}
