package com.sngv.sunshine.Controller.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sngv.sunshine.Controller.Details.DetailActivity;
import com.sngv.sunshine.Controller.Details.DetailFragment;
import com.sngv.sunshine.Controller.Setting.SettingsActivity;
import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.R;
import com.sngv.sunshine.Service.MultiPanelLisnter;


public class MainActivity extends ActionBarActivity implements MultiPanelLisnter{
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
        }
        MainFragment mainFragment = ((MainFragment)getSupportFragmentManager().findFragmentById(R.id.main_activity));
        if(mainFragment != null){
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this , SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_map){
            openPreferredLocationInMap();
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

        if(intent.resolveActivity(this.getPackageManager()) != null){
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
            final Intent detailsIntent = new Intent(this , DetailActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, weatherItem);
            startActivity(detailsIntent);
        }
    }
}
