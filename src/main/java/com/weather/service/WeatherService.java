package com.weather.service;

import com.weather.dto.WeatherStats;
import com.weather.exception.DuplicateWeatherDataException;
import com.weather.exception.WeatherDataNotFoundException;
import com.weather.model.Weather;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WeatherService   {

    void eraseAllWeatherData();

    void eraseWeatherDataForGivenDateRangeAndLocation(Date startDate, Date endDate, Float latitude, Float longitude) throws ParseException;

    Weather create(Weather weather) throws DuplicateWeatherDataException;

    List<Weather> getAllWeatherData();

    List<Weather> getAllWeatherDataForGivenLatitudeAndLongitude(Float  latitude, Float longitudde)
            throws WeatherDataNotFoundException;

    List<WeatherStats> getAllWeatherDataForGivenDateRange(Date startDate, Date endDate);
}
