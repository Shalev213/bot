package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumFactsModel {
    private String text;
    private BigInteger number;

    public String getText() {
        return text;
    }

    public BigInteger getNumber() {
        return number;
    }
}
