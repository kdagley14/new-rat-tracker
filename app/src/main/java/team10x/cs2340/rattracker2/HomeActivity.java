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
import android.widget.ListAdapter;
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
* It displays a list of rat reports pulled from a separate
* database. Also allows users to access the navigation menu
* and option to create a new rat report.
*/
public class HomeActivity extends AppCompatActivity {
    private List<RatReport> rats;
    private ActionBarDrawerToggle mToggle;

    /**
    * This method creates all layout objects necessary for
    * a user to interact with the home screen as soon as 
    * the home screen is intended to load.
    *
    * @param savedInstanceState  saved states of all objects and widgets
    */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //layout objects
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.string.open, R.string.closed);
        final ListView lvReports = (ListView) findViewById(R.id.lvReports);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            /**
            * This method is just confirming that the item selected
            * in the navigation menu equals the Home Activity selection.
            *
            * @param menuItem  MenuItem being compared
            * @return boolean  true or false value
            */
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                NavigationDrawer.getActivityOptions(HomeActivity.this, menuItem);
                return true;
            }
        });

        //generate list from database
        final Response.Listener<String> listener = new Response.Listener<String>() {

            /**
            * Method uses parameter to identify which object
            * to return from database, and then turn that object
            * into a new JSONArray object.
            *
            * @param response  String used to identify desired JSON object
            * @throws e  JSONException
            */
            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONArray jsonResponse = new JSONArray(response);
                    rats = new ArrayList<RatReport>();
                    //turn into ratreport objects
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        rats.add(new RatReport(x.getString("primaryId"), x.getString("date"), x.getString("zip"),
                                x.getString("borough")));
                    }
                    //create a list of the toStrings that just show the date, borough, & zip
                    List<String> testArray = new ArrayList<String>();
                    for (RatReport i: rats) {
                        testArray.add(i.toListString());
                    }

                    // This is the array adapter, it takes the context of the activity as a
                    // first parameter, the type of list view as a second parameter and your
                    // array as a third parameter.
                    ListAdapter arrayAdapter = new ArrayAdapter<String>(
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

        //when you click an item, get the primary id of the corresponding ratReport & send to
        //the detailPage activity so it can get the rest of the information from the database;
        //goes to detail page
        lvReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
            * Method that makes it so when the user clicks an item,
            * they get the primary id of the corresponding rat report, and 
            * send that to the detail page activity so the application can
            * pull the rest of the information from the database
            *
            * @param parent  AdapterView
            * @param view  item View
            * @param position  integer representing object position
            * @param id  long integer representing object id
            */
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

    /**
    * This method is just confirming that the item selected
    * in the navigation menu equals the Home Activity selection.
    *
    * @param item  MenuItem being compared
    * @return boolean  true or false value
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
