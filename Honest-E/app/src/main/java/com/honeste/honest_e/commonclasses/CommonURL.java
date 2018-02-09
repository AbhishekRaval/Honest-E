package com.honeste.honest_e.commonclasses;

/**
 * Created by abhis on 09-Mar-17.
 */

public class CommonURL {

    String ipAddress = "http://honesteservices.abhishekraval.com/";


    public  String getIP(String file)

    {

        return  ipAddress + "WebServices/"+file;

    }

    public String getPathIp(String file)
    {
        return  ipAddress + "WebServices/Images/complaintimages/"+file+".jpg";
    }

    public String getProfilePathdetailIP(String file)
    {
        return  ipAddress + "WebServices/Images/profileimages/"+file+".jpg";
    }
}
