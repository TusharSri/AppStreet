
package com.example.tushar.appstreetdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "webSearchUrl",
    "name",
    "thumbnailUrl",
    "datePublished",
    "contentUrl",
    "hostPageUrl",
    "contentSize",
    "encodingFormat",
    "hostPageDisplayUrl",
    "width",
    "height",
    "thumbnail",
    "imageInsightsToken",
    "insightsMetadata",
    "imageId",
    "accentColor"
})
public class Value {

    @JsonProperty("webSearchUrl")
    private String webSearchUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;
    @JsonProperty("datePublished")
    private String datePublished;
    @JsonProperty("contentUrl")
    private String contentUrl;
    @JsonProperty("hostPageUrl")
    private String hostPageUrl;
    @JsonProperty("contentSize")
    private String contentSize;
    @JsonProperty("encodingFormat")
    private String encodingFormat;
    @JsonProperty("hostPageDisplayUrl")
    private String hostPageDisplayUrl;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("thumbnail")
    private Thumbnail thumbnail;
    @JsonProperty("imageInsightsToken")
    private String imageInsightsToken;
    @JsonProperty("insightsMetadata")
    private InsightsMetadata insightsMetadata;
    @JsonProperty("imageId")
    private String imageId;
    @JsonProperty("accentColor")
    private String accentColor;

    @JsonProperty("webSearchUrl")
    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    @JsonProperty("webSearchUrl")
    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("thumbnailUrl")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @JsonProperty("thumbnailUrl")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @JsonProperty("datePublished")
    public String getDatePublished() {
        return datePublished;
    }

    @JsonProperty("datePublished")
    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    @JsonProperty("contentUrl")
    public String getContentUrl() {
        return contentUrl;
    }

    @JsonProperty("contentUrl")
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @JsonProperty("hostPageUrl")
    public String getHostPageUrl() {
        return hostPageUrl;
    }

    @JsonProperty("hostPageUrl")
    public void setHostPageUrl(String hostPageUrl) {
        this.hostPageUrl = hostPageUrl;
    }

    @JsonProperty("contentSize")
    public String getContentSize() {
        return contentSize;
    }

    @JsonProperty("contentSize")
    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    @JsonProperty("encodingFormat")
    public String getEncodingFormat() {
        return encodingFormat;
    }

    @JsonProperty("encodingFormat")
    public void setEncodingFormat(String encodingFormat) {
        this.encodingFormat = encodingFormat;
    }

    @JsonProperty("hostPageDisplayUrl")
    public String getHostPageDisplayUrl() {
        return hostPageDisplayUrl;
    }

    @JsonProperty("hostPageDisplayUrl")
    public void setHostPageDisplayUrl(String hostPageDisplayUrl) {
        this.hostPageDisplayUrl = hostPageDisplayUrl;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("thumbnail")
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @JsonProperty("thumbnail")
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("imageInsightsToken")
    public String getImageInsightsToken() {
        return imageInsightsToken;
    }

    @JsonProperty("imageInsightsToken")
    public void setImageInsightsToken(String imageInsightsToken) {
        this.imageInsightsToken = imageInsightsToken;
    }

    @JsonProperty("insightsMetadata")
    public InsightsMetadata getInsightsMetadata() {
        return insightsMetadata;
    }

    @JsonProperty("insightsMetadata")
    public void setInsightsMetadata(InsightsMetadata insightsMetadata) {
        this.insightsMetadata = insightsMetadata;
    }

    @JsonProperty("imageId")
    public String getImageId() {
        return imageId;
    }

    @JsonProperty("imageId")
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @JsonProperty("accentColor")
    public String getAccentColor() {
        return accentColor;
    }

    @JsonProperty("accentColor")
    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

}
