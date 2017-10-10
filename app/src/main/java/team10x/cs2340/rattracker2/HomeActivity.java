package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button bLogout = (Button) findViewById(R.id.logout_button);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(logoutIntent);
            }
        });
    }
}
