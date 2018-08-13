
package com.example.tushar.appstreetdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "displayText",
    "webSearchUrl"
})
public class BestRepresentativeQuery {

    @JsonProperty("text")
    private String text;
    @JsonProperty("displayText")
    private String displayText;
    @JsonProperty("webSearchUrl")
    private String webSearchUrl;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("displayText")
    public String getDisplayText() {
        return displayText;
    }

    @JsonProperty("displayText")
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    @JsonProperty("webSearchUrl")
    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    @JsonProperty("webSearchUrl")
    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

}
