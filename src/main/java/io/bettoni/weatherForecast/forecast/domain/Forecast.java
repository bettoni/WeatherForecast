package io.bettoni.weatherForecast.forecast.domain;

import java.time.LocalDateTime;

public class Forecast {

    private LocalDateTime date;
    private double temperature;
    private double pressure;

    public Forecast(LocalDateTime date, double temperature, double pressure) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

}
