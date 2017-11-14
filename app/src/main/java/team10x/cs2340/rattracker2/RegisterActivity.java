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

/*
* This class creates the activity within the application
* that allows users to enter information during registration
*/
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get all the elements so they can be referenced later
        final EditText etName = (EditText) findViewById(R.id.name);
        final EditText etUsername = (EditText) findViewById(R.id.email);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button bRegister = (Button) findViewById(R.id.register_button);
        final Button bCancel = (Button) findViewById(R.id.cancel_button);
        final Spinner sUserType = (Spinner) findViewById(R.id.user_type_spinner);

        // set the options for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sUserType.setAdapter(adapter);

        // set the action for when the register button is clicked
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the info the user entered
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String user_type = sUserType.getSelectedItem().toString();
                register(name, username, password, user_type);

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

    private void register (String name, String username, String password, String user_type) {
        if (name.equals("") || username.equals("") || password.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("No field may be null")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
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
                            // switch to the login screen
                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(loginIntent);
                        } else {
                            // alert the user that registration failed
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Email already in use")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            // create the register request and add it to the queue
            RegisterRequest request = new RegisterRequest(name, username, password, user_type, listener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(request);
        }
    }
}
