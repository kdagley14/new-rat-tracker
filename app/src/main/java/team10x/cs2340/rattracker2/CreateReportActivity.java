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

/**
 * Creates rat report based on report details
 * This method will return a report based on users
 * provided data.  If not enough information is given
 * then will return an error message
 */
public class CreateReportActivity extends AppCompatActivity {

    /**
    * This method creates all of the necessary layout 
    * objects at start of the activity when a user is creating a report.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(CreateReportActivity.this,
                mDrawerLayout, R.string.open, R.string.closed);

        //the objects from the layout
        final EditText etDate = findViewById(R.id.etDate);
        final EditText etLocationType = findViewById(R.id.etLocationType);
        final EditText etZipCode = findViewById(R.id.etZipCode);
        final EditText etAddress = findViewById(R.id.etAddress);
        final EditText etCity = findViewById(R.id.etCity);
        final EditText etBorough = findViewById(R.id.etBorough);
        final EditText etLatitude = findViewById(R.id.etLatitude);
        final EditText etLongitude = findViewById(R.id.etLongitude);
        final Button bCreate = findViewById(R.id.create_button);
        final Button bClear = findViewById(R.id.clear_button);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            /**
            * Method that starts activity that matches the
            * menu item that a user selects.
            *
            * @return true or activity begins
            * @param  menuItem item selected by user
            */
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                NavigationDrawer nav = new NavigationDrawer();
                nav.getActivityOptions(CreateReportActivity.this, menuItem);
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
                @SuppressWarnings("ChainedMethodCall") final String date = etDate.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String locationType = etLocationType.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String zip = etZipCode.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String address = etAddress.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String city = etCity.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String borough = etBorough.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String latitude = etLatitude.getText().toString();
                @SuppressWarnings("ChainedMethodCall") final String longitude = etLongitude.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            // get the JSON object returned from the database
                            //JSONObject jsonResponse = new JSONObject(response);

                            // switch to the home screen
                            Intent homeIntent = new Intent(CreateReportActivity.this,
                                    HomeActivity.class);
                            CreateReportActivity.this.startActivity(homeIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                // create the request and add it to the queue
                CreateReportRequest request = new CreateReportRequest(date, locationType, zip,
                        address, city, borough, latitude, longitude, listener);
                RequestQueue queue = Volley.newRequestQueue(CreateReportActivity.this);
                queue.add(request);
            }
        });

    }
}
