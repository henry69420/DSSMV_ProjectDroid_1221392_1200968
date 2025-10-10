package com.example.dssmv.dtos;

import java.util.List;

public class BookDto {
    private String isbn;
    private String title;
    private String byStatement;
    private String description;
    private int numberOfPages;
    private String publishDate;
    private CoverUrlsDto cover;  // DTO for CoverUrls
    private List<AuthorDto> authors;
    private List<String> subjectPeople;
    private List<String> subjectPlaces;
    private List<String> subjectTimes;
    private List<String> subjects;

    public BookDto(String isbn, String title, String byStatement, String description, int numberOfPages, String publishDate, CoverUrlsDto cover, List<AuthorDto> authors, List<String> subjectPeople, List<String> subjectPlaces, List<String> subjectTimes, List<String> subjects) {
        this.isbn = isbn;
        this.title = title;
        this.byStatement = byStatement;
        this.description = description;
        this.numberOfPages = numberOfPages;
        this.publishDate = publishDate;
        this.cover = cover;
        this.authors = authors;
        this.subjectPeople = subjectPeople;
        this.subjectPlaces = subjectPlaces;
        this.subjectTimes = subjectTimes;
        this.subjects = subjects;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByStatement() {
        return byStatement;
    }

    public void setByStatement(String byStatement) {
        this.byStatement = byStatement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public CoverUrlsDto getCover() {
        return cover;
    }

    public void setCover(CoverUrlsDto cover) {
        this.cover = cover;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public List<String> getSubjectPeople() {
        return subjectPeople;
    }

    public void setSubjectPeople(List<String> subjectPeople) {
        this.subjectPeople = subjectPeople;
    }

    public List<String> getSubjectPlaces() {
        return subjectPlaces;
    }

    public void setSubjectPlaces(List<String> subjectPlaces) {
        this.subjectPlaces = subjectPlaces;
    }

    public List<String> getSubjectTimes() {
        return subjectTimes;
    }

    public void setSubjectTimes(List<String> subjectTimes) {
        this.subjectTimes = subjectTimes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
