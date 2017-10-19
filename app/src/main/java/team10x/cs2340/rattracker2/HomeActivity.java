package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class HomeActivity extends AppCompatActivity {
    public List<RatReport> rats;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.string.open, R.string.closed);
        final ListView lvReports = (ListView) findViewById(R.id.lvReports);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.reports_list):
                        Intent reportsListIntent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(reportsListIntent);
                        break;
                    case(R.id.create_report):
                        Intent createReportIntent = new Intent(HomeActivity.this, CreateReportActivity.class);
                        startActivity(createReportIntent);
                        break;
                    case(R.id.logout):
                        Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                }
                return true;
            }
        });

        final Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONArray jsonResponse = new JSONArray(response);
                    rats = new ArrayList<RatReport>();
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        rats.add(new RatReport(x.getString("primaryId"), x.getString("date"), x.getString("zip"),
                                x.getString("borough")));
                    }
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


        ListRequest request = new ListRequest(listener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(request);

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
