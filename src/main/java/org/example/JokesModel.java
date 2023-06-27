package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JokesModel {
private String setup;
private String punchline;

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }
}
