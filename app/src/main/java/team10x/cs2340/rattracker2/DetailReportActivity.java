package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        final Button bBack = (Button) findViewById(R.id.back_button);
        final TextView tvDetails = (TextView) findViewById(R.id.tvReportDetails);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(DetailReportActivity.this, HomeActivity.class);
                DetailReportActivity.this.startActivity(backIntent);
            }
        });

    }
}
