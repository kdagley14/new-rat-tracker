package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
* This class allows the user the ability
* to search the rat sigtings between specified date ranges
*/
public class SearchDateRangeActivity extends AppCompatActivity {

    /**
    * When the user is viewing the map and has 
    * the ability to manipulate the date range, this 
    * method immediately creates all of the necessary
    * objects that allow the user to edit.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_date_range);

        final EditText etStartDate = (EditText) findViewById(R.id.etStartDate);
        final EditText etEndDate = (EditText) findViewById(R.id.etEndDate);
        final Button bSearch = (Button) findViewById(R.id.view_graph_button);
        final Button bCancel = (Button) findViewById(R.id.cancel_button);

        bSearch.setOnClickListener(new View.OnClickListener() {
            /**
            * Method that brings click functionality to start and 
            * end buttons
            */
            @Override
            public void onClick(View v) {
                String start = etStartDate.getText().toString();
                String end = etEndDate.getText().toString();

                Intent searchIntent = new Intent(SearchDateRangeActivity.this, MapActivity.class);
                searchIntent.putExtra("start", start);
                searchIntent.putExtra("end", end);
                SearchDateRangeActivity.this.startActivity(searchIntent);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            /**
            * Brings click functionality to cancel button within activity.
            */
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(SearchDateRangeActivity.this, MapActivity.class);
                SearchDateRangeActivity.this.startActivity(cancelIntent);
            }
        });
    }
}
