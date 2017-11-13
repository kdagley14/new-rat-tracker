package team10x.cs2340.rattracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by kdagley on 11/1/17.
 *
 * This class creates the navigation menu
 * found in the top corner of the application
 * along with each individual menu item
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
                Intent viewGraphsIntent = new Intent(current, GraphDateRangeActivity.class);
                current.startActivity(viewGraphsIntent);
                break;
            case (R.id.logout):
                Intent logoutIntent = new Intent(current, MainActivity.class);
                current.startActivity(logoutIntent);
        }
    }
}
