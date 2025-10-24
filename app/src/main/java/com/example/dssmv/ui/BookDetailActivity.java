package com.example.dssmv.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dssmv.R;
import com.example.dssmv.model.Author;
import com.example.dssmv.model.Book;
import com.example.dssmv.service.RequestsService;

import java.util.List;
import java.util.stream.Collectors;

public class BookDetailActivity extends AppCompatActivity {
    private TextView title, description, authors, publishDate;
    private String isbn;
    private ImageView bookCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        authors = findViewById(R.id.authors);
        publishDate = findViewById(R.id.publishDate);
        bookCover = findViewById(R.id.bookCover);

        isbn = getIntent().getStringExtra("ISBN");
        if (isbn != null) {
            loadBookDetails();
        } else {
            Log.e("BookDetailActivity", "ISBN is null");
        }
    }

    private void loadBookDetails() {
        RequestsService.getBookByIsbn(this, isbn, new RequestsService.BookDetailCallback() {
            @Override
            public void onSuccess(Book book) {
                title.setText(book.getTitle());
                description.setText(book.getDescription());

                List<Author> authorList = book.getAuthors();
                String authorNames = authorList.stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(", "));
                authors.setText(authorNames);
                publishDate.setText(book.getPublishDate());

                if (book.getCover() != null && book.getCover().getMediumUrl() != null) {
                    String imageUrl = book.getCover().getMediumUrl();
                    if (imageUrl != null && imageUrl.startsWith("/api/v1/assets/cover/")) {
                        imageUrl = imageUrl.replace("/api/v1/assets/cover/", "");
                    }
                    RequestsService.getCover(imageUrl, BookDetailActivity.this, new RequestsService.CoverCallback() {
                        @Override
                        public void onSuccess(Bitmap coverImage) {
                            Glide.with(BookDetailActivity.this)
                                    .load(coverImage)
                                    .placeholder(R.drawable.book_placeholder)
                                    .error(R.drawable.book_error)
                                    .centerCrop()
                                    .into(bookCover);
                        }

                        @Override
                        public void onError(Exception e) {
                            bookCover.setImageResource(R.drawable.book_placeholder);
                            e.printStackTrace();
                        }
                    });
                } else {
                    Log.e("ImageLoadError", "Error loading image");
                    bookCover.setImageResource(R.drawable.book_placeholder);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BookDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
