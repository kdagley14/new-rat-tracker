package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/*
* This class defines all activities and capabilities
* possible from the home screen.  This is the screen the user 
* is taken to after a successful login.
*
* It displays a list of rat reports pulled from a seperate
* database. Also allows users to access the navigation menu
* and option to create a new rat report.
*/
public class HomeActivity extends AppCompatActivity {
    public List<RatReport> rats;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //layout objects
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.string.open, R.string.closed);
        final ListView lvReports = (ListView) findViewById(R.id.lvReports);
        final Button bLogout = (Button) findViewById(R.id.logout_button);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                NavigationDrawer.getActivityOptions(HomeActivity.this, menuItem);
                return true;
            }
        });

        //generate list from database
        final Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONArray jsonResponse = new JSONArray(response);
                    rats = new ArrayList<RatReport>();
                    //turn innto ratreport objects
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        rats.add(new RatReport(x.getString("primaryId"), x.getString("date"), x.getString("zip"),
                                x.getString("borough")));
                    }
                    //create a list of the tostrings that just show the date, borough, & zip
                    List<String> testArray = new ArrayList<String>();
                    for (RatReport i: rats) {
                        testArray.add(i.toListString());
                    }

                    // This is the array adapter, it takes the context of the activity as a
                    // first parameter, the type of list view as a second parameter and your
                    // array as a third parameter.
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            HomeActivity.this,
                            android.R.layout.simple_list_item_1,
                            testArray );

                    lvReports.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //create request & add to queue
        ListRequest request = new ListRequest(listener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(request);

        //when you click an item, get the primary id of the corresponding ratreport & send to
        //the detailpage activity so it can get the rest of the information from the database;
        //goes to detail page
        lvReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RatReport rat = rats.get(position);
                String reportId = rat.getPrimaryId();
                Intent detailIntent = new Intent(HomeActivity.this, DetailReportActivity.class);
                detailIntent.putExtra("primary_id", reportId);
                HomeActivity.this.startActivity(detailIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
