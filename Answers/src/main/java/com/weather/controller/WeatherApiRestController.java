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
    public ResponseEntity deleteRecords() {
        weatherRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);

    }

    //2. erasing all weather date by date range and location coordinates
    @RequestMapping(value = "/weather/erase", method = RequestMethod.DELETE, params = {"start", "end", "lat", "lon"})
    public ResponseEntity deleteByDateRangeAndLoc(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam Float lat, @RequestParam Float lon) {
        weatherRepository.deleteByDateRangeAndLocCoordinates(start, end, lat, lon);
        return new ResponseEntity(HttpStatus.OK);
    }

    //3. adding new weather data
    @RequestMapping(value = "/weather", method = RequestMethod.POST)
    public ResponseEntity addRecord(@RequestBody Weather newRecord) {
        if (weatherRepository.findById(newRecord.getId()) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            weatherRepository.save(newRecord);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    //4. returning all weather data
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<List<Weather>> getRecords() {
        List<Weather> data = weatherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    //5. returning all weather data by date
    @RequestMapping(value = "/weather", params = "start", method = RequestMethod.GET)
    public ResponseEntity<List<Weather>> getRecordsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
        List<Weather> data = weatherRepository.findByDateRecorded(start);
        if (!data.isEmpty()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
    }

    //6. returning all weather data by location coordinate
    @RequestMapping(value = "/weather", params = {"lat", "lon"}, method = RequestMethod.GET)
    public ResponseEntity<List<Weather>> getRecordsByLocationCoordinates(@RequestParam Float lat, @RequestParam Float lon) {
        List<Weather> data = weatherRepository.findByLocationLatitudeAndLocationLongitude(lat, lon);
        if (!data.isEmpty()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
    }
}
