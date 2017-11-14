package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
* This class creates the page that results 
* from clicking on a specific rat report, and allows 
* the user to see the details of each respective sighting. 
*/
public class DetailReportActivity extends AppCompatActivity {

    /**
    * Creates all necessary layout objects for a user to
    * interact with the detailed report.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        //layout objects
        final Button bBack = (Button) findViewById(R.id.back_button);
        final TextView tvDetails = (TextView) findViewById(R.id.tvReportDetails);

        bBack.setOnClickListener(new View.OnClickListener() {
            /**
            * If user hits back, app goes back to homepage
            */
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(DetailReportActivity.this, HomeActivity.class);
                DetailReportActivity.this.startActivity(backIntent);
            }
        });

        //get primary id of the report clicked
        Bundle extras = getIntent().getExtras();
        String reportId = extras.getString("primary_id");

        Response.Listener<String> listener = new Response.Listener<String>() {

            /**
            * Pulls rat report object from database and
            * turns it into a new RatReport object.
            *
            * @param response  id of object selected
            * @throws e  JSONException if occurs  
            */
            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONObject x = new JSONObject(response);
                    RatReport rat = new RatReport(x.getString("primaryId"), x.getString("date"),
                            x.getString("locationType"), x.getString("zip"), x.getString("address"),
                            x.getString("city"), x.getString("borough"), x.getString("latitude"),
                            x.getString("longitude"));
                    //set TextView to show the toString version that displays all fields
                    tvDetails.setText(rat.toDetailString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DetailRequest request = new DetailRequest(reportId, listener);
        RequestQueue queue = Volley.newRequestQueue(DetailReportActivity.this);
        queue.add(request);
    }
}
