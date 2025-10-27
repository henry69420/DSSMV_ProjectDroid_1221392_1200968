package com.example.dssmv.dtos;

import android.util.Log;
import com.example.dssmv.model.*;
import com.example.dssmv.dtos.*;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static Author authorDTO2Author(AuthorDto obj) throws NullPointerException {
        Author data = new Author(
                obj.getId(),
                obj.getName(),
                obj.getBio(),
                obj.getAlternateNames(),
                obj.getBirthDate(),
                obj.getDeathDate()
        );
        return data;
    }

    public static AuthorDto author2AuthorDTO(Author obj) throws NullPointerException {
        AuthorDto data = new AuthorDto(
                obj.getId(),
                obj.getName(),
                obj.getBio(),
                obj.getAlternateNames(),
                obj.getBirthDate(),
                obj.getDeathDate()
        );
        return data;
    }

    public static List<Author> listAuthorDTO2ListAuthor(List<AuthorDto> list) throws NullPointerException {
        List<Author> data = new ArrayList<>();
        for(AuthorDto obj : list) {
            Author i = authorDTO2Author(obj);

            data.add(i);
        }
        return data;
    }

    public static List<AuthorDto> listAuthor2ListAuthorDTO(List<Author> list) throws NullPointerException {
        List<AuthorDto> data = new ArrayList<>();
        for(Author obj : list) {
            AuthorDto i = author2AuthorDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static Book bookDTO2Book(BookDto obj) throws NullPointerException {


        Book data = new Book(
                obj.getIsbn(),
                obj.getTitle(),
                obj.getByStatement(),
                obj.getDescription(),
                obj.getNumberOfPages(),
                obj.getPublishDate(),
                coverUrlsDTO2CoverUrls(obj.getCover()),
                listAuthorDTO2ListAuthor(obj.getAuthors()),
                obj.getSubjectPeople(),
                obj.getSubjectPlaces(),
                obj.getSubjectTimes(),
                obj.getSubjects()
        );

        return data;
    }

    public static BookDto book2BookDTO(Book obj) throws NullPointerException {
        BookDto data = new BookDto(
                obj.getIsbn(),
                obj.getTitle(),
                obj.getByStatement(),
                obj.getDescription(),
                obj.getNumberOfPages(),
                obj.getPublishDate(),
                coverUrls2CoverUrlsDTO(obj.getCover()),
                listAuthor2ListAuthorDTO(obj.getAuthors()),
                obj.getSubjectPeople(),
                obj.getSubjectPlaces(),
                obj.getSubjectTimes(),
                obj.getSubjects()
        );
        return data;
    }

    public static List<Book> listBookDTO2ListBook(List<BookDto> list) throws NullPointerException {
        List<Book> data = new ArrayList<>();
        for(BookDto obj : list) {
            Book i = bookDTO2Book(obj);
            data.add(i);
        }
        return data;
    }

    public static List<BookDto> listBook2ListBookDTO(List<Book> list) throws NullPointerException {
        List<BookDto> data = new ArrayList<>();
        for(Book obj : list) {
            BookDto i = book2BookDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static CoverUrls coverUrlsDTO2CoverUrls(CoverUrlsDto obj) throws NullPointerException {

        CoverUrls data = new CoverUrls(
                obj.getSmallUrl(),
                obj.getMediumUrl(),
                obj.getLargeUrl()
        );

        return data;
    }

    public static CoverUrlsDto coverUrls2CoverUrlsDTO(CoverUrls obj) throws NullPointerException {
        CoverUrlsDto data = new CoverUrlsDto(
                obj.getSmallUrl(),
                obj.getMediumUrl(),
                obj.getLargeUrl()
        );
        return data;
    }

    public static List<CoverUrls> listCoverUrlsDTO2ListCoverUrls(List<CoverUrlsDto> list) throws NullPointerException {
        List<CoverUrls> data = new ArrayList<>();
        for(CoverUrlsDto obj : list) {
            CoverUrls i = coverUrlsDTO2CoverUrls(obj);
            data.add(i);
        }
        return data;
    }

    public static List<CoverUrlsDto> listCoverUrls2ListCoverUrlsDTO(List<CoverUrls> list) throws NullPointerException {
        List<CoverUrlsDto> data = new ArrayList<>();
        for(CoverUrls obj : list) {
            CoverUrlsDto i = coverUrls2CoverUrlsDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static Library libraryDTO2Library(LibraryDto obj) throws NullPointerException {
        Library data = new Library(
                obj.getId(),
                obj.getName(),
                obj.getAddress(),
                obj.isOpen(),
                obj.getOpenDays(),
                obj.getOpenTime(),
                obj.getCloseTime(),
                obj.getOpenStatement()
        );
        return data;
    }

    public static LibraryDto library2LibraryDTO(Library obj) throws NullPointerException {
        LibraryDto data = new LibraryDto(
                obj.getId(),
                obj.getName(),
                obj.getAddress(),
                obj.isOpen(),
                obj.getOpenDays(),
                obj.getOpenTime(),
                obj.getCloseTime(),
                obj.getOpenStatement()
        );
        return data;
    }

    public static List<Library> listLibraryDTO2ListLibrary(List<LibraryDto> list) throws NullPointerException {
        List<Library> data = new ArrayList<>();
        for(LibraryDto obj : list) {
            Library i = libraryDTO2Library(obj);
            data.add(i);
        }
        return data;
    }

    public static List<LibraryDto> listLibrary2ListLibraryDTO(List<Library> list) throws NullPointerException {
        List<LibraryDto> data = new ArrayList<>();
        for(Library obj : list) {
            LibraryDto i = library2LibraryDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static LocalTime localTimeDTO2LocalTime(LocalTimeDto obj) throws NullPointerException {
        LocalTime data = LocalTime.of(
                obj.getHour(),
                obj.getMinute(),
                obj.getSecond(),
                obj.getNano()
        );
        return data;
    }

    public static LocalTimeDto localTime2LocalTimeDTO(LocalTime obj) throws NullPointerException {
        LocalTimeDto data = new LocalTimeDto(
                obj.getHour(),
                obj.getMinute(),
                obj.getSecond(),
                obj.getNano()
        );
        return data;
    }

    public static List<LocalTime> listLocalTimeDTO2ListLocalTime(List<LocalTimeDto> list) throws NullPointerException {
        List<LocalTime> data = new ArrayList<>();
        for(LocalTimeDto obj : list) {
            LocalTime i = localTimeDTO2LocalTime(obj);
            data.add(i);
        }
        return data;
    }

    public static List<LocalTimeDto> listLocalTime2ListLocalTimeDTO(List<LocalTime> list) throws NullPointerException {
        List<LocalTimeDto> data = new ArrayList<>();
        for(LocalTime obj : list) {
            LocalTimeDto i = localTime2LocalTimeDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static LibraryBook libraryBookDTO2LibraryBook(LibraryBookDto obj) throws NullPointerException {
        LibraryBook data = new LibraryBook(
                obj.getIsbn(),
                obj.getAvailable(),
                obj.getCheckedOut(),
                obj.getStock(),
                bookDTO2Book(obj.getBook()),
                libraryDTO2Library(obj.getLibrary())
        );
        return data;
    }

    public static LibraryBookDto libraryBook2LibraryBookDTO(LibraryBook obj) throws NullPointerException {
        LibraryBookDto data = new LibraryBookDto(
                obj.getIsbn(),
                obj.getAvailable(),
                obj.getCheckedOut(),
                obj.getStock(),
                book2BookDTO(obj.getBook()),
                library2LibraryDTO(obj.getLibrary())
        );
        return data;
    }

    public static List<LibraryBook> listLibraryBookDTO2ListLibraryBook(List<LibraryBookDto> list) throws NullPointerException {
        List<LibraryBook> data = new ArrayList<>();
        for(LibraryBookDto obj : list) {
            LibraryBook i = libraryBookDTO2LibraryBook(obj);
            data.add(i);
        }
        return data;
    }

    public static List<LibraryBookDto> listLibraryBook2ListLibraryBookDTO(List<LibraryBook> list) throws NullPointerException {
        List<LibraryBookDto> data = new ArrayList<>();
        for(LibraryBook obj : list) {
            LibraryBookDto i = libraryBook2LibraryBookDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static Review reviewDTO2Review(ReviewDto obj) throws NullPointerException {
        Review data = new Review(
                obj.getId(),
                obj.getIsbn(),
                obj.getReview(),
                obj.isRecommended(),
                obj.getReviewer(),
                obj.getCreatedDate()
        );
        return data;
    }

    public static ReviewDto review2ReviewDTO(Review obj) throws NullPointerException {
        ReviewDto data = new ReviewDto(
                obj.getId(),
                obj.getIsbn(),
                obj.getReview(),
                obj.isRecommended(),
                obj.getReviewer(),
                obj.getCreatedDate()
        );
        return data;
    }

    public static List<Review> listReviewDTO2ListReview(List<ReviewDto> list) throws NullPointerException {
        List<Review> data = new ArrayList<>();
        for(ReviewDto obj : list) {
            Review i = reviewDTO2Review(obj);
            data.add(i);
        }
        return data;
    }

    public static List<ReviewDto> listReview2ListReviewDTO(List<Review> list) throws NullPointerException {
        List<ReviewDto> data = new ArrayList<>();
        for(Review obj : list) {
            ReviewDto i = review2ReviewDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static Checkout checkoutDTO2Checkout(CheckoutDto obj) throws NullPointerException {
        Checkout data = new Checkout();
        data.setId(obj.getId());
        data.setBook(libraryBookDTO2LibraryBook(obj.getBook()));
        data.setActive(obj.isActive());
        data.setUserId(obj.getUserId());
        data.setCreateTimestamp(obj.getCreateTimestamp());
        data.setDueDate(obj.getDueDate());
        data.setOverdue(obj.isOverdue());
        data.setUpdateTimestamp(obj.getUpdateTimestamp());
        return data;
    }

    public static CheckoutDto checkout2CheckoutDTO(Checkout obj) throws NullPointerException {
        CheckoutDto data = new CheckoutDto();
        data.setId(obj.getId());
        data.setBook(libraryBook2LibraryBookDTO(obj.getBook()));
        data.setActive(obj.isActive());
        data.setUserId(obj.getUserId());
        data.setCreateTimestamp(obj.getCreateTimestamp());
        data.setDueDate(obj.getDueDate());
        data.setOverdue(obj.isOverdue());
        data.setUpdateTimestamp(obj.getUpdateTimestamp());
        return data;
    }

    public static List<Checkout> listCheckoutDTO2ListCheckout(List<CheckoutDto> list) throws NullPointerException {
        List<Checkout> data = new ArrayList<>();
        for(CheckoutDto obj : list) {
            Checkout i = checkoutDTO2Checkout(obj);
            data.add(i);
        }
        return data;
    }

    public static List<CheckoutDto> listCheckout2ListCheckoutDTO(List<Checkout> list) throws NullPointerException {
        List<CheckoutDto> data = new ArrayList<>();
        for(Checkout obj : list) {
            CheckoutDto i = checkout2CheckoutDTO(obj);
            data.add(i);
        }
        return data;
    }

    public static Checkout checkedOutBookDto2Checkout(CheckedOutBookDto obj) throws NullPointerException {
        Checkout data = new Checkout();
        data.setId(obj.getId());
        data.setUserId(obj.getUserId());
        data.setActive(obj.isActive());
        
        // Convert string dates to LocalDateTime
        if (obj.getDueDate() != null) {
            data.setDueDate(LocalDateTime.parse(obj.getDueDate()));
        }
        if (obj.getCreateTimestamp() != null) {
            data.setCreateTimestamp(LocalDateTime.parse(obj.getCreateTimestamp()));
        }
        if (obj.getUpdateTimestamp() != null) {
            data.setUpdateTimestamp(LocalDateTime.parse(obj.getUpdateTimestamp()));
        }
        
        // Create LibraryBook from the CheckedOutBookDto
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setIsbn(obj.getBookId());
        
        // Set the book
        Book book = bookDTO2Book(obj.getBook());
        libraryBook.setBook(book);
        
        // Create library from the DTO fields
        Library library = new Library();
        library.setId(obj.getLibraryId());
        library.setName(obj.getLibraryName());
        library.setAddress(obj.getLibraryAddress());
        libraryBook.setLibrary(library);
        
        data.setBook(libraryBook);
        return data;
    }

    public static List<Checkout> listCheckedOutBookDto2ListCheckout(List<CheckedOutBookDto> list) throws NullPointerException {
        List<Checkout> data = new ArrayList<>();
        for(CheckedOutBookDto obj : list) {
            Checkout i = checkedOutBookDto2Checkout(obj);
            data.add(i);
        }
        return data;
    }
}