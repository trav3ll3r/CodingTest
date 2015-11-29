package au.com.westpac.exercise.api;

import org.json.JSONObject;

public class GetForecastResponse
{
    private boolean success;
    private String icon;
    private String errorMessage;

    public GetForecastResponse()
    {
        this.success = false;
        this.icon = "unknown";
        this.errorMessage = null;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public static GetForecastResponse factory(JSONObject payload)
    {
        GetForecastResponse result = new GetForecastResponse();

        if (payload != null)
        {
            try
            {
                if (payload.has("currently"))
                {
                    JSONObject currently = payload.getJSONObject("currently");
                    if (currently.has("icon"))
                    {
                        result.icon = currently.getString("icon");
                        result.success = true;
                    }
                    else
                    {
                        result.errorMessage = "Response payload's 'icon' node is missing";
                    }
                }
                else
                {
                    result.errorMessage = "Response payload's 'currently' node is missing";
                }
            }
            catch (Exception ex)
            {
                result.errorMessage = "Response payload's structure does not match expected structure";
            }
        }
        else
        {
            result.errorMessage = "Response payload is NULL";
        }

        return result;
    }
}
