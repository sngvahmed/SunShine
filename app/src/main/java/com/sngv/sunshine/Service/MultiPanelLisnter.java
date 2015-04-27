package com.sngv.sunshine.Service;

import com.sngv.sunshine.DB.domain.WeatherItem;

/**
 * Created by sngv on 27/04/15.
 */
public interface MultiPanelLisnter {
    public void onItemSelect(WeatherItem weatherItem);
}
