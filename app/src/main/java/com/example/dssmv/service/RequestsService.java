package com.example.dssmv.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.dssmv.dtos.BookDto;
import com.example.dssmv.handler.JsonHandler;
import com.example.dssmv.handler.NetworkHandler;
import com.example.dssmv.helper.Utils;
import com.example.dssmv.model.*;
import com.example.dssmv.dtos.*;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import android.os.Handler;
import android.os.Looper;


public class RequestsService {
    public static String lastUrl;


    /*public static Book getBookByIsbn(Context context, String isbn) {
       try {
           String url = Utils.getWSAddress(context) + "book/" + isbn;
           String json = NetworkHandler.getDataInStringFromUrl(url);
           lastUrl = url;
           BookDto bookDto = JsonHandler.deSerializeJson2BookDto(json);
           Book book = Mapper.bookDTO2Book(bookDto);
           return book;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }*/
    public interface BookDetailCallback {
        void onSuccess(Book book);
        void onError(Exception e);
    }

    public interface BookSearchCallback {
        void onSuccess(List<Book> books);
        void onError(Exception e);
    }

    public interface LibraryListCallback {
        void onSuccess(List<Library> libraries);
        void onError(Exception e);
    }

    public interface LibrarySearchCallback {
        void onSuccess(List<Library> libraries);
        void onError(Exception e);
    }


    public interface LibraryDetailCallback {
        void onSuccess(Library library);
        void onError(Exception e);
    }

    public interface CoverCallback {
        void onSuccess(Bitmap bitmap);
        void onError(Exception e);
    }

    public interface LibraryCallback {
        void onSuccess(LibraryDto library);
        void onError(Exception e);
    }

    public interface LibraryDeleteCallback {
        void onSuccess();
        void onError(Exception e);
    }

