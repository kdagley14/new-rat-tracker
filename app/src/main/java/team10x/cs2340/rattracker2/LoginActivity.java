package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
* This class creates the login activity, including
* all text fields and buttons to allow the user to enter
* their login information.
*/
public class LoginActivity extends AppCompatActivity {

    /**
    * This method creates all layout objects necessary for
    * a user to login as soon as they choose the login activity.
    *
    * @param savedInstanceState  saved states of all objects and widgets
    */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get elements so they can be referenced later
        final EditText etUsername = (EditText) findViewById(R.id.email);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button bLogin = (Button) findViewById(R.id.login_button);
        final Button bCancel = (Button) findViewById(R.id.cancel_button);

        // set action when login button is clicked
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etUsername, etPassword);
            }
        });

        // set action when cancel button is clicked
        bCancel.setOnClickListener(new View.OnClickListener() {
            /**
            * Takes user back to main screen if they click cancel
            *
            * @param view  current app View
            */
            @Override
            public void onClick(View view) {

                // take the user back to the main screen
                Intent cancelIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(cancelIntent);
            }
        });
    }

    /**
    * Method that reads users entries for username and password.
    * 
    * @param user  field where user should enter registered email
    * @param pass  field in which user should enter password corresponding with email entered
    */
    private void login(EditText user, EditText pass) {
        // get the username(email) and password the user entered
        @SuppressWarnings("ChainedMethodCall") final String username = user.getText().toString();
        @SuppressWarnings("ChainedMethodCall") final String password = pass.getText().toString();
        Response.Listener<String> listener = new Response.Listener<String>() {

            /**
            * Method that checks to see if the users credentials match
            * with an existing JSON object from database.
            *
            * If the login succeeds, user is taken to home screen, and
            * if the login failed, then the user is alerted to try again.
            *
            * @param response  users entered credentials
            */
            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    // check if the login was successful
                    if (success) {
                        String name = jsonResponse.getString("name");
                        String user_type = jsonResponse.getString("user_type");

                        // switch to the home screen
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        homeIntent.putExtra("name", name);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("user_type", user_type);
                        LoginActivity.this.startActivity(homeIntent);

                    } else {
                        // alert the user that the login failed
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // create the login request and add it to the queue
        LoginRequest request = new LoginRequest(username, password, listener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);
    }

}
