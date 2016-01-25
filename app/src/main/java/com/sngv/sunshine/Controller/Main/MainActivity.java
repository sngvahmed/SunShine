package com.sngv.sunshine.Controller.Main;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sngv.sunshine.Controller.Details.DetailActivity;
import com.sngv.sunshine.Controller.Details.DetailFragment;
import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.R;
import com.sngv.sunshine.Utility.MultiPanelLisnter;
import com.sngv.sunshine.Utility.Utility;


public class MainActivity extends ActionBarActivity implements MultiPanelLisnter {
    private boolean mTwoPane = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (findViewById(R.id.details_activity) != null) {
            mTwoPane = true;
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_activity, new MainFragment())
                        .commit();
            }
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
        MainFragment mainFragment = ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity));
        if (mainFragment != null) {
            mainFragment.setmUseTodayLayout(!mTwoPane);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainFragment mainFragment = ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity));
        if (mainFragment != null) {
            return mainFragment.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPref.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)
        );

        Uri uri = Uri.parse(getString(R.string.location_site)).buildUpon().
                appendQueryParameter("q", location).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelect(WeatherItem weatherItem) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putSerializable(DetailActivity.DATE_KEY, weatherItem);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_activity, fragment)
                    .commit();
        } else {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.details_fragment);
            init(dialog, weatherItem);
            dialog.show();
        }
    }

    private void init(final Dialog dialog, WeatherItem weatherItem) {
        double maxD = Double.parseDouble(weatherItem.getMaxGrade());
        double minD = Double.parseDouble(weatherItem.getMinGrade());
        double degreed = Double.parseDouble(weatherItem.getDegree());
        double windSpeed = Double.parseDouble(weatherItem.getWindSpeed());
        double pressured = Double.parseDouble(weatherItem.getPressure());
        double humitiy = Double.parseDouble(weatherItem.getHumidity());

        ((TextView) dialog.findViewById(R.id.country_details)).setText(weatherItem.getCountry());
        ((TextView) dialog.findViewById(R.id.day_details)).setText(weatherItem.getDay());
        ((TextView) dialog.findViewById(R.id.minGrade_details)).setText(Utility.formatTemp(minD, getApplicationContext()));
        ((TextView) dialog.findViewById(R.id.maxGrade_details)).setText(Utility.formatTemp(maxD, getApplicationContext()));
        ((TextView) dialog.findViewById(R.id.description_details)).setText(weatherItem.getDescription());
        ((TextView) dialog.findViewById(R.id.windSpeed_details)).setText(Utility.formatWindSpeed(windSpeed, getApplicationContext()));
        ((TextView) dialog.findViewById(R.id.pressure_details)).setText(Utility.formatPressure(pressured, getApplicationContext()));
        ((TextView) dialog.findViewById(R.id.humidity_details)).setText(Utility.formatHumidity(humitiy, getApplicationContext()));
        ((TextView) dialog.findViewById(R.id.degree_details)).setText(Utility.formatDegree(degreed, getApplicationContext()));
        ((ImageView) dialog.findViewById(R.id.CloudImage_details)).setContentDescription(weatherItem.getDescription());
        ((ImageView) dialog.findViewById(R.id.close_dialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
