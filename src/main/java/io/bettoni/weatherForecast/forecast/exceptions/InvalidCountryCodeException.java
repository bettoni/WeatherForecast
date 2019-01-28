package io.bettoni.weatherForecast.forecast.exceptions;

public class InvalidCountryCodeException extends Throwable {

    public static final String EXCEPTION_MESSAGE = "Invalid country code. You should use in the ISO 3166 format";

    public InvalidCountryCodeException() {
        super(EXCEPTION_MESSAGE);
    }
}
