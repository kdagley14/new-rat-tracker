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
class LockRequest extends StringRequest {

    private static final String LOCK_REQUEST_URL =
            "http://rat-tracker.000webhostapp.com/Lock.php";
    private final Map<String, String> params;

    /**
     * Method creates functionality for user login function
     *
     * @param username  text placed in field created for username input
     * @param listener  response listener to read the user's input
     */
    public LockRequest(String username, Response.Listener<String> listener) {
        super(Request.Method.POST, LOCK_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);

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
