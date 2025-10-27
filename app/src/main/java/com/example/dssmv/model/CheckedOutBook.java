package com.example.dssmv.model;

import java.time.LocalDateTime;

public class CheckedOutBook {
    private String id;
    private String libraryId;
    private String libraryName;
    private String userId;
    private LocalDateTime dueDate;
    private Book book;

    public CheckedOutBook() {
    }

    public CheckedOutBook(String id, String libraryId, String libraryName, String userId, LocalDateTime dueDate, Book book) {
        this.id = id;
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.userId = userId;
        this.dueDate = dueDate;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
