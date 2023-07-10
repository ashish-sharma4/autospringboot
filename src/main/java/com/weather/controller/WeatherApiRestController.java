package com.weather.controller;

import com.weather.model.Weather;
import com.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class WeatherApiRestController {
    @Autowired
    WeatherRepository weatherRepository;

    //1. erasing all weather date
    @RequestMapping(value = "/weather/erase", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRecords() {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    //2. erasing all weather date by date range and location coordinates
    @RequestMapping(value = "/weather/erase", method = RequestMethod.DELETE, params = {"start", "end", "lat", "lon"})
    public ResponseEntity<?> deleteByDateRangeAndLoc(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam Float lat, @RequestParam Float lon) {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    //3. adding new weather data
    @RequestMapping(value = "/weather", method = RequestMethod.POST)
    public ResponseEntity<?> addRecord(@RequestBody Weather newRecord) {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    //4. returning all weather data
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<?> getRecords() {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    //5. returning all weather data by date
    @RequestMapping(value = "/weather", params = "start", method = RequestMethod.GET)
    public ResponseEntity<?> getRecordsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    //6. returning all weather data by location coordinate
    @RequestMapping(value = "/weather", params = {"lat", "lon"}, method = RequestMethod.GET)
    public ResponseEntity<?> getRecordsByLocationCoordinates(@RequestParam Float lat, @RequestParam Float lon) {
    	return new ResponseEntity<String>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
