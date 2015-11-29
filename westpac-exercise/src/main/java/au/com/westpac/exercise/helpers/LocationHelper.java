package au.com.westpac.exercise.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper
{
    private LocationRequester locationRequester;

    private LocationManager locationManager;
    private LocationListener locationListener;

    public LocationHelper(Context context, LocationRequester requester)
    {
        locationRequester = requester;

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener()
        {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                onLocationFound(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider)
            {
                onLocationProviderDisabled();
            }
        };

        // Register the listener with the Location Manager to receive GPS location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void onLocationProviderDisabled()
    {
        if (locationRequester != null)
        {
            locationRequester.onLocationProviderDisabled();
        }

        stopLocationUpdates();
    }

    private void onLocationFound(Location location)
    {
        if (locationRequester != null)
        {
            locationRequester.onLocationAvailable(location.getLatitude(), location.getLongitude());
        }

        stopLocationUpdates();
    }

    private void stopLocationUpdates()
    {
        if (locationListener != null)
        {
            locationManager.removeUpdates(locationListener);
        }
    }
}
