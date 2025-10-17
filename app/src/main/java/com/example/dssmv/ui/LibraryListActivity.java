package com.example.dssmv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv.R;
import com.example.dssmv.adapter.LibraryAdapter;
import com.example.dssmv.dtos.LibraryDto;
import com.example.dssmv.model.Library;
import com.example.dssmv.service.RequestsService;

import java.io.Serializable;
import java.util.List;

public class LibraryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Library> libraryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);

        recyclerView = findViewById(R.id.recyclerView);
        loadLibraries();
    }

    private void loadLibraries() {
        RequestsService.getLibraries(this, new LibraryListCallback() {
            @Override
            public void onSuccess(List<Library> libraries) {
                if (libraries.isEmpty()) {
                    Toast.makeText(LibraryListActivity.this, "No libraries found", Toast.LENGTH_SHORT).show();
                } else {
                    libraryList = libraries;
                    setupRecyclerView();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(LibraryListActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void setupRecyclerView(List<Library> libraries) {
        LibraryAdapter adapter = new LibraryAdapter(libraries);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/
    private void setupRecyclerView() {
        LibraryAdapter  adapter = new LibraryAdapter(libraryList, new LibraryAdapter.LibraryClickListener() {

          /*  public void onUpdateClick(String libraryId) {
                onUpdateLibrary(libraryId);
            }

            @Override
            public void onDeleteClick(String libraryId) {
                onDeleteLibrary(libraryId);
            }*/
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
/*
    public void onUpdateLibrary(String libraryId) {
        Intent intent = new Intent(this, UpdateLibraryActivity.class);
        intent.putExtra("libraryId", libraryId);
        startActivity(intent);
    }
    public void onDeleteLibrary(String libraryId) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Library")
                .setMessage("Are you sure you want to delete this library?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    RequestsService.deleteLibrary(libraryId, this, new RequestsService.LibraryDeleteCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LibraryListActivity.this, "Library deleted", Toast.LENGTH_SHORT).show();
                            loadLibraries();
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(LibraryListActivity.this, "Error deleting library", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }*/
}
