package team10x.cs2340.rattracker2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
* This class accesses the database containing registered 
* users and adds the new users registration information
* into a new row
*/
class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL =
            "http://rat-tracker.000webhostapp.com/Register.php";
    private final Map<String, String> params;

    // access the database and add all of the user's info into another row of the database
    public RegisterRequest(String name, String username, String password, String user_type,
                           Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("user_type", user_type);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
