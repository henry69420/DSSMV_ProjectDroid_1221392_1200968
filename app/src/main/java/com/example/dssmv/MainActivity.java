package com.example.dssmv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dssmv.ui.LibraryListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button librariesButton = findViewById(R.id.btnLibraries);
        if (librariesButton != null) {
            librariesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLibraries();
                }
            });
        }
    }

    private void openLibraries() {
        Intent intent = new Intent(this, LibraryListActivity.class);
        startActivity(intent);
    }
}
