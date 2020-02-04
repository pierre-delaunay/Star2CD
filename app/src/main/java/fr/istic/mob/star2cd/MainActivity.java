package fr.istic.mob.star2cd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import fr.istic.mob.star2cd.fragments.BusRouteFragment;
import fr.istic.mob.star2cd.fragments.RouteDetailFragment;
import fr.istic.mob.star2cd.fragments.StopFragment;
import fr.istic.mob.star2cd.fragments.StopTimeFragment;
import fr.istic.mob.star2cd.model.StopTime;
import fr.istic.mob.star2cd.adapters.SearchAdapter;
import fr.istic.mob.star2cd.utils.StarContract;

/**
 * Main Activity
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class MainActivity extends AppCompatActivity implements BusRouteFragment.BusRouteFragmentListener, StopFragment.StopFragmentListener, StopTimeFragment.StopTimeFragmentListener {

    private FragmentManager fragmentManager;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        fragmentManager = this.getSupportFragmentManager();
        BusRouteFragment busRouteFragment = BusRouteFragment.newInstance();

        replaceFragment(busRouteFragment);
    }

    /**
     * Replace current fragment with a new one
     * Previous fragment is added to back stack
     *
     * @param fragment the new fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        if (!isTablet) {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        } else {
            if (!(fragment instanceof BusRouteFragment)) {
                fragmentTransaction.replace(R.id.frameLayout2, fragment);
            } else {
                fragmentTransaction.replace(R.id.frameLayout, fragment);
            }
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void searchOnClick(int routeId, int direction) {
        StopFragment stopFragment = StopFragment.newInstance(routeId, direction);
        replaceFragment(stopFragment);
    }

    @Override
    public void onStopClick(int stopId, int routeId, int direction) {
        StopTimeFragment stopTimeFragment = StopTimeFragment.newInstance(stopId, routeId, direction);
        replaceFragment(stopTimeFragment);
    }

    @Override
    public void onStopTimeClick(StopTime chosenStopTime, int routeId) {
        RouteDetailFragment routeDetailFragment = RouteDetailFragment.newInstance(chosenStopTime, routeId);
        replaceFragment(routeDetailFragment);
    }

    /**
     * Disable back press for the first fragment
     */
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.search_view_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setContentView(R.layout.search_view);
                ListView list = findViewById(R.id.searchList);

                final Cursor cursor = getContentResolver().query(
                        StarContract.Search.CONTENT_URI,
                        null, query.trim(), null, null);
                final SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), cursor);
                list.setAdapter(searchAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onCreate(savedInstanceState);
    }
}
