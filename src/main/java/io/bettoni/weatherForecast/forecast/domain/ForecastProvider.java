package io.bettoni.weatherForecast.forecast.domain;

import java.util.List;

public interface ForecastProvider {

    List<Forecast> forecastFor(ForecastFilter forecastFilter);
}
