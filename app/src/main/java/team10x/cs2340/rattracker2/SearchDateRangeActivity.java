package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchDateRangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_date_range);

        final EditText etStartDate = (EditText) findViewById(R.id.etStartDate);
        final EditText etEndDate = (EditText) findViewById(R.id.etEndDate);
        final Button bSearch = (Button) findViewById(R.id.view_graph_button);
        final Button bCancel = (Button) findViewById(R.id.cancel_button);

        bSearch.setOnClickListener(new View.OnClickListener() {
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
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(SearchDateRangeActivity.this, MapActivity.class);
                SearchDateRangeActivity.this.startActivity(cancelIntent);
            }
        });
    }
}
