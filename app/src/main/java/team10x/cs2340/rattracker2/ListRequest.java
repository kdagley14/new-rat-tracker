package team10x.cs2340.rattracker2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class pulls the first 20 rat reports from
* the database in order to allow them to be 
* displayed on the home screen.
*/
public class ListRequest extends StringRequest {

    private static final String LIST_REQUEST_URL = "http://rat-tracker.000webhostapp.com/List.php";

    /**
    * Method accesses the database and gets the first 20 rat reports
    *
    * @param listener  response listener for strings
    */
    public ListRequest(Response.Listener<String> listener) {
        super(Request.Method.POST, LIST_REQUEST_URL, listener, null);
    }
}
