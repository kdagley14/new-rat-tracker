package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GraphDateRangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_date_range);

        final EditText etStartDate = (EditText) findViewById(R.id.etStartDate);
        final EditText etEndDate = (EditText) findViewById(R.id.etEndDate);
        final Button bViewGraph = (Button) findViewById(R.id.view_graph_button);

        bViewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = etStartDate.getText().toString();
                String end = etEndDate.getText().toString();

                Intent viewGraphIntent = new Intent(GraphDateRangeActivity.this, GraphActivity.class);
                viewGraphIntent.putExtra("start", start);
                viewGraphIntent.putExtra("end", end);
                GraphDateRangeActivity.this.startActivity(viewGraphIntent);
            }
        });
    }
}
