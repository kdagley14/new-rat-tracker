package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
* Creates map view containing rat sighting within
* an area and a user defined date range
*/
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActionBarDrawerToggle mToggle;
    private List<RatReport> rats;
    private GoogleMap map;

    /**
    * This method creates all layout objects necessary for
    * a user to interact with the map as soon as 
    * it is intended to load.
    *
    * @param savedInstanceState  saved states of all objects and widgets
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment sMapFragment = SupportMapFragment.newInstance();

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(MapActivity.this, mDrawerLayout, R.string.open,
                R.string.closed);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            /**
            * Method makes sure that selection on navigation menu
            * corresponds with current screen, the map
            *
            * @param menuItem  selected item on menu
            * @return boolean  true if the menu selection and current screen match
            */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                NavigationDrawer nav = new NavigationDrawer();
                nav.getActivityOptions(MapActivity.this, menuItem);
                return true;
            }
        });

        sMapFragment.getMapAsync(MapActivity.this);
        FragmentManager sFm = getSupportFragmentManager();
        //noinspection ChainedMethodCall,ChainedMethodCall
        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();

        final Button bSearch = findViewById(R.id.view_graph_button);
        bSearch.setOnClickListener(new View.OnClickListener() {
            /**
            * Method that starts search date range activity 
            * when filter entries by date is selected
            *
            * @param v  action user takes to begin search date activity
            */
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MapActivity.this, SearchDateRangeActivity.class);
                MapActivity.this.startActivity(searchIntent);
            }
        });
    }

    /**
    * Method loads google map functionality at start of map activity
    *
    * @param googleMap  Google map object
    */
    @SuppressWarnings("FeatureEnvy")
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
                    rats = new ArrayList<>();
                    //turn into ratReport objects
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        rats.add(new RatReport(x.getString("primaryId"),
                                x.getString("date"), x.getString("address"),
                                x.getString("latitude"), x.getString("longitude")));
                    }
                    for (RatReport report: rats) {
                        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                        map.addMarker(new MarkerOptions().position(report.getLatLong())
                                .title(toMapString(report)).snippet("Date: " + report.getDate()
                                        + "    Address: " + report.getAddress()));
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

    /**
    * Method allows for toggle function on selected menu item
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private static String toMapString(RatReport report) {
        return "Reference #: " + report.getPrimaryId();
    }
}
