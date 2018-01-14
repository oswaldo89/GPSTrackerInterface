package com.oswaldogh89.gpstracker;

/**
 * Created by oswaldogh89 on 13/01/18.
 */

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class HandlerLocation extends Service implements LocationListener {

    private GPSListener interfaceListener;
    private final Context mContext;
    private Location location;
    private static final long DISTANCIA_MINIMA_PARA_ACTUALIZAR = 2;
    private static final long TIEMPO_MINIMO_ENTRE_ACTUALIZACION = 1000 * 60 * 1;
    protected LocationManager locationManager;
    private double latitude;
    private double longitude;

    public HandlerLocation(Context context, GPSListener interfaceListener) {
        this.mContext = context;
        this.interfaceListener = interfaceListener;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                if (location == null) {
                    if (locationManager != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIEMPO_MINIMO_ENTRE_ACTUALIZACION, DISTANCIA_MINIMA_PARA_ACTUALIZAR, this);
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(HandlerLocation.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        interfaceListener.UpdateLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
