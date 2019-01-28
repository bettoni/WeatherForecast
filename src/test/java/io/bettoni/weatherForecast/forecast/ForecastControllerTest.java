package io.bettoni.weatherForecast.forecast;

import io.bettoni.weatherForecast.forecast.api.ForecastController;
import io.bettoni.weatherForecast.forecast.domain.ForecastFilter;
import io.bettoni.weatherForecast.forecast.domain.ForecastResult;
import io.bettoni.weatherForecast.forecast.domain.ForecastService;
import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForecastControllerTest {

    private static final String GERMANY = "DE";
    private static final String BERLIN = "berlin";
    private static final ForecastFilter FILTER_FOR_BERLIN = new ForecastFilter(GERMANY, BERLIN);
    private static final ForecastResult FORECAST_FOR_BERLIN = new ForecastResult(100,100,15);

    @Mock
    ForecastService forecastService;

    @Test public void
    should_get_forecast_by_city() throws InvalidCountryCodeException {
        ForecastController controller = new ForecastController(forecastService);

        when(forecastService.getData(FILTER_FOR_BERLIN)).thenReturn(FORECAST_FOR_BERLIN);

        ForecastResult result = controller.getForecastFor(GERMANY, BERLIN);
        assertThat(result).isEqualTo(FORECAST_FOR_BERLIN);
    }
}