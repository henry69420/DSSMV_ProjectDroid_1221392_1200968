package com.example.dssmv.dtos;

public class ReviewDto {
    private String id;
    private String isbn;
    private String review;
    private boolean recommended;
    private String reviewer;
    private String createdDate;

    public ReviewDto(String id, String isbn, String review, boolean recommended, String reviewer, String createdDate) {
        this.id = id;
        this.isbn = isbn;
        this.review = review;
        this.recommended = recommended;
        this.reviewer = reviewer;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
