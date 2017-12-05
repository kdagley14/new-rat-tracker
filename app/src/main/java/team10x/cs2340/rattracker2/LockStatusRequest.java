package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class pulls the details from a specific
* rat report entry and puts it into a HashMap.
*/
class LockStatusRequest extends StringRequest {

    private static final String LOCKSTATUS_REQUEST_URL =
            "http://rat-tracker.000webhostapp.com/LockStatus.php";
    private final Map<String, String> params;

    /**
     * Accesses the database and sends the primary key to get the rest of the data
     * that responds to that key.
     *
     * @param username  object ID
     * @param listener  response listener to register user's action
     */
    public LockStatusRequest(String username, Response.Listener<String> listener) {
        super(Request.Method.POST, LOCKSTATUS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    /**
     * This method returns parameter information from HashMap
     *
     * @return params  map object containing parameter info
     */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
