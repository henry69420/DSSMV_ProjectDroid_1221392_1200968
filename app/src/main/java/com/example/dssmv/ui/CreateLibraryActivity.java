package com.example.dssmv.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv.R;
import com.example.dssmv.dtos.LibraryDto;
import com.example.dssmv.dtos.LocalTimeDto;
import com.example.dssmv.service.RequestsService;

public class CreateLibraryActivity extends AppCompatActivity {

    private EditText nameInput, addressInput, openDaysInput;
    private TimePicker openTimePicker, closeTimePicker;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_create);

        nameInput = findViewById(R.id.nameInput);
        addressInput = findViewById(R.id.addressInput);
        openDaysInput = findViewById(R.id.openDaysInput);
        openTimePicker = findViewById(R.id.openTimePicker);
        closeTimePicker = findViewById(R.id.closeTimePicker);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> createLibrary());
        closeTimePicker.setIs24HourView(true);
        openTimePicker.setIs24HourView(true);
    }

    private void createLibrary() {

        String name = nameInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String openDays = openDaysInput.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || openDays.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        int openHour = openTimePicker.getHour();
        int openMinute = openTimePicker.getMinute();
        int closeHour = closeTimePicker.getHour();
        int closeMinute = closeTimePicker.getMinute();
        if (closeHour < openHour || (closeHour == openHour && closeMinute <= openMinute)) {
            Toast.makeText(this, "Closing time must be after opening time", Toast.LENGTH_SHORT).show();
            return;
        }

        String openTime = String.format("%02d:%02d", openHour, openMinute);
        String closeTime = String.format("%02d:%02d", closeHour, closeMinute);


        LibraryDto libraryDto = new LibraryDto(
                name,
                address,
                openDays,
                openTime,
                closeTime
        );


        RequestsService.postLibrary(libraryDto, this, new RequestsService.LibraryCallback() {
            @Override
            public void onSuccess(LibraryDto library) {
                Toast.makeText(CreateLibraryActivity.this, "Library Created", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(CreateLibraryActivity.this, "Error creating library: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
