package team10x.cs2340.rattracker2;

/**
 * Created by paige on 10/10/2017.
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

    public String toDetailString() {
        return "Date: " + date + "\nLocation Type: " + locationType + "\nZip Code: " + zip
                + "\nAddress: " + address + "\nCity: " + city + "\nBorough: " + borough
                + "\nLatitude: " + latitude + "\nLongitude: " + longitude;
    }

    public String getPrimaryId() {
        return primaryId;
    }

}