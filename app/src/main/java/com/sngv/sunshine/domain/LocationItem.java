package com.sngv.sunshine.domain;

/**
 * Created by sngv on 09/04/15.
 */
public class LocationItem {
    private String PATH_LOCATION;
    private String postalCode;
    private String CONTENT_AUTHORITY;
    private String PATH_WEATHER;
    private String COLUMN_COORD_LAT;
    private String COLUMN_COORD_LONG;

    public String getCONTENT_AUTHORITY() {
        return CONTENT_AUTHORITY;
    }

    public void setCONTENT_AUTHORITY(String CONTENT_AUTHORITY) {
        this.CONTENT_AUTHORITY = CONTENT_AUTHORITY;
    }

    public String getPATH_WEATHER() {
        return PATH_WEATHER;
    }

    public void setPATH_WEATHER(String PATH_WEATHER) {
        this.PATH_WEATHER = PATH_WEATHER;
    }

    public String getCOLUMN_COORD_LAT() {
        return COLUMN_COORD_LAT;
    }

    public void setCOLUMN_COORD_LAT(String COLUMN_COORD_LAT) {
        this.COLUMN_COORD_LAT = COLUMN_COORD_LAT;
    }

    public String getCOLUMN_COORD_LONG() {
        return COLUMN_COORD_LONG;
    }

    public void setCOLUMN_COORD_LONG(String COLUMN_COORD_LONG) {
        this.COLUMN_COORD_LONG = COLUMN_COORD_LONG;
    }

    public LocationItem(){
        PATH_LOCATION = "Egypt";
    }

    public LocationItem(String PATHLOCATION) {
        PATH_LOCATION = PATHLOCATION;
    }

    public String getPATH_LOCATION() {
        return PATH_LOCATION;
    }

    public void setPATH_LOCATION(String PATH_LOCATION) {
        this.PATH_LOCATION = PATH_LOCATION;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
