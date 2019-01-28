package io.bettoni.weatherForecast.forecast.api;

import io.bettoni.weatherForecast.forecast.exceptions.InvalidCountryCodeException;
import io.bettoni.weatherForecast.infrastructure.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ForecastControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidCountryCodeException.class})
    protected ResponseEntity<Object> handleInvalidCountryCode(RuntimeException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getCause().getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    protected ResponseEntity<Object> handleRestClientException(HttpClientErrorException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getStatusCode(), ex.getLocalizedMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }
}
