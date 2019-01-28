package io.bettoni.weatherForecast.infrastructure;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleValidatorTest {

    private static final String GERMANY = "DE";
    private static final String NOT_A_COUNTRY = "1Q";

    @Test public void
    should_evaluate_valid_code() {
        assertThat(new LocaleValidator().isValidCode(GERMANY)).isTrue();
    }

    @Test public void
    should_evaluate_invalid_code() {
        assertThat(new LocaleValidator().isValidCode(NOT_A_COUNTRY)).isFalse();
    }

}