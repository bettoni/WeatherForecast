package io.bettoni.weatherForecast.forecast;

import io.bettoni.weatherForecast.forecast.domain.ForecastFilter;
import io.bettoni.weatherForecast.forecast.domain.ForecastResult;
import io.bettoni.weatherForecast.forecast.domain.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ForecastController {

    private ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping(value = "/data/{country}/{city}")
    public ForecastResult getForecastFor(@PathVariable String country, @PathVariable String city) {
        return forecastService.getData(createFilterFrom(country, city));
    }

    private ForecastFilter createFilterFrom(String country, String city) {
        return new ForecastFilter(country, city);
    }
}

