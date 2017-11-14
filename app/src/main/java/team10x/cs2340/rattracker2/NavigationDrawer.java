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
class NavigationDrawer {

    /**
    * Method sets each available activity within the application
    * to a seperate menu item
    * @param currActivity  current activity state of application
    * @param menuItem  new menu item object 
    */
    public static void getActivityOptions(AppCompatActivity currActivity, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.reports_list):
                Intent reportsListIntent = new Intent(currActivity, HomeActivity.class);
                currActivity.startActivity(reportsListIntent);
                break;
            case (R.id.create_report):
                Intent createReportIntent = new Intent(currActivity, CreateReportActivity.class);
                currActivity.startActivity(createReportIntent);
                break;
            case (R.id.view_map):
                Intent viewMapIntent = new Intent(currActivity, MapActivity.class);
                currActivity.startActivity(viewMapIntent);
                break;
            case (R.id.view_graphs):
                Intent viewGraphsIntent = new Intent(currActivity, GraphDateRangeActivity.class);
                currActivity.startActivity(viewGraphsIntent);
                break;
            case (R.id.logout):
                Intent logoutIntent = new Intent(currActivity, MainActivity.class);
                currActivity.startActivity(logoutIntent);
        }
    }
}