    public static void getBookByIsbn(Context context, String isbn, BookDetailCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "book/" + isbn;
                String json = NetworkHandler.getDataInStringFromUrl(url);
                lastUrl = url;
                Log.d("API Response", json);
                BookDto bookDto = JsonHandler.deSerializeJson2BookDto(json);
                Book book = Mapper.bookDTO2Book(bookDto);
                handler.post(() -> {
                    if (book != null) {
                        callback.onSuccess(book);
                    } else {
                        callback.onError(new Exception("Book not found"));
                    }
                });
            } catch (Exception e) {
                handler.post(() -> callback.onError(e));
            }
        });
    }


    public static List<Review> getReviewsByIsbn(Activity activity, String isbn, int limit) {
        try {
            String url = Utils.getWSAddress(activity) + "book/" + isbn + "/review?limit=" + limit;
            String json = NetworkHandler.getDataInStringFromUrl(url);
            List<ReviewDto> reviewDtos = JsonHandler.deserializeJson2ListReviewDto(json);
            List<Review> reviews = Mapper.listReviewDTO2ListReview(reviewDtos);
            lastUrl = url;
            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error fetching reviews: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            return null;
        }
    }


    public static void addReview(String isbn, ReviewDto reviewDto, Activity activity) {
        try {
            String url = Utils.getWSAddress(activity) + "book/" + isbn + "/review";
            String json = JsonHandler.serializeReviewDto2Json(reviewDto);
            String result = NetworkHandler.addDataInStringFromUrl(url, json);
            lastUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error adding review: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


    public static void getLibraries(Context context, LibraryListCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "library";
                String json = NetworkHandler.getDataInStringFromUrl(url);
                List<LibraryDto> libraryDtos = JsonHandler.deserializeJson2ListLibraryDto(json);
                List<Library> libraries = Mapper.listLibraryDTO2ListLibrary(libraryDtos);
                lastUrl = url;
                handler.post(() -> {callback.onSuccess(libraries);
                });
            } catch (Exception e) {

                handler.post(() -> {callback.onError(e);
                });
            }
        });
    }


    public static Library getLibraryById(Context context, String id) {
        try {
            String url = Utils.getWSAddress(context) + "library/" + id;
            String json = NetworkHandler.getDataInStringFromUrl(url);
            lastUrl = url;
            LibraryDto libraryDto = JsonHandler.deserializeJson2LibraryDto(json);
            Library library = Mapper.libraryDTO2Library(libraryDto);
            return library;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<LibraryBook> getBooksInLibrary(Activity activity, String libraryId) {
        try {
            String url = Utils.getWSAddress(activity) + "library/" + libraryId + "/book";
            String json = NetworkHandler.getDataInStringFromUrl(url);
            List<LibraryBookDto> LibraryBookDtos = JsonHandler.deserializeJson2ListLibraryBookDto(json);
            List<LibraryBook> libraryBooks = Mapper.listLibraryBookDTO2ListLibraryBook(LibraryBookDtos);
            lastUrl = url;
            return libraryBooks;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error fetching books: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            return null;
        }
    }


    public static void checkoutBook(String libraryId, String bookId, String userId, Activity activity) {
        try {
            String url = Utils.getWSAddress(activity) + "library/" + libraryId + "/book/" + bookId + "/checkout?userId=" + userId;
            String result = NetworkHandler.addDataInStringFromUrl(url, null);
            lastUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error checking out book: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


    public static void checkinBook(String libraryId, String bookId, String userId, Activity activity) {
        try {
            String url = Utils.getWSAddress(activity) + "library/" + libraryId + "/book/" + bookId + "/checkin?userId=" + userId;
            String result = NetworkHandler.addDataInStringFromUrl(url, null);
            lastUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error checking in book: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


    public static void extendCheckout(String checkoutId, Activity activity) {
        try {
            String url = Utils.getWSAddress(activity) + "checkout/" + checkoutId + "/extend";
            String result = NetworkHandler.addDataInStringFromUrl(url, null);  //
            lastUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error extending checkout: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


   /* public static List<Book> searchBooks(String query, Context context) {
        try {
            String url = Utils.getWSAddress(context) + "search?query=" + query; //page=0&
            String json = NetworkHandler.getDataInStringFromUrl(url);
            List<BookDto> bookDtos = JsonHandler.deserializeJson2ListBookDto(json);
            List<Book> books = Mapper.listBookDTO2ListBook(bookDtos);
            lastUrl = url;
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } */

    public static void searchBooks(String query, Context context, BookSearchCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "search?query=" + query;
                String json = NetworkHandler.getDataInStringFromUrl(url);

                List<BookDto> bookDtos = JsonHandler.deserializeJson2ListBookDto(json);
                List<Book> books = Mapper.listBookDTO2ListBook(bookDtos);
                lastUrl = url;


                handler.post(() -> callback.onSuccess(books));
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onError(e));
            }
        });
    }

    /* public static void getCover(String imageUrl, Context context, CoverCallback callback) {
         ExecutorService executor = Executors.newSingleThreadExecutor();
         Handler handler = new Handler(Looper.getMainLooper());

         executor.execute(() -> {
             try {
                 String url = Utils.getWSAddress(context) + "assets/cover/" + imageUrl;
                 String json = NetworkHandler.getDataInStringFromUrl(url);
                 //CoverUrlsDto coverUrlsDto = JsonHandler.deserializeJson2CoverUrlsDto(json);
                 //CoverUrls coverUrls = Mapper.coverUrlsDTO2CoverUrls(coverUrlsDto);

                 handler.post(() -> callback.onSuccess(json));
             } catch (Exception e) {
                 e.printStackTrace();
                 handler.post(() -> callback.onError(e));
             }
         });
     } */
/*   private void sendCreateLibraryRequest(LibraryDto library, Activity activity, LibraryCreationCallback callback) {
       ExecutorService executor = Executors.newSingleThreadExecutor();
       Handler handler = new Handler(Looper.getMainLooper());

       executor.execute(() -> {
           try {
               String url = Utils.getWSAddress(activity) + "library";
               String json = JsonHandler.serializeLibraryDto2Json(library);
               String response = NetworkHandler.addDataInStringFromUrl(url, json);


               activity.runOnUiThread(() -> {
                   callback.onSuccess(response);
                   Toast.makeText(activity, "Library created successfully!", Toast.LENGTH_SHORT).show();
               });
           } catch (Exception e) {
               e.printStackTrace();
               activity.runOnUiThread(() -> {
                   callback.onError(e);
                   Toast.makeText(activity, "Error creating library: " + e.getMessage(), Toast.LENGTH_SHORT).show();
               });
           }
       });
   }
*/
    public static void getCover(String imageUrl, Context context, CoverCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "assets/cover/" + imageUrl;


                byte[] imageBytes = NetworkHandler.getDataInBytesFromUrl(url);
                if (imageBytes != null) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    handler.post(() -> callback.onSuccess(bitmap));
                } else {
                    handler.post(() -> callback.onError(new Exception("Failed to retrieve image.")));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onError(e));
            }
        });
    }

    public static void postLibrary(LibraryDto libraryDto, Context context, LibraryCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "library";
                String jsonBody = JsonHandler.serializeLibraryDto2Json(libraryDto);
                InputStream in = NetworkHandler.openPostHttpConnection(url, jsonBody);
                String jsonResponse = NetworkHandler.readString(in);
                LibraryDto responseLibrary = JsonHandler.deserializeJson2LibraryDto(jsonResponse);

                handler.post(() -> callback.onSuccess(responseLibrary));
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onError(e));
            }
        });
    }



    public static void deleteLibrary(String libraryId, Context context, LibraryDeleteCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "library/" + libraryId;
                InputStream in = NetworkHandler.openDeleteHttpConnection(url);
                if (in != null) {
                    handler.post(() -> callback.onSuccess());
                } else {
                    handler.post(() -> callback.onError(new Exception("Error: InputStream is null")));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onError(e));
            }
        });
    }


    public static void updateLibrary(String libraryId, LibraryDto libraryDto, Context context, LibraryCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Utils.getWSAddress(context) + "library/" + libraryId;
                String requestBody = JsonHandler.serializeLibraryDto2Json(libraryDto);
                InputStream responseStream = NetworkHandler.openPutHttpConnection(url, requestBody);
                String jsonResponse = NetworkHandler.readString(responseStream);

                LibraryDto updatedLibrary = JsonHandler.deserializeJson2LibraryDto(jsonResponse);
                handler.post(() -> callback.onSuccess(updatedLibrary));
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onError(e));
            }
        });
    }
}
