package au.com.westpac.exercise.api;

import android.test.InstrumentationTestCase;
import org.json.JSONObject;

public class GetForecastResponseTest extends InstrumentationTestCase
{
    public void testFactoryShouldFailWithEmptyPayload()
    {
        JSONObject payload = mockUpEmptyJsonPayload();

        GetForecastResponse response = GetForecastResponse.factory(payload);
        assertEquals("Response should be False", false, response.isSuccess());
        assertNotNull("Response error message should not be NULL", response.getErrorMessage());
    }

    public void testFactoryShouldFailWithPartialPayload()
    {
        JSONObject payload = mockUpBadJsonPayload();

        GetForecastResponse response = GetForecastResponse.factory(payload);
        assertEquals("Response should be False", false, response.isSuccess());
        assertNotNull("Response error message should not be NULL", response.getErrorMessage());
    }

    public void testFactoryShouldFailWithNullPayload()
    {
        GetForecastResponse response = GetForecastResponse.factory(null);
        assertEquals("Response should be False", false, response.isSuccess());
        assertNotNull("Response error message should not be NULL", response.getErrorMessage());
    }

    public void testFactoryShouldSucceed()
    {
        JSONObject payload = mockUpMinValidJsonPayload();

        GetForecastResponse response = GetForecastResponse.factory(payload);
        assertEquals("Response should be True", true, response.isSuccess());
        assertNull("Response error message should be NULL", response.getErrorMessage());
        assertEquals("Response icon should be 'clear-day", "clear-day", response.getIcon());
    }

    private JSONObject mockUpMinValidJsonPayload()
    {
        JSONObject result = null;
        try
        {
            result = new JSONObject("{\"currently\": { \"icon\": \"clear-day\" }}");
        }
        catch (Exception ex)
        {
            //DO NOTHING: SCOPE OF THE TEST IS TO REALLY GET EXPECTED VALID Payload
        }

        return result;
    }

    private JSONObject mockUpEmptyJsonPayload()
    {
        JSONObject result = null;
        try
        {
            result = new JSONObject("{}");
        }
        catch (Exception ex)
        {
            //DO NOTHING: SCOPE OF THE TEST IS TO REALLY GET EMPTY Payload
        }

        return result;
    }

    private JSONObject mockUpBadJsonPayload()
    {
        JSONObject result = null;
        try
        {
            result = new JSONObject("{\"currently\": {}}");
        }
        catch (Exception ex)
        {
            //DO NOTHING: SCOPE OF THE TEST IS TO REALLY GET LESS THAN MIN REQUIRED Payload
        }

        return result;
    }
}