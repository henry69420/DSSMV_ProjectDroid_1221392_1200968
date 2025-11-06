package com.example.dssmv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv.R;
import com.example.dssmv.dtos.LibraryDto;
import com.example.dssmv.service.RequestsService;

public class UpdateLibraryActivity extends AppCompatActivity {
    private EditText editTextName, editTextAddress, editTextOpenDays;
    private Button btnUpdate;
    private TimePicker editOpenTimePicker, editCloseTimePicker;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_update);

        Intent intent = getIntent();
        libraryId = getIntent().getStringExtra("libraryId");

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextOpenDays = findViewById(R.id.editTextOpenDays);
        editOpenTimePicker = findViewById(R.id.editOpenTimePicker);
        editCloseTimePicker = findViewById(R.id.editCloseTimePicker);
        btnUpdate = findViewById(R.id.btnUpdate);
        editCloseTimePicker.setIs24HourView(true);
        editOpenTimePicker.setIs24HourView(true);

        String currentName = intent.getStringExtra("libraryName");
        String currentAddress = intent.getStringExtra("libraryAddress");
        String currentOpenDays = intent.getStringExtra("libraryOpenDays");
        String currentOpenTime = intent.getStringExtra("libraryOpenTime");
        String currentCloseTime = intent.getStringExtra("libraryCloseTime");


        if (currentName != null) editTextName.setText(currentName);
        if (currentAddress != null) editTextAddress.setText(currentAddress);
        if (currentOpenDays != null) editTextOpenDays.setText(currentOpenDays);


        if (currentOpenTime != null && currentOpenTime.contains(":")) {
            String[] parts = currentOpenTime.split(":");
            editOpenTimePicker.setHour(Integer.parseInt(parts[0]));
            editOpenTimePicker.setMinute(Integer.parseInt(parts[1]));
        }

        if (currentCloseTime != null && currentCloseTime.contains(":")) {
            String[] parts = currentCloseTime.split(":");
            editCloseTimePicker.setHour(Integer.parseInt(parts[0]));
            editCloseTimePicker.setMinute(Integer.parseInt(parts[1]));
        }

        btnUpdate.setOnClickListener(v -> updateLibrary());
    }

    private void updateLibrary() {
        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();
        String openDays = editTextOpenDays.getText().toString();
        int openHour = editOpenTimePicker.getHour();
        int openMinute = editOpenTimePicker.getMinute();
        String openTime = String.format("%02d:%02d", openHour, openMinute);

        int closeHour = editCloseTimePicker.getHour();
        int closeMinute = editCloseTimePicker.getMinute();
        String closeTime = String.format("%02d:%02d", closeHour, closeMinute);

        LibraryDto libraryDto = new LibraryDto(name,address, openDays, openTime, closeTime);
        RequestsService.updateLibrary(libraryId, libraryDto, this, new RequestsService.LibraryCallback() {
            @Override
            public void onSuccess(LibraryDto library) {
                Toast.makeText(UpdateLibraryActivity.this, "Library updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }


            public void onError(Exception e) {
                Toast.makeText(UpdateLibraryActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
