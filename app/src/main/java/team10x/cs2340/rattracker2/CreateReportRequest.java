package team10x.cs2340.rattracker2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class creates the structure that pulls the information 
* from a rat report created by a user in order to add
* the report and its details to the database.
*/
public class CreateReportRequest extends StringRequest {

    private static final String CREATEREPORT_REQUEST_URL = "http://rat-tracker.000webhostapp.com/CreateReport.php";
    private Map<String, String> params;

    // access the database and add all of the rat report's info into another row of the database
    public CreateReportRequest(String date, String locationType, String zip,
                               String address, String city, String borough, String latitude,
                               String longitude, Response.Listener<String> listener) {
        super(Method.POST, CREATEREPORT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("date", date);
        params.put("locationType", locationType);
        params.put("zip", zip);
        params.put("address", address);
        params.put("city", city);
        params.put("borough", borough);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
