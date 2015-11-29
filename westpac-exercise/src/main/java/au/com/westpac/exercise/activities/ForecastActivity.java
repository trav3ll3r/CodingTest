package au.com.westpac.exercise.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import au.com.westpac.exercise.R;
import au.com.westpac.exercise.api.ApiCaller;
import au.com.westpac.exercise.api.GetForecast;
import au.com.westpac.exercise.api.GetForecastParams;
import au.com.westpac.exercise.api.GetForecastResponse;
import au.com.westpac.exercise.helpers.LocationHelper;
import au.com.westpac.exercise.helpers.LocationRequester;
import au.com.westpac.exercise.policies.WeatherIconPolicy;

public class ForecastActivity extends ActionBarActivity implements ApiCaller, LocationRequester
{
    private ImageView currentWeatherIcon;
    private TextView currentWeatherLabel;
    private TextView statusLabel;

    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        initResources();

        getPhoneLocation();
    }

    private void initResources()
    {
        currentWeatherIcon  = (ImageView) findViewById(R.id.img_current_weather);
        currentWeatherLabel = (TextView) findViewById(R.id.lbl_current_weather);
        statusLabel         = (TextView) findViewById(R.id.lbl_status);
    }

    private void getPhoneLocation()
    {
        updateStatus(getString(R.string.obtaining_phone_location));
        locationHelper = new LocationHelper(ForecastActivity.this, this);
    }

    @Override
    public void onLocationAvailable(double latitude, double longitude)
    {
        onPhoneLocationObtained(latitude,longitude);
    }

    @Override
    public void onLocationProviderDisabled()
    {
        updateStatus(getString(R.string.location_error_disabled));
    }

    private void onPhoneLocationObtained(double latitude, double longitude)
    {
        loadData(latitude, longitude);
    }

    private void loadData(double latitude, double longitude)
    {
        GetForecastParams apiParams = new GetForecastParams();
        apiParams.setApiKey(getString(R.string.forecast_api_key));
        apiParams.setLatitude(latitude);
        apiParams.setLongitude(longitude);

        updateStatus(getString(R.string.calling_api));
        new GetForecast(this).execute(apiParams);
    }

    @Override
    public void onDataAvailable(GetForecastResponse response)
    {
        if (response != null)
        {
            // SHOW THIS FOR ANY API RESULT
            if (currentWeatherLabel != null)
            {
                currentWeatherLabel.setText(response.getIcon());
            }

            if (response.isSuccess())
            {
                updateStatus(getString(R.string.response_success));
                // API SUCCESS RESPONSE
                int icon = WeatherIconPolicy.getWeatherIconDrawable(response.getIcon());
                if (icon > 0)
                {
                    if (currentWeatherIcon != null)
                    {
                        currentWeatherIcon.setImageResource(icon);
                    }
                }
            }
            else
            {
                // API ERROR RESPONSE
                updateStatus(response.getErrorMessage());
            }
        }
        else
        {
            updateStatus(getString(R.string.response_error_null));
        }
    }

    private void updateStatus(String statusText)
    {
        if (statusLabel != null)
        {
            statusLabel.setText(statusText);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        currentWeatherIcon = null;
        currentWeatherLabel = null;
        statusLabel = null;

        locationHelper = null;
    }
}