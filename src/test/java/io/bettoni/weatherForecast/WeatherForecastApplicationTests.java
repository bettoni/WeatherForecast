package io.bettoni.weatherForecast;

import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeatherForecastApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_load_servlet_context() {
        ServletContext servletContext = wac.getServletContext();

        assertThat(servletContext).isNotNull();
        assertThat(servletContext instanceof MockServletContext).isTrue();
        assertThat(wac.getBean("forecastController")).isNotNull();
    }

    @Test
    public void should_get_data_from_third_party_service() throws Exception {
        this.mockMvc.perform(get("/data/de/berlin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageDailyTemperature").exists())
                .andExpect(jsonPath("$.averageNightlyTemperature").exists())
                .andExpect(jsonPath("$.averagePressure").exists())
                .andReturn();
    }

    @Test
    public void should_validate_invalid_country_code() throws Exception {
        this.mockMvc.perform(get("/data/d2/berlin"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.message").value(InvalidCountryCodeException.EXCEPTION_MESSAGE))
                .andReturn();
    }

    @Test
    public void should_evaluate_http_client_exception() throws Exception {
        this.mockMvc.perform(get("/data/de/berlewein"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.message").value("404 Not Found"))
                .andReturn();
    }
}

