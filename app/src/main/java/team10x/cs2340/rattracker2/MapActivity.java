package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SupportMapFragment sMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        sMapFragment = SupportMapFragment.newInstance();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(MapActivity.this, mDrawerLayout, R.string.open, R.string.closed);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.reports_list):
                        Intent reportsListIntent = new Intent(MapActivity.this, HomeActivity.class);
                        startActivity(reportsListIntent);
                        break;
                    case(R.id.create_report):
                        Intent createReportIntent = new Intent(MapActivity.this, CreateReportActivity.class);
                        startActivity(createReportIntent);
                        break;
                    case(R.id.view_map):
                        Intent viewMapIntent = new Intent(MapActivity.this, MapActivity.class);
                        startActivity(viewMapIntent);
                        break;
                    case(R.id.logout):
                        Intent logoutIntent = new Intent(MapActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                }
                return true;
            }
        });

        sMapFragment.getMapAsync(MapActivity.this);
        FragmentManager sFm = getSupportFragmentManager();
        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng newYork = new LatLng(40, -73);
        googleMap.addMarker(new MarkerOptions().position(newYork).title("HEY THERE"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newYork));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
