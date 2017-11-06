package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapRequest extends StringRequest {

    private static final String MAP_REQUEST_URL = "http://rat-tracker.000webhostapp.com/Map.php";
    private Map<String, String> params;

    // access the database and check to see if the credentials are in the database
    public MapRequest(String start, String end, Response.Listener<String> listener) {
        super(Request.Method.POST, MAP_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}