package controllers;

import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller {
    public static void index(Long id) {
        Station station = Station.findById(id);
        List<Station> stations = Station.findAll();
        List<Reading> readings = station.readings;
        if (readings.size() > 0) {
            Reading latestReading = readings.get(readings.size() - 1);
            station.weatherCondition = station.getWeatherCondition(latestReading.code);
            station.farenheit = station.celsiusToFarenheit(latestReading.temperature);
            station.celsius = readings.get(readings.size() - 1).temperature;
            station.latestWindSpeed = station.convertToLatestWindSpeed(latestReading.windSpeed);
            station.latestPressure = readings.get(readings.size() - 1).pressure;
            station.latestWindDirection = station.convertToLatestWindDirection(latestReading.windDirection);
            station.windChill = station.latestWindChill(latestReading.temperature, latestReading.windSpeed);
            station.minTemperature = station.getMinTemperature(station);
            station.maxTemperature = station.getMaxTemperature(station);
            station.maxWindSpeed = station.getMaxWindSpeed(station);
            station.minWindSpeed = station.getMinWindSpeed(station);
            station.minPressure = station.getMinPressure(station);
            station.maxPressure = station.getMaxPressure(station);

        }

        Logger.info("Station id = " + id);
        render("station.html", station);
    }

    public static void deletereading(Long id, Long readingid) {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info("Removing " + reading.code);
        Logger.info("Removing" + reading.temperature);
        Logger.info("Removing" + reading.windSpeed);
        Logger.info("Removing" + reading.pressure);
        Logger.info("Removing" + reading.windDirection);
        station.readings.remove(reading);
        station.save();
        reading.delete();
        render("station.html", station);
    }

    public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, int pressure) {
        Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/" + id);
    }
}