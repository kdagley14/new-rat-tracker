package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
* This class creates the activity within the application
* that allows users to enter information during registration
*/
public class RegisterActivity extends AppCompatActivity {

    private int registrationResult;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get all the elements so they can be referenced later
        final EditText etName = findViewById(R.id.name);
        final EditText etUsername = findViewById(R.id.email);
        final EditText etPassword = findViewById(R.id.password);
        final Button bRegister = findViewById(R.id.register_button);
        final Button bCancel = findViewById(R.id.cancel_button);
        final Spinner sUserType = findViewById(R.id.user_type_spinner);

        // set the options for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sUserType.setAdapter(adapter);

        // set the action for when the register button is clicked
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the info the user entered
                @SuppressWarnings("ChainedMethodCall") final String name = etName.getText()
                        .toString();
                @SuppressWarnings("ChainedMethodCall") final String username = etUsername.getText()
                        .toString();
                @SuppressWarnings("ChainedMethodCall") final String password = etPassword.getText()
                        .toString();
                @SuppressWarnings("ChainedMethodCall") final String user_type = sUserType
                        .getSelectedItem().toString();
                int result = register(name, username, password, user_type,true, true);
                if (result == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    builder.setMessage("No field may be null")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                } else if (result == 1) {
                    // switch to the login screen
                    Intent loginIntent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    RegisterActivity.this.startActivity(loginIntent);
                } else {
                    // alert the user that registration failed
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            RegisterActivity.this);
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    builder.setMessage("Email already in use")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            }
        });

        // set the action for when the cancel button is clicked
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // take the user back to the main screen
                Intent cancelIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(cancelIntent);
            }
        });
    }

    //returns 0 if one or more field if empty
    //returns 1 if success
    //returns 2 if email already in use
    int register (String name, String username, String password, String user_type, boolean db,
                  boolean conn) {
        if ("".equals(name) || "".equals(username) || "".equals(password)) {
            registrationResult = 0;
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        // get the JSON object returned from the database
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        // check if the login was successful
                        if (success) {
                            registrationResult = 1;
                        } else {
                            registrationResult = 2;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            // create the register request and add it to the queue
            if (db) {
                RegisterRequest request = new RegisterRequest(name, username, password, user_type,
                        listener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(request);
            } else {
                if (conn) {
                    return 1;
                }
                return 2;
            }

        }
        return registrationResult;
    }
}
