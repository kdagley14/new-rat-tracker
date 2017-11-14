package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class confirms that the date range
* the user submitted to filter the map has
* valid credentials.
*/
class MapRequest extends StringRequest {

    private static final String MAP_REQUEST_URL = "http://rat-tracker.000webhostapp.com/Map.php";
    private final Map<String, String> params;

    /**
    * Method accesses the database and check to see
    * if the credentials are in the database.
    *
    * @param start  start date for filter
    * @param end  end date for filter
    * @param listener  response listener object to read user entries
    * @return params  info input into parameters
    */
    public MapRequest(String start, String end, Response.Listener<String> listener) {
        super(Request.Method.POST, MAP_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);

    }

    
    /**
    * Method that returns a map object containing information from paramaters
    *
    * @return params  information previously received for entry
    */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
