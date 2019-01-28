package io.bettoni.weatherForecast.forecast.domain;

import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForecastServiceTest {

    private static final String INVALID_COUNTRY = "11";
    private static final String GERMANY = "DE";
    private static final String BERLIN = "Berlin";

    private static final ForecastFilter FILTER_WITH_INVALID_COUNTRY = new ForecastFilter(INVALID_COUNTRY, BERLIN);
    private static final ForecastFilter BERLIN_FILTER = new ForecastFilter(GERMANY, BERLIN);
    private static final ForecastResult RESULT_FOR_BERLIN = new ForecastResult(50,55,25);

    @Mock
    ForecastProvider provider;

    @Test(expected = InvalidCountryCodeException.class)
    public void should_throw_exception_when_country_code_is_invalid() throws InvalidCountryCodeException {
        ForecastService service = new ForecastService(provider);
        service.getData(FILTER_WITH_INVALID_COUNTRY);
    }

    @Test public void
    should_get_a_forecast_for_valid_city() throws InvalidCountryCodeException {
        when(provider.forecastFor(BERLIN_FILTER)).thenReturn(aForecastList());

        ForecastResult result = new ForecastService(provider).getData(BERLIN_FILTER);

        assertThat(result.getAverageDailyTemperature()).isEqualTo(RESULT_FOR_BERLIN.getAverageDailyTemperature());
        assertThat(result.getAverageNightlyTemperature()).isEqualTo(RESULT_FOR_BERLIN.getAverageNightlyTemperature());
        assertThat(result.getAveragePressure()).isEqualTo(RESULT_FOR_BERLIN.getAveragePressure());
    }

    private List<Forecast> aForecastList() {
        LocalDateTime now = LocalDateTime.now();
        List<Forecast> forecastList = new ArrayList<>();
        forecastList.add(new Forecast(now, 30, 15));
        forecastList.add(new Forecast(now.plusDays(1).withHour(17), 40, 20));
        forecastList.add(new Forecast(now.plusDays(1).withHour(20), 45, 20));
        forecastList.add(new Forecast(now.plusDays(2).withHour(17), 50, 25));
        forecastList.add(new Forecast(now.plusDays(2).withHour(20), 55, 25));
        forecastList.add(new Forecast(now.plusDays(3).withHour(17), 60, 30));
        forecastList.add(new Forecast(now.plusDays(3).withHour(20), 65, 30));
        forecastList.add(new Forecast(now.plusDays(4).withHour(17), 70, 35));
        forecastList.add(new Forecast(now.plusDays(4).withHour(20), 75, 35));

        return forecastList;
    }
}