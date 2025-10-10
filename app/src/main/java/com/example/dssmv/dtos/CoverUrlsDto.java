package com.example.dssmv.dtos;

public class CoverUrlsDto {
    private String smallUrl;
    private String mediumUrl;
    private String largeUrl;

    public CoverUrlsDto(String smallUrl, String mediumUrl, String largeUrl) {
        this.smallUrl = smallUrl;
        this.mediumUrl = mediumUrl;
        this.largeUrl = largeUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }
}