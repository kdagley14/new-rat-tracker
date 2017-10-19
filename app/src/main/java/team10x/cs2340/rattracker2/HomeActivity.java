package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //layout objects
        final Button bLogout = (Button) findViewById(R.id.logout_button);
        final Button bCreateReport = (Button) findViewById(R.id.create_report_button);
        final ListView lvReports = (ListView) findViewById(R.id.lvReports);

        //return to main screen (where you select login or register)
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(logoutIntent);
            }
        });

        //go to add report screen
        bCreateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createReportIntent = new Intent(HomeActivity.this, CreateReportActivity.class);
                HomeActivity.this.startActivity(createReportIntent);
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
}
