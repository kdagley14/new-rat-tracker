package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
* This class sets up the first action within the application.
* It creates the first screen, containing the login and 
* register buttons.
*/
public class MainActivity extends AppCompatActivity {

    /**
    * This method creates all layout objects necessary for
    * a user to interact with the opening screen as soon as 
    * the app loads.
    *
    * @param savedInstanceState  saved states of all objects and widgets
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bLogin = findViewById(R.id.login_button);
        final Button bRegister = findViewById(R.id.register_button);

        bLogin.setOnClickListener(new View.OnClickListener() {
            /**
            * Method starts login activity if user clicks the login button
            *
            * @param view  which button the user clicks
            */
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            /**
            * Method starts register activity if user clicks the register button
            *
            * @param view  which button the user clicks
            */
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}
