package au.com.westpac.exercise.policies;

import au.com.westpac.exercise.R;

public class WeatherIconPolicy
{
    /**
     * Returns an icon (Drawable) based on icon's string value.
     * If icon Drawable not found, a Drawable of "icon_not_found" is returned
     * @param icon Icon's name to be resolved into a Drawable
     * @return int value of Drawable
     */
    public static int getWeatherIconDrawable(String icon)
    {
        // "DAY" VALUES
        if ("clear-day".equals(icon))
        {
            return R.drawable.icon_clear_day;
        }
        else if ("partly-cloudy-day".equals(icon))
        {
            return R.drawable.icon_partly_cloudy_day;
        }

        // "NIGHT" VALUES
        else if ("clear-night".equals(icon))
        {
            return R.drawable.icon_clear_night;
        }
        else if ("partly-cloudy-night".equals(icon))
        {
            return R.drawable.icon_partly_cloudy_night;
        }

        else
        {
            return R.drawable.icon_not_found;
        }
    }
}
