package com.example.dssmv.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dssmv.R;
import com.example.dssmv.model.Library;
import com.example.dssmv.service.RequestsService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class LibraryMapActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView osmMap;
    private IMapController mapController;
    private FusedLocationProviderClient fusedLocationClient;
    private Switch switchShowAll;
    private ProgressBar loadingIndicator;


    private static class MappableLibrary {
        final Library library;
        final GeoPoint coordinates;
        final boolean isValid;

        MappableLibrary(Library library, GeoPoint coordinates, boolean isValid) {
            this.library = library;
            this.coordinates = coordinates;
            this.isValid = isValid;
        }
    }

    private List<MappableLibrary> mappableLibraries = new ArrayList<>();
    private ExecutorService geocodingExecutor = Executors.newSingleThreadExecutor();
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_library_map);

        switchShowAll = findViewById(R.id.switchShowAll);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        osmMap = findViewById(R.id.mapview);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setupOsmMap();
        checkLocationPermissionAndLoadData();

        switchShowAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (osmMap != null && !mappableLibraries.isEmpty()) {
                osmMap.getOverlays().clear();
                osmMap.invalidate();
                displayLibrariesOnMap();
            }
        });
    }

    private void setupOsmMap() {
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setMultiTouchControls(true);
        mapController = osmMap.getController();
        mapController.setZoom(10.0);

        CompassOverlay compassOverlay = new CompassOverlay(this, osmMap);
        compassOverlay.enableCompass();
        osmMap.getOverlays().add(compassOverlay);

        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(osmMap);
        locationOverlay.enableMyLocation();
        osmMap.getOverlays().add(locationOverlay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        osmMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        osmMap.onPause();
    }



    private void checkLocationPermissionAndLoadData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getDeviceLocationAndLoadLibraries();
        }
    }

    private void getDeviceLocationAndLoadLibraries() {
        try {
            loadingIndicator.setVisibility(View.VISIBLE);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            lastKnownLocation = location;
                            GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                            mapController.setCenter(currentLocation);
                        }
                        loadLibrariesAndGeocode();
                    });
        } catch (SecurityException e) {
            Toast.makeText(this, "A permissão de localização é necessária.", Toast.LENGTH_SHORT).show();
            loadLibrariesAndGeocode();
        }
    }

    private void loadLibrariesAndGeocode() {
        RequestsService.getLibraries(this, new RequestsService.LibraryListCallback() {
            @Override
            public void onSuccess(List<Library> libraries) {
                geocodeLibraries(libraries);
            }

            @Override
            public void onError(Exception e) {
                uiHandler.post(() -> {
                    loadingIndicator.setVisibility(View.GONE);
                    Toast.makeText(LibraryMapActivity.this, "Erro ao carregar bibliotecas: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void geocodeLibraries(List<Library> libraries) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        geocodingExecutor.execute(() -> {
            List<MappableLibrary> results = new ArrayList<>();
            for (Library library : libraries) {
                GeoPoint coordinates = null;
                boolean isValid = false;


                String address = library.getAddress();
                if (address == null || address.trim().isEmpty()) {

                    results.add(new MappableLibrary(library, null, false));
                    continue;
                }


                try {

                    List<Address> addresses = geocoder.getFromLocationName(address, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address geoAddress = addresses.get(0);
                        coordinates = new GeoPoint(geoAddress.getLatitude(), geoAddress.getLongitude());
                        isValid = true;
                    }
                } catch (IOException e) {

                }
                results.add(new MappableLibrary(library, coordinates, isValid));
            }

            uiHandler.post(() -> {
                mappableLibraries = results;
                displayLibrariesOnMap();
                loadingIndicator.setVisibility(View.GONE);
            });
        });
    }

    private void displayLibrariesOnMap() {
        if (mappableLibraries.isEmpty()) {
            Toast.makeText(this, "Nenhuma biblioteca com endereço válido encontrada.", Toast.LENGTH_LONG).show();
            return;
        }

        osmMap.getOverlays().clear();
        boolean showAll = switchShowAll.isChecked();

        if (showAll) {
            plotAllLibraries();
        } else {
            findAndPlotNearestLibrary();
        }


        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(osmMap);
        locationOverlay.enableMyLocation();
        osmMap.getOverlays().add(locationOverlay);

        osmMap.invalidate();
    }

    private void findAndPlotNearestLibrary() {
        if (lastKnownLocation == null) {
            Toast.makeText(this, "Localização do usuário indisponível. A mostrar todas as bibliotecas.", Toast.LENGTH_LONG).show();
            switchShowAll.setChecked(true);
            plotAllLibraries();
            return;
        }

        MappableLibrary nearestLibrary = null;
        float minDistance = Float.MAX_VALUE;


        Location userLocation = new Location("user");
        userLocation.setLatitude(lastKnownLocation.getLatitude());
        userLocation.setLongitude(lastKnownLocation.getLongitude());

        for (MappableLibrary mappable : mappableLibraries) {
            if (mappable.isValid) {
                Location libraryLocation = new Location("library");
                libraryLocation.setLatitude(mappable.coordinates.getLatitude());
                libraryLocation.setLongitude(mappable.coordinates.getLongitude());

                float distance = userLocation.distanceTo(libraryLocation);

                if (distance < minDistance) {
                    minDistance = distance;
                    nearestLibrary = mappable;
                }
            }
        }

        if (nearestLibrary != null) {
            Marker marker = new Marker(osmMap);
            marker.setPosition(nearestLibrary.coordinates);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(nearestLibrary.library.getName());
            marker.setSnippet("Mais próxima (" + String.format("%.2f km", minDistance / 1000) + ")");
            osmMap.getOverlays().add(marker);

            mapController.animateTo(nearestLibrary.coordinates);
            mapController.setZoom(14.0);

            Toast.makeText(this, "Biblioteca mais próxima: " + nearestLibrary.library.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nenhuma biblioteca próxima encontrada.", Toast.LENGTH_LONG).show();
        }
    }

    private void plotAllLibraries() {
        for (MappableLibrary mappable : mappableLibraries) {
            if (mappable.isValid) {
                Marker marker = new Marker(osmMap);
                marker.setPosition(mappable.coordinates);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(mappable.library.getName());
                marker.setSnippet(mappable.library.getAddress());
                osmMap.getOverlays().add(marker);
            }
        }

        GeoPoint center = lastKnownLocation != null ?
                new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()) :
                new GeoPoint(41.1579, -8.6291); // Porto
        mapController.setCenter(center);
        mapController.setZoom(10.0);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocationAndLoadLibraries();
            } else {
                Toast.makeText(this, "A funcionalidade de localização está limitada sem permissão de GPS.", Toast.LENGTH_LONG).show();
                loadLibrariesAndGeocode();
            }
        }
    }
}