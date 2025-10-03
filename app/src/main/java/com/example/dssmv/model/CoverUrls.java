package com.example.dssmv.model;

public class CoverUrls {

    private String largeUrl;
    private String mediumUrl;
    private String smallUrl;

    public CoverUrls(String smallUrl, String mediumUrl, String largeUrl) {
        this.smallUrl = smallUrl;
        this.mediumUrl = mediumUrl;
        this.largeUrl = largeUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
