package fr.istic.mob.star2cd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import fr.istic.mob.star2cd.fragments.BusRouteFragment;
import fr.istic.mob.star2cd.fragments.RouteDetailFragment;
import fr.istic.mob.star2cd.fragments.StopFragment;
import fr.istic.mob.star2cd.fragments.StopTimeFragment;
import fr.istic.mob.star2cd.model.Stop;
import fr.istic.mob.star2cd.utils.StarFactory;

/**
 * Main Activity
 *
 * @author Charly C, Pierre D
 * @version 1.0.1
 */
public class MainActivity extends AppCompatActivity implements BusRouteFragment.BusRouteFragmentListener, StopFragment.StopFragmentListener {

    private StopTimeFragment stopTimesFragment;
    private RouteDetailFragment routeDetailFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = this.getSupportFragmentManager();
        BusRouteFragment busRouteFragment = BusRouteFragment.newInstance();

        replaceFragment(busRouteFragment);
        /*
        List<Stop> stops =  StarFactory.getStops(this, "5", "1");
        for (Stop stop : stops) {
            Log.i("STOPS : ", stop.getStopName());
        }
         */
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
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void searchOnClick(int routeId, int direction) {
        StopFragment stopFragment = StopFragment.newInstance(routeId, direction);
        replaceFragment(stopFragment);
    }

    @Override
    public void onStopClick(int stopId) {

    }

    /**
     * Disable back press for the first fragment
     */
    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            super.onBackPressed();
        }
    }
}
