package com.oswaldogh89.gpstracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GPSListener {

    String locationUpdated = "";
    TextView txtLocation;
    GPSTracker tracker;
    Button startBtn, stopBtn, getLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLocation = findViewById(R.id.txtLocation);
        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        getLocationBtn = findViewById(R.id.getLocationBtn);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public void clearLog(View v) {
        locationUpdated = "";
        txtLocation.setText(locationUpdated);
    }

    public void startGpsTracker(View v) {
        tracker = new GPSTracker(this, this);
        startBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.VISIBLE);
        getLocationBtn.setVisibility(View.VISIBLE);
    }

    public void stopGpsTracker(View v) {
        tracker.stopUsingGPS();
        startBtn.setVisibility(View.VISIBLE);
        stopBtn.setVisibility(View.GONE);
        getLocationBtn.setVisibility(View.GONE);
    }

    public void getLocation(View v) {
        locationUpdated += "Latitud: " + tracker.getLatitude() + "\n Longitud: " + tracker.getLongitude() + "\n";
        txtLocation.setText(locationUpdated);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void UpdateLocation(Location location) {
        locationUpdated += "Latitud: " + location.getLatitude() + "\n Longitud: " + location.getLongitude() + "\n";
        txtLocation.setText(locationUpdated);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
