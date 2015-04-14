package com.sngv.sunshine.DB;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Android Sunshine CopyRight to Udacity Sunshine Android
 */
public class LocationEntry implements BaseColumns {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

    // Table name
    public static final String TABLE_NAME = "location";

    // The location setting string is what will be sent to openweathermap
    // as the location query.
    public static final String COLUMN_LOCATION_SETTING = "location_setting";

    // Human readable location string, provided by the API.  Because for styling,
    // "Mountain View" is more recognizable than 94043.
    public static final String COLUMN_CITY_NAME = "city_name";

    // In order to uniquely pinpoint the location on the map when we launch the
    // map intent, we store the latitude and longitude as returned by openweathermap.
    public static final String COLUMN_COORD_LAT = "coord_lat";
    public static final String COLUMN_COORD_LONG = "coord_long";

    public static Uri buildLocationUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
