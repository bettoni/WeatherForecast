package io.bettoni.weatherForecast.forecast.domain;

import io.bettoni.weatherForecast.infrastructure.OpenWeatherConfig;
import io.bettoni.weatherForecast.infrastructure.response.openWeather.ForecastList;
import io.bettoni.weatherForecast.infrastructure.response.openWeather.ForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.LinkedList;
import java.util.List;

@Component
public class ApiForecastProvider implements ForecastProvider {


    private RestOperations restClient;
    private OpenWeatherConfig configs;

    @Autowired
    public ApiForecastProvider(@Qualifier("openWeatherClient") RestOperations restClient, OpenWeatherConfig configs) {
        this.restClient = restClient;
        this.configs = configs;
    }

    @Override
    public List<Forecast> forecastFor(ForecastFilter forecastFilter) {
        StringBuilder url = new StringBuilder();
        url.append(configs.getApiUrl())
                .append(forecastFilter.getCity()).append(",").append(forecastFilter.getCountry())
                .append("&mode=json")
                .append("&appid=").append(configs.getAppId())
                .append("&units=").append(configs.getUnits());

        ResponseEntity<ForecastResponse> response = restClient.getForEntity(url.toString(), ForecastResponse.class);

        if (hasResponseDate(response.getBody()))
            return transformResponseToForecast(response.getBody());

        return new LinkedList<>();
    }

    private boolean hasResponseDate(ForecastResponse body) {
        return body != null && body.getForecastList() != null;
    }

    private List<Forecast> transformResponseToForecast(ForecastResponse responseBody) {
        List<Forecast> finalResult = new LinkedList<>();
        responseBody.getForecastList().forEach(response -> addResponseToFinalResult(response, finalResult));
        return finalResult;
    }

    private void addResponseToFinalResult(ForecastList response, List<Forecast> finalResult) {
        finalResult.add(new Forecast(response.getDtTxt(), response.getMain().getTemp(), response.getMain().getPressure()));
    }
}
