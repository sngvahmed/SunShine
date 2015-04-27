package com.sngv.sunshine.Controller.Main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sngv.sunshine.Controller.Details.DetailActivity;
import com.sngv.sunshine.Controller.Details.DetailFragment;
import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.R;
import com.sngv.sunshine.Service.MultiPanelLisnter;


public class MainActivity extends ActionBarActivity implements MultiPanelLisnter{
    private boolean mTwoPane = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity, new MainFragment())
                    .commit();
        }
        if (findViewById(R.id.details_activity) != null) {
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.details_activity, new DetailFragment())
//                        .commit();
//            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
