package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class pulls the details from a specific
* rat report entry
*/
public class DetailRequest extends StringRequest {

    private static final String DETAIL_REQUEST_URL = "http://rat-tracker.000webhostapp.com/Detail.php";
    private Map<String, String> params;

    // access the database and send the primary key to get the rest of the data
    public DetailRequest(String primaryId, Response.Listener<String> listener) {
        super(Request.Method.POST, DETAIL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("key", primaryId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
