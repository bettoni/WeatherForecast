package io.bettoni.weatherForecast.forecast.domain;

import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import io.bettoni.weatherForecast.infrastructure.LocaleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDate.now;

@Service
public class ForecastService {

    private static final long FORECAST_DAYS = 3;
    private ForecastProvider provider;

    @Autowired
    public ForecastService(ForecastProvider provider) {
        this.provider = provider;
    }

    public ForecastResult getData(ForecastFilter filter) throws InvalidCountryCodeException {
        if (!validCountryCode(filter.getCountry()))
            throw new InvalidCountryCodeException();

        List<Forecast> forecast = provider.forecastFor(filter);

        return createResultFrom(forecast);
    }

    private ForecastResult createResultFrom(List<Forecast> forecasts) {
        double averageDailyTemperature = getDailyTemperature(forecasts);
        double averageNightlyTemperature = getNightlyTemperature(forecasts);
        double averagePressure = getPressure(forecasts);

        return new ForecastResult(averageDailyTemperature, averageNightlyTemperature, averagePressure);
    }

    private double getPressure(List<Forecast> forecasts) {
        return forecasts.stream().
                filter(p -> isValidPeriod(p.getDate())).
                mapToDouble(p -> p.getPressure()).
                summaryStatistics().getAverage();
    }

    private double getNightlyTemperature(List<Forecast> forecasts) {
        return forecasts.stream().
                filter(p -> isValidPeriod(p.getDate())).
                filter(p -> !isDaytime(p.getDate())).
                mapToDouble(p -> p.getTemperature()).
                summaryStatistics().getAverage();
    }

    private double getDailyTemperature(List<Forecast> forecasts) {
        return forecasts.stream().
                filter(p -> isValidPeriod(p.getDate())).
                filter(p -> isDaytime(p.getDate())).
                mapToDouble(p -> p.getTemperature()).
                summaryStatistics().getAverage();
    }

    private boolean isValidPeriod(LocalDateTime date) {
        LocalDate limit = now().plusDays(FORECAST_DAYS);
        return (date.toLocalDate().isBefore(limit) || date.toLocalDate().isEqual(limit)) && date.toLocalDate().isAfter(now());
    }

    private boolean isDaytime(LocalDateTime date) {
        return date.getHour() >= 6 && date.getHour() <= 18;
    }

    private boolean validCountryCode(String country) {
        return new LocaleValidator().isValidCode(country);
    }

}
