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
        busRouteFragment = (BusRouteFragment) fragmentManager.findFragmentById(R.id.busRouteFragment);
        stopFragment = (StopFragment) fragmentManager.findFragmentById(R.id.stopFragment);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(stopFragment);
        fragmentTransaction.commit();

        Cursor cursor = getContentResolver().query(
                StarContract.BusRoutes.CONTENT_URI,
                null, null, null, "_id");

        while (cursor.moveToNext()) {
            //Log.i("Cursor", "id : " + cursor.getString(0));
        }
    }
}
