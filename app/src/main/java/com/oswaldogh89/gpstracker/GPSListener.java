package com.oswaldogh89.gpstracker;

import android.location.Location;

/**
 * Created by oswaldogh89 on 13/01/18.
 */

public interface GPSListener {
    void UpdateLocation(Location location);
}
