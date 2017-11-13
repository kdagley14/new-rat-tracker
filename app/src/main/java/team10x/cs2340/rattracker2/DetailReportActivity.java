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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        //layout objects
        final Button bBack = (Button) findViewById(R.id.back_button);
        final TextView tvDetails = (TextView) findViewById(R.id.tvReportDetails);

        //if user hits back, go back to homepage
        bBack.setOnClickListener(new View.OnClickListener() {
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

            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONObject x = new JSONObject(response);
                    //turn into a RatReport object
                    RatReport rat = new RatReport(x.getString("primaryId"), x.getString("date"),
                            x.getString("locationType"), x.getString("zip"), x.getString("address"),
                            x.getString("city"), x.getString("borough"), x.getString("latitude"),
                            x.getString("longitude"));
                    //set textview to show the tostring version that displays all fields
                    tvDetails.setText(rat.toDetailString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //create request & add to queue
        DetailRequest request = new DetailRequest(reportId, listener);
        RequestQueue queue = Volley.newRequestQueue(DetailReportActivity.this);
        queue.add(request);
    }
}
