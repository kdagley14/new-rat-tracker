package team10x.cs2340.rattracker2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by paige on 10/10/2017.
 *
 * This class creates detailed rat report using 
 * date and location details
 */

class RatReport {

    private String primaryId = "";
    private String date = "";
    private String locationType = "";
    private String zip = "";
    private String address = "";
    private String city = "";
    private String borough = "";
    private String latitude = "";
    private String longitude = "";

    /**
    * Method creates new rat report entry with given information
    * 
    * @param primaryId  string defining rat reports ID
    * @param date  string defining date of rat report
    * @param zip  string defining zipcode of report entry
    * @param borough  string defining neighborhood of rat sigting
    */
    public RatReport(String primaryId, String date, String zip, String borough) {
        this.primaryId = primaryId;
        this.date = date;
        this.zip = zip;
        this.borough = borough;
    }

    /**
    * Method creates new rat report entry with given information
    * 
    * @param primaryId  string defining rat reports ID
    * @param date  string defining date of rat report
    * @param address  string defining street address of report entry
    * @param latitude  string defining global latitude of rat sighting
    * @param longitude  string defining corresponding global longitude of rat sighting
    */
    public RatReport(String primaryId, String date, String address, String latitude, String longitude) {
        this.primaryId = primaryId;
        this.date = date;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
    * Method creates new rat report entry with given information
    * 
    * @param primaryId  string defining rat reports ID
    * @param date  string defining date of rat report
    * @param locationType  string defining type of location the sighting occurred at
    * @param zip  string defining zipcode of rat sigting
    * @param address  string defining street address of rat sighting
    * @param city  string defining the city the rat sighting occured in
    * @param borough  string defining the neighborhood the rat sighting occured in
    * @param latitude  string defining global latitude of rat sighting
    * @param longitude  string defining corresponding global longitude of rat sighting
    */
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

    /**
    * This method returns the date, borough, and zip
    * of rat report in a specific format
    *
    * @return date + "       " + borough + ", " + zip
    */
    public String toListString() {
        return date + "       " + borough + ", " + zip;
    }

    /**
    * This method returns the date of rat report
    * @return date  date of rat report
    */
    public String getDate() {
        return date;
    }

    /**
    * This method returns the address of rat report
    * @return address  street address of rat report
    */
    public String getAddress() {
        return address;
    }

    /**
    * This method puts all of the report details into a string with
    * formatted spacing
    */
    public CharSequence toDetailString() {
        return "Date: \t\t" + date + "\nLocation Type: \t\t" + locationType + "\nZip Code: \t\t"
                + zip + "\nAddress: \t\t" + address + "\nCity: \t\t" + city + "\nBorough: \t\t"
                + borough + "\nLatitude: \t\t" + latitude + "\nLongitude: \t\t" + longitude
                + "\nReference #: \t\t" + primaryId;
    }

    /**
    * Method returns  rat report id
    *
    * @return primaryId  primary id of rat report
    */
    public String getPrimaryId() {
        return primaryId;
    }

    /**
    * Method returns latitude and longitude coordinates of rat report
    *
    * @return LatLng  latitude and longitude values in form of doubles
    */
    public LatLng getLatLong() {
        return new LatLng(Double.parseDouble(this.latitude), Double.parseDouble(this.longitude));
    }
}
