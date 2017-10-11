package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button bLogout = (Button) findViewById(R.id.logout_button);
        final ListView lvReports = (ListView) findViewById(R.id.lvReports);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(logoutIntent);
            }
        });

        List<RatReport> rats = new ArrayList<RatReport>();
        rats.add(new RatReport("1", "10/10/2017", "12345", "brooklyn"));
        rats.add(new RatReport("2", "10/11/2017", "12348", "staten island"));

        List<String> testArray = new ArrayList<String>();
        for (RatReport i: rats) {
            testArray.add(i.toListString());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                testArray );

        lvReports.setAdapter(arrayAdapter);
    }
}
