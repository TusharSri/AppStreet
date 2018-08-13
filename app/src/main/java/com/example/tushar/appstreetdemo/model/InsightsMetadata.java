
package com.example.tushar.appstreetdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "recipeSourcesCount",
    "bestRepresentativeQuery",
    "pagesIncludingCount",
    "availableSizesCount"
})
public class InsightsMetadata {

    @JsonProperty("recipeSourcesCount")
    private Integer recipeSourcesCount;
    @JsonProperty("bestRepresentativeQuery")
    private BestRepresentativeQuery bestRepresentativeQuery;
    @JsonProperty("pagesIncludingCount")
    private Integer pagesIncludingCount;
    @JsonProperty("availableSizesCount")
    private Integer availableSizesCount;

    @JsonProperty("recipeSourcesCount")
    public Integer getRecipeSourcesCount() {
        return recipeSourcesCount;
    }

    @JsonProperty("recipeSourcesCount")
    public void setRecipeSourcesCount(Integer recipeSourcesCount) {
        this.recipeSourcesCount = recipeSourcesCount;
    }

    @JsonProperty("bestRepresentativeQuery")
    public BestRepresentativeQuery getBestRepresentativeQuery() {
        return bestRepresentativeQuery;
    }

    @JsonProperty("bestRepresentativeQuery")
    public void setBestRepresentativeQuery(BestRepresentativeQuery bestRepresentativeQuery) {
        this.bestRepresentativeQuery = bestRepresentativeQuery;
    }

    @JsonProperty("pagesIncludingCount")
    public Integer getPagesIncludingCount() {
        return pagesIncludingCount;
    }

    @JsonProperty("pagesIncludingCount")
    public void setPagesIncludingCount(Integer pagesIncludingCount) {
        this.pagesIncludingCount = pagesIncludingCount;
    }

    @JsonProperty("availableSizesCount")
    public Integer getAvailableSizesCount() {
        return availableSizesCount;
    }

    @JsonProperty("availableSizesCount")
    public void setAvailableSizesCount(Integer availableSizesCount) {
        this.availableSizesCount = availableSizesCount;
    }

}
