package io.bettoni.weatherForecast.forecast.domain;

import java.util.Objects;

public class ForecastFilter {

    private final String country;
    private final String city;

    public ForecastFilter(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastFilter that = (ForecastFilter) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city);
    }
}
