package com.example.dssmv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv.R;
import com.example.dssmv.adapter.BookAdapter;
import com.example.dssmv.model.Book;
import com.example.dssmv.service.RequestsService;
import java.util.List;

public class BookSearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        searchInput = findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.btnSearch).setOnClickListener(v -> searchBooks());
    }

    /* private void searchBooks() {
        String query = searchInput.getText().toString();
        List<Book> books = RequestsService.searchBooks(query, this);
    }*/

    private void searchBooks() {
        String query = searchInput.getText().toString();


        RequestsService.searchBooks(query, this, new RequestsService.BookSearchCallback() {
            @Override
            public void onSuccess(List<Book> books) {
                if (books.isEmpty()) {
                    Toast.makeText(BookSearchActivity.this, "No books found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookSearchActivity.this, "Found " + books.size() + " books", Toast.LENGTH_SHORT).show();
                    setupRecyclerView(books);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BookSearchActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(List<Book> books) {

        for (Book book : books) {

        }
        BookAdapter adapter = new BookAdapter(books, this::openBookDetail);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void openBookDetail(String isbn) {
        /*Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("ISBN", isbn);
        startActivity(intent); */
        if (isbn != null) {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("ISBN", isbn);
            startActivity(intent);
        } else {

        }
    }

}
