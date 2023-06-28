package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesModel {
    private String name;
    private String alpha3Code;
    private String capital;
    private int population;
    private int status;

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }
}
