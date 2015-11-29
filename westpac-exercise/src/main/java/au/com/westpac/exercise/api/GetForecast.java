package au.com.westpac.exercise.api;

import android.os.AsyncTask;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetForecast extends AsyncTask<GetForecastParams, Void, GetForecastResponse>
{
    private ApiCaller apiCaller;

    public GetForecast(ApiCaller caller) {
        apiCaller = caller;
    }

    @Override
    protected GetForecastResponse doInBackground(GetForecastParams... getForecastParams)
    {
        GetForecastParams requestParams = getForecastParams[0];

        String apiUrl = String.format("https://api.forecast.io/forecast/%s/%s,%s",
                requestParams.getApiKey(),
                requestParams.getLatitude(),
                requestParams.getLongitude());

        HttpURLConnection urlConnection = null;

        try
        {
            URL url = new URL(apiUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

//            urlConnection.setRequestProperty("Accepts", "application/json");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject payload = readStream(in);
            return GetForecastResponse.factory(payload);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(GetForecastResponse response)
    {
        if (apiCaller != null)
        {
            apiCaller.onDataAvailable(response);
        }
    }

    private JSONObject readStream(InputStream in)
    {
        BufferedReader streamReader;

        try
        {
            streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String line;

            while ((line = streamReader.readLine()) != null)
            {
                responseStrBuilder.append(line);
            }

            return new JSONObject(responseStrBuilder.toString());
        }
        catch (Exception e)
        {
            // TODO: LOG VIA SENTRY OR CRASHLYTICS (OR SIMILAR)
            e.printStackTrace();
        }

        return null;
    }
}
