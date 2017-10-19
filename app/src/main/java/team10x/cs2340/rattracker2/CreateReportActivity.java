package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        final EditText etDate = (EditText) findViewById(R.id.etDate);
        final EditText etTime = (EditText) findViewById(R.id.etTime);
        final EditText etLocationType = (EditText) findViewById(R.id.etLocationType);
        final EditText etZipCode = (EditText) findViewById(R.id.etZipCode);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etBorough = (EditText) findViewById(R.id.etBorough);
        final EditText etLatitude = (EditText) findViewById(R.id.etLatitude);
        final EditText etLongitude = (EditText) findViewById(R.id.etLongitude);
        final Button bCreate = (Button) findViewById(R.id.create_button);
        final Button bCancel = (Button) findViewById(R.id.cancel_button);

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(CreateReportActivity.this, HomeActivity.class);
                CreateReportActivity.this.startActivity(cancelIntent);
            }
        });

    }
}
