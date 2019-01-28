package io.bettoni.weatherForecast.infrastructure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.util.Arrays.asList;

public class LocaleValidator {

    private static final Set<String> ISO_COUNTRIES = new HashSet<String>(asList(Locale.getISOCountries()));

    public boolean isValidCode(String country) {
        return ISO_COUNTRIES.contains(country);
    }
}
