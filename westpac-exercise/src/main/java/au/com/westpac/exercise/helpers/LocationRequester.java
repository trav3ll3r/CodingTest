package au.com.westpac.exercise.helpers;

public interface LocationRequester
{
    void onLocationAvailable(double latitude, double longitude);
    void onLocationProviderDisabled();
}
