package com.example.dssmv.handler;

import com.example.dssmv_projectdroid_1200968_1221392.dto.*;
import com.example.dssmv_projectdroid_1200968_1221392.model.Book;
import com.example.dssmv_projectdroid_1200968_1221392.model.Library;
import com.example.dssmv_projectdroid_1200968_1221392.model.LibraryBook;
import com.example.dssmv_projectdroid_1200968_1221392.model.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonHandler {
    private static Gson gson = new Gson();



    public static BookDto deserializeJson2BookDto(String json) {
        return gson.fromJson(json, BookDto.class);
    }



    public static List<BookDto> deserializeJson2ListBookDto(String json) {
        Type listType = new TypeToken<List<BookDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }


    public static AuthorDto deserializeJson2AuthorDto(String json) {
        return gson.fromJson(json, AuthorDto.class);
    }


    public static List<AuthorDto> deserializeJson2ListAuthorDto(String json) {
        Type listType = new TypeToken<List<AuthorDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }


    public static LibraryDto deserializeJson2LibraryDto(String json) {
        return gson.fromJson(json, LibraryDto.class);
    }


    public static List<LibraryDto> deserializeJson2ListLibraryDto(String json) {
        Type listType = new TypeToken<List<LibraryDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }


    public static LibraryBookDto deserializeJson2LibraryBookDto(String json) {
        return gson.fromJson(json, LibraryBookDto.class);
    }


    public static List<LibraryBookDto> deserializeJson2ListLibraryBookDto(String json) {
        Type listType = new TypeToken<List<LibraryBookDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }


    public static CheckoutDto deserializeJson2CheckoutDto(String json) {
        return gson.fromJson(json, CheckoutDto.class);
    }


    public static List<CheckoutDto> deserializeJson2ListCheckoutDto(String json) {
        Type listType = new TypeToken<List<CheckoutDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }


    public static ReviewDto deserializeJson2ReviewDto(String json) {
        return gson.fromJson(json, ReviewDto.class);
    }


    public static List<ReviewDto> deserializeJson2ListReviewDto(String json) {
        Type listType = new TypeToken<List<ReviewDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public static CoverUrlsDto deserializeJson2CoverUrlsDto(String json) {
        return gson.fromJson(json, CoverUrlsDto.class);
    }

    public static List<CoverUrlsDto> deserializeJson2ListCoverUrlsDto(String json) {
        Type listType = new TypeToken<List<CoverUrlsDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public static LocalTimeDto deserializeJson2LocalTimeDto(String json) {
        return gson.fromJson(json, LocalTimeDto.class);
    }

    public static List<LocalTimeDto> deserializeJson2ListLocalTimeDto(String json) {
        Type listType = new TypeToken<List<LocalTimeDto>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public static BookDto deSerializeJson2BookDto(String json) {
        return gson.fromJson(json, BookDto.class);
    }

    public static String serializeReviewDto2Json(ReviewDto reviewDto) {
        return gson.toJson(reviewDto);
    }
    public static String serializeLibraryDto2Json(LibraryDto libraryDto) {
        return gson.toJson(libraryDto);
    }
}
