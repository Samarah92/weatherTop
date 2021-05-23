package controllers;

import java.util.ArrayList;
import java.util.List;


import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Dashboard");
        Member member = Account.getLoggedInMember();

        List<Station> stations = Station.findAll();
        for (Station station : stations) {
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
                station.minWindSpeed = station.getMinWindSpeed(station);
                station.maxWindSpeed = station.getMaxWindSpeed(station);
                station.minPressure = station.getMinPressure(station);
                station.maxPressure = station.getMaxPressure(station);
            }

        }

        render("dashboard.html", stations);
    }


    public static void deleteStation(Long id) {

        Logger.info("Deleting a Station");
        Member member = Account.getLoggedInMember();
        Station station = Station.findById(id);
        member.stations.remove(station);
        member.save();
        station.delete();
        redirect("/dashboard");
    }

    public static void addStation(String name, double lat, double lng) {

        Logger.info("Adding a Station" + name);
        Member member = Account.getLoggedInMember();
        Station station = new Station(name, lat, lng);
        member.stations.add(station);
        member.save();

        redirect("/dashboard");
    }

}

