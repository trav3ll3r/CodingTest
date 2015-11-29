package au.com.westpac.exercise.api;

public class GetForecastParams
{
    private String apiKey;
    private Double latitude;
    private Double longitude;

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String value)
    {
        this.apiKey = value;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double value)
    {
        this.latitude = value;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double value)
    {
        this.longitude = value;
    }
}
