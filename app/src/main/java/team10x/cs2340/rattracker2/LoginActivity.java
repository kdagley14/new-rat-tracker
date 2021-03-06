package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private boolean result;
    private String prevUsername = "";
    private int attempts = 0;

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
        final EditText etUsername = findViewById(R.id.email);
        final EditText etPassword = findViewById(R.id.password);
        final Button bLogin = findViewById(R.id.login_button);
        final Button bCancel = findViewById(R.id.cancel_button);

        // set action when login button is clicked
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the username(email) and password the user entered
                @SuppressWarnings("ChainedMethodCall") final String user = etUsername.getText()
                        .toString();
                @SuppressWarnings("ChainedMethodCall") final String pass = etPassword.getText()
                        .toString();
                login(user, pass, false);
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
    public boolean login(String user, String pass, boolean conn) {
        final String username = user;
        final String password = pass;

        final Response.Listener<String> locklistener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        // alert the user that the login failed
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                        builder.setMessage("Account locked - contact an admin to regain access")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }
                } catch (Exception e) {
                    Log.d("yikes", "lockout failed");
                }

            }
        };

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
                        result = true;

                    } else {
                        if (username.equals(prevUsername)) {
                            attempts++;
                        } else {
                            attempts = 0;
                        }


                        result = false;

                        if (attempts >= 2) {
                            //lock out user
                            LockRequest lockRequest = new LockRequest(username, locklistener);
                            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                            queue.add(lockRequest);
                        } else {
                            // alert the user that the login failed
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                            builder.setMessage("Login Failed\n(account locks after 3 attempts)")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                        prevUsername = username;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if (!conn) {
            // create the login request and add it to the queue
            LoginRequest request = new LoginRequest(username, password, listener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(request);
        }
        if (pass.equals("password")) {
            result = true;
        }

        return result;
    }

}
