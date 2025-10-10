package com.example.dssmv.dtos;

public class LibraryBookDto {
    private String isbn;
    private int available;
    private int checkedOut;
    private int stock;
    private BookDto book;
    private LibraryDto library;

    public LibraryBookDto(String isbn, int available, int checkedOut, int stock, BookDto book, LibraryDto library) {
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

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LibraryDto getLibrary() {
        return library;
    }

    public void setLibrary(LibraryDto library) {
        this.library = library;
    }
}
