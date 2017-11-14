package team10x.cs2340.rattracker2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
* This class creates all aspects and functionality
* of the graph included in the application, 
* including layout objects.  
* Also takes the information from the database
* and converts it into useable data for the graph.
*/
public class GraphActivity extends AppCompatActivity implements GeneralCallbacks {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private List<BarEntry> barEntries = new ArrayList<>();
    private Collection<String> dates = new ArrayList<>();
    private int numEntries;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //layout objects
        TextView etOne = (TextView) findViewById(R.id.one);
        TextView etTwo = (TextView) findViewById(R.id.two);
        TextView etThree = (TextView) findViewById(R.id.three);
        TextView etFour = (TextView) findViewById(R.id.four);
        TextView etFive = (TextView) findViewById(R.id.five);
        TextView etSix = (TextView) findViewById(R.id.six);
        TextView[] key = {etOne, etTwo, etThree, etFour, etFive, etSix};

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(GraphActivity.this, mDrawerLayout, R.string.open, R.string.closed);

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
        barChart = (BarChart) findViewById(R.id.bargraph);
        numEntries = 1;

        //generate list from database
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONObject jsonResponse = new JSONObject(response);
                    String count = jsonResponse.getString("count");
                    barEntries.add(new BarEntry(numEntries, (float) Integer.parseInt(count)));
                    numEntries++;
                    Log.d("FUCK UR BITCH: ", barEntries + "");
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Rat Sightings");
                    BarData theData = new BarData(barDataSet);
                    barChart.setData(theData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // convert date strings into actual dates to be able to create graph intervals
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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


                // add dates to bar graph data
                dates.add(start + " - " + end);
                TextView temp = key[i];
                String tempString = temp.getText() + start + " - " + end;
                temp.setText(tempString);

                // get the new start date
                GregorianCalendar cal2 = new GregorianCalendar();
                cal2.setTime(endDate);
                cal2.add(Calendar.DATE, 1);
                startDate = cal2.getTime();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.d("FUCKING HELL!!! ", barEntries + "");
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        //barChart.setScaleEnabled(true);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        //BarData theData = new BarData(barDataSet);
        //barChart.setData(theData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void VolleyResponse(String data) {

    }
}
