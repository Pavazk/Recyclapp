package com.example.recyclapp.modules.bins;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.List;

public class GPS implements LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 0;
    private static final long MIN_TIME_FOR_UPDATE = 30000;

    public static GPS getInstance() {
        return new GPS();
    }
    @SuppressLint("MissingPermission")
    public String getLocalization(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        String response = "";

        try {
            if (isGPSEnable) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    if (location.getLatitude() == 0.0) {
                        response += 0;
                    } else {
                        response += location.getLatitude();
                    }
                    response += "|";
                    if (location.getLongitude() == 0.0) {
                        response += 0;
                    } else {
                        response += location.getLongitude();
                    }
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (isNetworkEnabled) {
                if (locationManager.getAllProviders().contains("network")) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    if (location.getLatitude() == 0.0) {
                        response += 0;
                    } else {
                        response += location.getLatitude();
                    }
                    response += "|";
                    if (location.getLongitude() == 0.0) {
                        response += 0;
                    } else {
                        response += location.getLongitude();
                    }
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
    }

    @Override
    public void onFlushComplete(int requestCode) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }
}
