package models;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

import static java.lang.Math.pow;

@Entity
public class Station extends Model {
    public String name;
    public double lat;
    public double lng;
    public Reading reading;
    public String weatherCondition;
    public double farenheit;
    public double celsius;
    public int latestWindSpeed;
    public int latestPressure;
    public String latestWindDirection;
    public double windChill;
    public double roundWindChill;
    public double minTemperature;
    public double maxTemperature;
    public double minWindSpeed;
    public double maxWindSpeed;
    public double minPressure;
    public double maxPressure;

    @OneToMany(cascade = CascadeType.ALL)

    public List<Reading> readings = new ArrayList<Reading>();


    public Station(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.reading = reading;
        this.weatherCondition = weatherCondition;
        this.farenheit = farenheit;
        this.celsius = celsius;
        this.latestWindSpeed = latestWindSpeed;
        this.latestWindDirection = latestWindDirection;
        this.latestPressure = latestPressure;
        this.windChill = windChill;
        this.roundWindChill = roundWindChill;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minWindSpeed = minWindSpeed;
        this.maxWindSpeed = maxWindSpeed;
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;


    }

    public static String getWeatherCondition(int code) {
        String weatherCondition = null;
        switch (code) {
            case 100:
                weatherCondition = "Clear";
                break;
            case 200:
                weatherCondition = "Partial Clouds";
                break;
            case 300:
                weatherCondition = "Cloudy";
                break;
            case 400:
                weatherCondition = "Light Showers";
                break;
            case 500:
                weatherCondition = "Heavy Showers";
                break;
            case 600:
                weatherCondition = "Rain";
                break;
            case 700:
                weatherCondition = "Snow";
                break;
            case 800:
                weatherCondition = "Thunder";
                break;
            default:
                weatherCondition = "Invalid Condition";

        }
        return weatherCondition;
    }

    public static void farenheitConversion() {
        double farenheit = celsiusToFarenheit(6.0);
        System.out.println("Farenheit value is: " + farenheit);
    }

    public static double celsiusToFarenheit(double celsius) {
        double result = (celsius * 9.0 / 5.0 + 32.0);
        return result;

    }

    public static int convertToLatestWindSpeed(double windSpeed) {
        if (windSpeed <= 1) {
            return 0;
        } else if (windSpeed >= 1 && windSpeed <= 5) {
            return 1;
        } else if (windSpeed >= 6 && windSpeed <= 11) {
            return 2;
        } else if (windSpeed >= 12 && windSpeed <= 19) {
            return 3;
        } else if (windSpeed >= 20 && windSpeed <= 28) {
            return 4;
        } else if (windSpeed >= 29 && windSpeed <= 38) {
            return 5;
        } else if (windSpeed >= 39 && windSpeed <= 49) {
            return 6;
        } else if (windSpeed >= 50 && windSpeed <= 61) {
            return 7;
        } else if (windSpeed >= 62 && windSpeed <= 74) {
            return 8;
        } else if (windSpeed >= 75 && windSpeed <= 88) {
            return 9;
        } else if (windSpeed <= 89 && windSpeed <= 102) {
            return 10;
        } else
            return 11;
    }

    public static String convertToLatestWindDirection(double windDirection) {
        String latestWindDirection = null;

        if (windDirection >= 348.75 && windDirection <= 11.25) {
            latestWindDirection = "North";
        } else if (windDirection >= 11.25 && windDirection <= 33.75) {
            latestWindDirection = "North North East";
        } else if (windDirection >= 33.75 && windDirection <= 56.25) {
            latestWindDirection = "North East";
        } else if (windDirection >= 56.25 && windDirection <= 78.75) {
            latestWindDirection = "East North East";
        } else if (windDirection >= 78.75 && windDirection <= 101.25) {
            latestWindDirection = "East";
        } else if (windDirection >= 101.25 && windDirection <= 123.75) {
            latestWindDirection = "East South East";
        } else if (windDirection >= 123.75 && windDirection <= 146.25) {
            latestWindDirection = "South East";
        } else if (windDirection >= 146.25 && windDirection <= 168.75) {
            latestWindDirection = "South South East";
        } else if (windDirection >= 168.75 && windDirection <= 191.25) {
            latestWindDirection = "South";
        } else if (windDirection >= 191.25 && windDirection <= 213.75) {
            latestWindDirection = "South South West";
        } else if (windDirection >= 213.75 && windDirection <= 236.25) {
            latestWindDirection = "South West";
        } else if (windDirection >= 236.25 && windDirection <= 258.75) {
            latestWindDirection = "West South West";
        } else if (windDirection >= 258.75 && windDirection <= 281.25) {
            latestWindDirection = "West";
        } else if (windDirection >= 281.25 && windDirection <= 303.75) {
            latestWindDirection = "West North West";
        } else if (windDirection >= 303.75 && windDirection <= 326.25) {
            latestWindDirection = "North West";
        } else if (windDirection >= 326.25 && windDirection <= 348.75) {
            latestWindDirection = "North North West";
        } else {
            latestWindDirection = "No Value";
        }
        return latestWindDirection;
    }

    public static double latestWindChill(double temperature, double windSpeed) {
        double a = pow(windSpeed, 0.16);
        double result = 13.12 + (0.6215 * temperature) - (11.37 * a) + (0.3965 * temperature * a);
        return Math.round(result * 100.0) / 100.0;
    }

    public static double getMinTemperature(Station station) {

        Reading minTemperature = null;
        if (station.readings.size() > 0) {
            minTemperature = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.temperature < minTemperature.temperature) {
                    minTemperature = reading;
                }
            }
        }
        return minTemperature.temperature;
    }

    public static double getMaxTemperature(Station station) {

        Reading maxTemperature = null;
        if (station.readings.size() > 0) {
            maxTemperature = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.temperature > maxTemperature.temperature) {
                    maxTemperature = reading;
                }
            }
        }
        return maxTemperature.temperature;
    }

    public static double getMaxWindSpeed(Station station) {

        Reading maxWindSpeed = null;
        if (station.readings.size() > 0) {
            maxWindSpeed = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.windSpeed > maxWindSpeed.windSpeed) {
                    maxWindSpeed = reading;
                }
            }
        }
        return maxWindSpeed.windSpeed;
    }

    public static double getMinWindSpeed(Station station) {

        Reading minWindSpeed = null;
        if (station.readings.size() > 0) {
            minWindSpeed = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.windSpeed < minWindSpeed.windSpeed) {
                    minWindSpeed = reading;
                }
            }
        }
        return minWindSpeed.windSpeed;
    }

    public static double getMinPressure(Station station) {

        Reading minPressure = null;
        if (station.readings.size() > 0) {
            minPressure = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.pressure < minPressure.pressure) {
                    minPressure = reading;
                }
            }
        }
        return minPressure.pressure;
    }

    public static double getMaxPressure(Station station) {

        Reading maxPressure = null;
        if (station.readings.size() > 0) {
            maxPressure = station.readings.get(0);
            for (Reading reading : station.readings) {
                if (reading.pressure > maxPressure.pressure) {
                    maxPressure = reading;
                }
            }
        }
        return maxPressure.pressure;
    }
}










