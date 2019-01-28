package io.bettoni.weatherForecast.forecast.domain;

import io.bettoni.weatherForecast.infrastructure.OpenWeatherConfig;
import io.bettoni.weatherForecast.infrastructure.response.openWeather.ForecastList;
import io.bettoni.weatherForecast.infrastructure.response.openWeather.ForecastResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static io.bettoni.weatherForecast.infrastructure.response.ForecastBuilder.aForecast;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class ApiForecastProviderTest {

    private static final ForecastFilter BERLIN_FILTER = new ForecastFilter("DE", "Berlin");
    private static final double TEMPERATURE = 100;
    private static final double PRESSURE = 15;
    private static final ForecastList RESPONSE_FOR_BERLIN = aForecast().today().withTemperature(TEMPERATURE).withPressure(PRESSURE).build();
    private static final LocalDateTime TODAY = LocalDate.now().atStartOfDay();

    private static final Forecast FORECAST_FOR_BERLIN = new Forecast(TODAY, TEMPERATURE, PRESSURE);

    @Mock
    RestOperations restClient;

    @Mock
    OpenWeatherConfig configs;

    ApiForecastProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new ApiForecastProvider(restClient, configs);
    }

    @Test
    public void
    should_call_rest_client_to_get_data() {
        Mockito.when(restClient.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(new ResponseEntity<>(OK));

        provider.forecastFor(BERLIN_FILTER);

        verify(restClient).getForEntity(Mockito.anyString(), Mockito.any());

    }

    @Test
    public void
    should_return_empty_when_empty_response_from_rest_client() {
        Mockito.when(restClient.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(new ResponseEntity<>(OK));

        List<Forecast> result = provider.forecastFor(BERLIN_FILTER);

        assertThat(result).hasSize(0);
    }

    @Test
    public void
    should_convert_api_response_to_business_object() {
        Mockito.when(restClient.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(mockApiResponse());

        List<Forecast> result = provider.forecastFor(BERLIN_FILTER);

        assertThat(result).hasSize(1);
        assertThat(result).containsAnyOf(FORECAST_FOR_BERLIN);
    }

    private ResponseEntity mockApiResponse() {
        ForecastResponse responseBody = new ForecastResponse();
        responseBody.setForecastList(asList(RESPONSE_FOR_BERLIN));

        return new ResponseEntity<>(responseBody, OK);
    }
}