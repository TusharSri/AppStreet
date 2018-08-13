
package com.example.tushar.appstreetdemo.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_type",
    "instrumentation",
    "readLink",
    "webSearchUrl",
    "totalEstimatedMatches",
    "nextOffset",
    "value"
})
public class ImageResponse {

    @JsonProperty("_type")
    private String type;
    @JsonProperty("instrumentation")
    private Instrumentation instrumentation;
    @JsonProperty("readLink")
    private String readLink;
    @JsonProperty("webSearchUrl")
    private String webSearchUrl;
    @JsonProperty("totalEstimatedMatches")
    private Integer totalEstimatedMatches;
    @JsonProperty("nextOffset")
    private Integer nextOffset;
    @JsonProperty("value")
    private List<Value> value = null;

    @JsonProperty("_type")
    public String getType() {
        return type;
    }

    @JsonProperty("_type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("instrumentation")
    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    @JsonProperty("instrumentation")
    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @JsonProperty("readLink")
    public String getReadLink() {
        return readLink;
    }

    @JsonProperty("readLink")
    public void setReadLink(String readLink) {
        this.readLink = readLink;
    }

    @JsonProperty("webSearchUrl")
    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    @JsonProperty("webSearchUrl")
    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    @JsonProperty("totalEstimatedMatches")
    public Integer getTotalEstimatedMatches() {
        return totalEstimatedMatches;
    }

    @JsonProperty("totalEstimatedMatches")
    public void setTotalEstimatedMatches(Integer totalEstimatedMatches) {
        this.totalEstimatedMatches = totalEstimatedMatches;
    }

    @JsonProperty("nextOffset")
    public Integer getNextOffset() {
        return nextOffset;
    }

    @JsonProperty("nextOffset")
    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    @JsonProperty("value")
    public List<Value> getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(List<Value> value) {
        this.value = value;
    }

}
