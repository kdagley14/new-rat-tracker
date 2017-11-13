package team10x.cs2340.rattracker2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by paige on 10/10/2017.
 *
 * This class creates detailed rat report using 
 * date and location details
 */

public class RatReport {

    private String primaryId = "";
    private String date = "";
    private String locationType = "";
    private String zip = "";
    private String address = "";
    private String city = "";
    private String borough = "";
    private String latitude = "";
    private String longitude = "";

    public RatReport(String primaryId, String date, String zip, String borough) {
        this.primaryId = primaryId;
        this.date = date;
        this.zip = zip;
        this.borough = borough;
    }

    public RatReport(String primaryId, String date, String address, String latitude, String longitude) {
        this.primaryId = primaryId;
        this.date = date;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RatReport(String primaryId, String date, String locationType, String zip, String address,
                     String city, String borough, String latitude, String longitude) {
        this.primaryId = primaryId;
        this.date = date;
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toListString() {
        return date + "       " + borough + ", " + zip;
    }

    public String toMapString() {
        return "Reference #: " + primaryId;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String toDetailString() {
        return "Date: \t\t" + date + "\nLocation Type: \t\t" + locationType + "\nZip Code: \t\t"
                + zip + "\nAddress: \t\t" + address + "\nCity: \t\t" + city + "\nBorough: \t\t"
                + borough + "\nLatitude: \t\t" + latitude + "\nLongitude: \t\t" + longitude
                + "\nReference #: \t\t" + primaryId;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public LatLng getLatLong() {
        return new LatLng(Double.parseDouble(this.latitude), Double.parseDouble(this.longitude));
    }
}
