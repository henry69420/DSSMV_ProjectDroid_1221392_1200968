package com.example.dssmv.model;

import java.time.LocalDateTime;

public class Checkout {
    private String id;
    private LibraryBook book;
    private boolean active;
    private String userId;
    private LocalDateTime createTimestamp;
    private LocalDateTime dueDate;
    private boolean overdue;
    private LocalDateTime updateTimestamp;

    public Checkout(String id, LibraryBook book, boolean active, String userId, LocalDateTime createTimestamp, LocalDateTime dueDate, boolean overdue, LocalDateTime updateTimestamp) {
        this.id = id;
        this.book = book;
        this.active = active;
        this.userId = userId;
        this.createTimestamp = createTimestamp;
        this.dueDate = dueDate;
        this.overdue = overdue;
        this.updateTimestamp = updateTimestamp;
    }

    public Checkout() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(LocalDateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
