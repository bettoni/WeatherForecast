package io.bettoni.weatherForecast.forecast.api;

import io.bettoni.weatherForecast.forecast.domain.ForecastFilter;
import io.bettoni.weatherForecast.forecast.domain.ForecastResult;
import io.bettoni.weatherForecast.forecast.domain.ForecastService;
import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastController {

    private ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @RequestMapping("/data/{country}/{city}")
    public ForecastResult getForecastFor(@PathVariable String country, @PathVariable String city) {
        try {
            return forecastService.getData(createFilterFrom(country, city));
        } catch (InvalidCountryCodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ForecastFilter createFilterFrom(String country, String city) {
        return new ForecastFilter(country, city);
    }
}


