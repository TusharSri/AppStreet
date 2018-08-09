
package com.example.tushar.appstreetdemo.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "totalResults",
    "articles"
})
public class ImageResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("totalResults")
    private Integer totalResults;
    @JsonProperty("articles")
    private List<Article> articles = null;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("totalResults")
    public Integer getTotalResults() {
        return totalResults;
    }

    @JsonProperty("totalResults")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @JsonProperty("articles")
    public List<Article> getArticles() {
        return articles;
    }

    @JsonProperty("articles")
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
