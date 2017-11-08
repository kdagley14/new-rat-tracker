package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class GraphActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ArrayList<RatReport> rats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //layout objects
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(GraphActivity.this, mDrawerLayout, R.string.open, R.string.closed);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                NavigationDrawer.getActivityOptions(GraphActivity.this, menuItem);
                return true;
            }
        });

        Intent initialIntent = getIntent();
        String start = initialIntent.getStringExtra("start");
        String end = initialIntent.getStringExtra("end");

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

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // convert date strings into actual dates to be able to create graph intervals
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate, eDate;
        try {
            sDate = df.parse(start);
            eDate = df.parse(end);

            //get the number of days between the start and end date
            long diff = eDate.getTime() - sDate.getTime();
            int numDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            int interval = numDays / 6;

            Date startDate = sDate;
            Date endDate;
            for (int i = 0; i < 6; i++) {
                // add interval to start date to get end date
                if (i != 5) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, interval);
                    endDate = cal.getTime();
                } else {
                    endDate = eDate;
                }

                // create request & add to queue
                start = df.format(startDate);
                end = df.format(endDate);
                GraphRequest request = new GraphRequest(start, end, listener);
                RequestQueue queue = Volley.newRequestQueue(GraphActivity.this);
                queue.add(request);

                // get the new start date
                GregorianCalendar cal2 = new GregorianCalendar();
                cal2.setTime(startDate);
                cal2.add(Calendar.DATE, 1);
                startDate = cal2.getTime();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
