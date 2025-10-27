package com.example.dssmv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dssmv.ui.BookSearchActivity;
import com.example.dssmv.ui.LibraryListActivity;
import com.example.dssmv.ui.CheckedOutBooksActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button librariesButton = findViewById(R.id.btnLibraries);
        Button booksButton = findViewById(R.id.btnSearchBooks);
        Button checkedOutBooksButton = findViewById(R.id.btnCheckedOutBooks);
        
        if (librariesButton != null) {
            librariesButton.setOnClickListener(v -> openLibraries());
        }
        if (booksButton != null) {
            booksButton.setOnClickListener(v -> openBookSearch());
        }
        if (checkedOutBooksButton != null) {
            checkedOutBooksButton.setOnClickListener(v -> openCheckedOutBooks());
        }

    }

    private void openLibraries() {
        Intent intent = new Intent(this, LibraryListActivity.class);
        startActivity(intent);
    }
    private void openBookSearch() {
        Intent intent = new Intent(this, BookSearchActivity.class);
        startActivity(intent);
    }
    
    private void openCheckedOutBooks() {
        Intent intent = new Intent(this, CheckedOutBooksActivity.class);
        startActivity(intent);
    }
}
