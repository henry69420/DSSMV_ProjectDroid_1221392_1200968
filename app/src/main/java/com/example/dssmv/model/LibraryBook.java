package com.example.dssmv.model;

public class LibraryBook {

    private String isbn;
    private int available;
    private int checkedOut;
    private int stock;
    private Book book;
    private Library library;

    public LibraryBook(String isbn, int available, int checkedOut, int stock, Book book, Library library) {
        this.isbn = isbn;
        this.available = available;
        this.checkedOut = checkedOut;
        this.stock = stock;
        this.book = book;
        this.library = library;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(int checkedOut) {
        this.checkedOut = checkedOut;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

}

