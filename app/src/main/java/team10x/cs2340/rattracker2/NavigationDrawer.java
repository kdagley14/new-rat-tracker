package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by kdagley on 11/1/17.
 */

public class NavigationDrawer {

    public static void getActivityOptions(AppCompatActivity currActivity, MenuItem menuItem) {
        final AppCompatActivity current = currActivity;
        switch (menuItem.getItemId()) {
            case (R.id.reports_list):
                Intent reportsListIntent = new Intent(current, HomeActivity.class);
                current.startActivity(reportsListIntent);
                break;
            case (R.id.create_report):
                Intent createReportIntent = new Intent(current, CreateReportActivity.class);
                current.startActivity(createReportIntent);
                break;
            case (R.id.view_map):
                Intent viewMapIntent = new Intent(current, MapActivity.class);
                current.startActivity(viewMapIntent);
                break;
            case (R.id.view_graphs):
                Intent viewGraphsIntent = new Intent(current, GraphActivity.class);
                current.startActivity(viewGraphsIntent);
                break;
            case (R.id.logout):
                Intent logoutIntent = new Intent(current, MainActivity.class);
                current.startActivity(logoutIntent);
        }
    }
}