package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * allows user to input a date range for graph
 */
public class GraphDateRangeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_date_range);

        final EditText etStartDate = findViewById(R.id.etStartDate);
        final EditText etEndDate = findViewById(R.id.etEndDate);
        final Button bViewGraph = findViewById(R.id.view_graph_button);

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(GraphDateRangeActivity.this, mDrawerLayout,
                R.string.open, R.string.closed);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                NavigationDrawer nav = new NavigationDrawer();
                nav.getActivityOptions(GraphDateRangeActivity.this, menuItem);
                return true;
            }
        });

        bViewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressWarnings("ChainedMethodCall") String start = etStartDate.getText()
                        .toString();
                @SuppressWarnings("ChainedMethodCall") String end = etEndDate.getText().toString();

                Intent viewGraphIntent = new Intent(GraphDateRangeActivity.this,
                        GraphActivity.class);
                viewGraphIntent.putExtra("start", start);
                viewGraphIntent.putExtra("end", end);
                GraphDateRangeActivity.this.startActivity(viewGraphIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
