package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AdminActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private List<User> users;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //layout objects
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(AdminActivity.this, mDrawerLayout, R.string.open,
                R.string.closed);
        final ListView lvUsers = findViewById(R.id.lv_users);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            /**
             * This method is just confirming that the item selected
             * in the navigation menu equals the Home Activity selection.
             *
             * @param menuItem  MenuItem being compared
             * @return boolean  true or false value
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                NavigationDrawer nav = new NavigationDrawer();
                nav.getActivityOptions(AdminActivity.this, menuItem);
                return true;
            }
        });

        //generate list from database
        final Response.Listener<String> userListener = new Response.Listener<String>() {

            /**
             * Method uses parameter to identify which object
             * to return from database, and then turn that object
             * into a new JSONArray object.
             *
             * @param response  String used to identify desired JSON object
             */
            @Override
            public void onResponse(String response) {
                try {
                    // get the JSON object returned from the database
                    JSONArray jsonResponse = new JSONArray(response);
                    users = new ArrayList<>();
                    //turn into ratReport objects
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject x = jsonResponse.getJSONObject(i);
                        users.add(new User(x.getString("username"),
                                x.getInt("locked")));
                    }
                    //create a list of the toStrings that just show the date, borough, & zip
                    List<String> testArray = new ArrayList<>();
                    for (User i: users) {
                        testArray.add(i.getUsername());
                    }

                    // This is the array adapter, it takes the context of the activity as a
                    // first parameter, the type of list view as a second parameter and your
                    // array as a third parameter.
                    ArrayAdapter arrayAdapter = new ArrayAdapter(
                            AdminActivity.this,
                            android.R.layout.simple_list_item_1,
                            testArray);

                    lvUsers.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //create request & add to queue
        UserListRequest userRequest = new UserListRequest(userListener);
        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
        queue.add(userRequest);

        //when you click an item, get the primary id of the corresponding ratReport & send to
        //the detailPage activity so it can get the rest of the information from the database;
        //goes to detail page
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Method that makes it so when the user clicks an item,
             * they get the primary id of the corresponding rat report, and
             * send that to the detail page activity so the application can
             * pull the rest of the information from the database
             *
             * @param parent  AdapterView
             * @param view  item View
             * @param position  integer representing object position
             * @param id  long integer representing object id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < lvUsers.getChildCount(); i++) {
                    if(users.get(i).getLockedStatus() == 1) {
                        lvUsers.getChildAt(i).setBackgroundColor(Color.RED);
                    }
                    if(position == i ){
                        if (users.get(i).getLockedStatus() == 1) {
                            lvUsers.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            lvUsers.getChildAt(i).setBackgroundColor(Color.RED);

                        }
                    }
                }
                Response.Listener<String> listener = new Response.Listener<String>() {

                    /**
                     * Pulls rat report object from database and
                     * turns it into a new RatReport object.
                     *
                     * @param response  id of object selected
                     */
                    @Override
                    public void onResponse(String response) {

                    }
                };
                User user = users.get(position);

                if (user.getLockedStatus() == 0) {
                    LockRequest lockRequest = new LockRequest(user.getUsername(), listener);
                    RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
                    queue.add(lockRequest);
                    user.setLockedStatus(1);
                } else {
                    UnlockRequest unlockRequest = new UnlockRequest(user.getUsername(), listener);
                    RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
                    queue.add(unlockRequest);
                    user.setLockedStatus(0);
                }


            }
        });

        Log.d("I AM HERE: ", "" + lvUsers.getChildCount());
        for (int i = 0; i < lvUsers.getChildCount(); i++) {
            Log.d("USER " + i + ": ", "" + users.get(i).toString());
            if(users.get(i).getLockedStatus() == 1) {
                lvUsers.getChildAt(i).setBackgroundColor(Color.RED);
            }

        }

    }

    /**
     * This method is just confirming that the item selected
     * in the navigation menu equals the Home Activity selection.
     *
     * @param item  MenuItem being compared
     * @return boolean  true or false value
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
