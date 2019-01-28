package io.bettoni.weatherForecast.forecast.domain;

import java.util.Objects;

public class ForecastResult {


    private final double averageDailyTemperature;
    private final double averageNightlyTemperature;
    private final double averagePressure;

    public ForecastResult(double averageDailyTemperature, double averageNightlyTemperature, double averagePressure) {
        this.averageDailyTemperature = averageDailyTemperature;
        this.averageNightlyTemperature = averageNightlyTemperature;
        this.averagePressure = averagePressure;
    }

    public double getAverageDailyTemperature() {
        return averageDailyTemperature;
    }

    public double getAverageNightlyTemperature() {
        return averageNightlyTemperature;
    }

    public double getAveragePressure() {
        return averagePressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastResult that = (ForecastResult) o;
        return Double.compare(that.averageDailyTemperature, averageDailyTemperature) == 0 &&
                Double.compare(that.averageNightlyTemperature, averageNightlyTemperature) == 0 &&
                Double.compare(that.averagePressure, averagePressure) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(averageDailyTemperature, averageNightlyTemperature, averagePressure);
    }
}
