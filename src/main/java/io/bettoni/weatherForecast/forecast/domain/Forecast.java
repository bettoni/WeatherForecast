package io.bettoni.weatherForecast.forecast.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return Double.compare(forecast.temperature, temperature) == 0 &&
                Double.compare(forecast.pressure, pressure) == 0 &&
                Objects.equals(date, forecast.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, temperature, pressure);
    }
}
