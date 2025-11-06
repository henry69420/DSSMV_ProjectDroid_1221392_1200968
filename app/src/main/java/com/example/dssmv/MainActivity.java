package com.example.dssmv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dssmv.ui.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button librariesButton = findViewById(R.id.btnLibraries);
        Button booksButton = findViewById(R.id.btnSearchBooks);
        Button checkedOutBooksButton = findViewById(R.id.btnCheckedOutBooks);
        Button addLibraryButton = findViewById(R.id.btnAddLibrary);
        Button libraryLocationsButton = findViewById(R.id.btnLibraryLocations);

        if (librariesButton != null) {
            librariesButton.setOnClickListener(v -> openLibraries());
        }
        if (booksButton != null) {
            booksButton.setOnClickListener(v -> openBookSearch());
        }
        if (checkedOutBooksButton != null) {
            checkedOutBooksButton.setOnClickListener(v -> openCheckedOutBooks());
        }
        if (addLibraryButton != null) {
            addLibraryButton.setOnClickListener(v -> openAddLibraryButton());
        }
        if (libraryLocationsButton != null) {
            libraryLocationsButton.setOnClickListener(v -> openLibraryLocations());
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
    private void openAddLibraryButton() {
        Intent intent = new Intent(this, CreateLibraryActivity.class);
        startActivity(intent);
    }
    private void openLibraryLocations() {
        Intent intent = new Intent(this, LibraryMapActivity.class);
        startActivity(intent);
    }
}
