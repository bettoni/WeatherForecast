package io.bettoni.weatherForecast.infrastructure.response;

import io.bettoni.weatherForecast.infrastructure.response.openWeather.ForecastList;
import io.bettoni.weatherForecast.infrastructure.response.openWeather.Main;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ForecastBuilder {

    private double temperature;
    private double pressure;
    private LocalDateTime forecastDate;

    public static ForecastBuilder aForecast() {
        return new ForecastBuilder();
    }

    public ForecastBuilder withTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public ForecastBuilder withPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public ForecastList build() {
        ForecastList forecast = new ForecastList();
        forecast.setDtTxt(forecastDate);

        Main wheather = new Main();
        wheather.setTemp(temperature);
        wheather.setPressure(pressure);

        forecast.setMain(wheather);

        return forecast;
    }

    public ForecastBuilder today() {
        this.forecastDate = LocalDate.now().atStartOfDay();
        return this;
    }
}
