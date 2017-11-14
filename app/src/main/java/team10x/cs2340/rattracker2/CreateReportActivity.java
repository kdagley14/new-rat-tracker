package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Creates rat report based on report details
 * This method will return a report based on users
 * provided data.  If not enough information is given
 * then will return an error message
 */
public class CreateReportActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    /**
    * This method creates all of the necessary layout 
    * objects at start of the activity when a user is creating a report.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(CreateReportActivity.this, mDrawerLayout, R.string.open, R.string.closed);

        //the objects from the layout
        final EditText etDate = (EditText) findViewById(R.id.etDate);
        final EditText etLocationType = (EditText) findViewById(R.id.etLocationType);
        final EditText etZipCode = (EditText) findViewById(R.id.etZipCode);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etBorough = (EditText) findViewById(R.id.etBorough);
        final EditText etLatitude = (EditText) findViewById(R.id.etLatitude);
        final EditText etLongitude = (EditText) findViewById(R.id.etLongitude);
        final Button bCreate = (Button) findViewById(R.id.create_button);
        final Button bClear = (Button) findViewById(R.id.clear_button);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            /**
            * Method that starts activity that matches the
            * menu item that a user selects.
            *
            * @return true or activity begins
            * @param  menu item selected by user
            */
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.reports_list):
                        Intent reportsListIntent = new Intent(CreateReportActivity.this, HomeActivity.class);
                        startActivity(reportsListIntent);
                        break;
                    case(R.id.create_report):
                        Intent createReportIntent = new Intent(CreateReportActivity.this, CreateReportActivity.class);
                        startActivity(createReportIntent);
                        break;
                    case(R.id.view_map):
                        Intent viewMapIntent = new Intent(CreateReportActivity.this, MapActivity.class);
                        startActivity(viewMapIntent);
                        break;
                    case(R.id.logout):
                        Intent logoutIntent = new Intent(CreateReportActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                }
                return true;
            }
        });

        bClear.setOnClickListener(new View.OnClickListener() {
            /**
            * This method simply creates the effect
            * of replacing text in a text box with a blank space
            * as a user clicks to edit it.
            */
            @Override
            public void onClick(View v) {
                etDate.setText("");
                etLocationType.setText("");
                etZipCode.setText("");
                etAddress.setText("");
                etCity.setText("");
                etBorough.setText("");
                etLatitude.setText("");
                etLongitude.setText("");
            }
        });

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the text the user entered in each field
                final String date = etDate.getText().toString();
                final String locationType = etLocationType.getText().toString();
                final String zip = etZipCode.getText().toString();
                final String address = etAddress.getText().toString();
                final String city = etCity.getText().toString();
                final String borough = etBorough.getText().toString();
                final String latitude = etLatitude.getText().toString();
                final String longitude = etLongitude.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            // get the JSON object returned from the database
                            JSONObject jsonResponse = new JSONObject(response);

                            // switch to the home screen
                            Intent homeIntent = new Intent(CreateReportActivity.this, HomeActivity.class);
                            CreateReportActivity.this.startActivity(homeIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // create the request and add it to the queue
                CreateReportRequest request = new CreateReportRequest(date, locationType, zip, address, city, borough, latitude, longitude, listener);
                RequestQueue queue = Volley.newRequestQueue(CreateReportActivity.this);
                queue.add(request);
            };
        });

    }

    /**
    * This method just confirms true or false
    * if a menu item is selected.
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
