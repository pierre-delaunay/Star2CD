package fr.istic.mob.star2cd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import fr.istic.mob.star2cd.fragments.BusRouteFragment;
import fr.istic.mob.star2cd.fragments.RouteDetailFragment;
import fr.istic.mob.star2cd.fragments.StopFragment;
import fr.istic.mob.star2cd.fragments.StopTimeFragment;
import fr.istic.mob.star2cd.model.BusRoute;
import fr.istic.mob.star2cd.utils.StarContract;

public class MainActivity extends AppCompatActivity {

    private BusRouteFragment busRouteFragment;
    private StopFragment stopFragment;
    private StopTimeFragment stopTimesFragment;
    private RouteDetailFragment routeDetailFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = this.getSupportFragmentManager();
        //busRouteFragment = (BusRouteFragment) fragmentManager.findFragmentById(R.id.busRouteFragment);

        stopFragment = StopFragment.newInstance(null, 1);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, stopFragment);
        fragmentTransaction.commit();

    }
}
