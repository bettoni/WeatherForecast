package io.bettoni.weatherForecast.infrastructure;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
public class OpenWeatherConfig {

    @Value("${open-weather.api-url}")
    private String apiUrl;

    @Value("${open-weather.app-id}")
    private String appId;

    @Value("${open-weather.units}")
    private String units;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getUnits() {
        return units;
    }

    @Bean
    @Qualifier("openWeatherClient")
    public RestTemplate webClient(RestTemplateBuilder builder) {
        return builder.errorHandler(new DefaultResponseErrorHandler()).build();
    }
}
