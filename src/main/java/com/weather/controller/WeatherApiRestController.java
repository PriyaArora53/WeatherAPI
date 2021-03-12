
package com.weather.controller;

import com.weather.dto.WeatherStats;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.weather.exception.DuplicateWeatherDataException;
import com.weather.exception.WeatherDataNotFoundException;
import com.weather.model.Weather;
import com.weather.service.WeatherService;

@RestController
public class WeatherApiRestController {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

    private WeatherService weatherService;

    @Autowired
    public WeatherApiRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @PostMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Weather createWeather(@Valid @RequestBody Weather weatherData) throws DuplicateWeatherDataException {
        return weatherService.create(weatherData);
    }


    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Weather> getAllWeatherData() {
        return weatherService.getAllWeatherData();
    }


    @GetMapping(value = "/weather",
            params = {"lat", "lon"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Weather> getAllWeatherDataForGivenLatitudeAndLongitude(
            @Valid @RequestParam("lat") float latitude,
            @Valid @RequestParam("lon") float longitude) throws WeatherDataNotFoundException {
        return weatherService.getAllWeatherDataForGivenLatitudeAndLongitude(latitude, longitude);
    }

    @GetMapping(value = "/weather/temperature",
            params = {"start", "end"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<WeatherStats> getAllWeatherDataForGivenDateRange(
            @Valid @RequestParam("start") String startDate,
            @Valid @RequestParam("end") String endDate) throws ParseException {

        return weatherService.getAllWeatherDataForGivenDateRange(simpleDateFormat.parse(startDate),
                simpleDateFormat.parse(endDate));
    }


    @DeleteMapping(value = "/erase")
    @ResponseStatus(HttpStatus.OK)
    public void eraseAllWeatherInformation() {
        weatherService.eraseAllWeatherData();
    }


    @DeleteMapping(value = "/erase", params = {"start", "end", "lat", "lon"})
    @ResponseStatus(HttpStatus.OK)
    public void eraseSpecificWeatherData(@Valid @RequestParam("start") String startDate,
                                         @Valid @RequestParam("end") String endDate,
                                         @Valid @RequestParam("lat") float latitude,
                                         @Valid @RequestParam("lon") float longitude
    ) throws ParseException {


        weatherService.eraseWeatherDataForGivenDateRangeAndLocation(simpleDateFormat.parse(startDate),
                simpleDateFormat.parse(endDate), latitude, longitude);
    }

}


