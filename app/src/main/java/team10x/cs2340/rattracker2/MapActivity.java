package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SupportMapFragment sMapFragment;
    private ArrayList<RatReport> rats;
    private GoogleMap map;

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

        final Button bSearch = (Button) findViewById(R.id.search_button);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MapActivity.this, SearchDateRangeActivity.class);
                MapActivity.this.startActivity(searchIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        // if the date range was set, load the data from that date range
        // else, load the data from the default date range
        Intent initialIntent = getIntent();
        String start = initialIntent.getStringExtra("start");
        String end = initialIntent.getStringExtra("end");

        if (start == null) {
            start = "2016-01-01";
        }
        if (end == null) {
            end = "2016-01-03";
        }

        //generate list from database
        final Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONArray jsonResponse = new JSONArray(response);
                    rats = new ArrayList<RatReport>();
                    //turn into ratreport objects
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        rats.add(new RatReport(x.getString("primaryId"), x.getString("date"), x.getString("address"),
                                x.getString("latitude"), x.getString("longitude")));
                    }
                    for (RatReport report: rats) {
                        map.addMarker(new MarkerOptions().position(report.getLatLong()).title(report.toMapString()).snippet("Date: " + report.getDate() + "    Address: " + report.getAddress()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //create request & add to queue
        MapRequest request = new MapRequest(start, end, listener);
        RequestQueue queue = Volley.newRequestQueue(MapActivity.this);
        queue.add(request);

    }

    public void setDateRange(String start, String end) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
