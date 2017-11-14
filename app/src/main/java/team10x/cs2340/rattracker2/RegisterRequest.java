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

    /**
    * This method accesses the database and adds
    * all of the user's info into another row of the database
    *
    * @param name  user's name
    * @param username  username created by new user
    * @param password  passwrod created by new user
    * @param user_type  type of user
    * @param listener  response listener object to read user's text entries
    */
    RegisterRequest(String name, String username, String password, String user_type,
                    Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("user_type", user_type);

    }

    /**
    * Method that returns previous information in the form
    * of a map object.
    *
    * @return params  map object with user information
    */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
