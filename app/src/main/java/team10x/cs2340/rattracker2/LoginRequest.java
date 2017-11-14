package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class takes the information the user
* extends in the Login text fields, and uses it to 
* confirm or deny correct login information
*/
public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://rat-tracker.000webhostapp.com/Login.php";
    private Map<String, String> params;

    /**
    * Method creates functionality for user login function
    *
    * @param username  text placed in field created for username input
    * @param password  text placed in field created for password input
    * @param listener  response listener to read the user's input
    * @return params  information given by method parameters
    */
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    /**
    * Method returns parameters as map object
    * @return params  map object created by previously given information
    */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
