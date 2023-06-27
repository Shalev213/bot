package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesModel {
    private String content;
    private String author;

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
