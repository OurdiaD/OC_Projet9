package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.model.Address;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars int
     * @return euros int
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euros){
        return (int) Math.round(euros / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return date string
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    public static String getTodayDatewHour(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyykkmmss");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context Context
     * @return boolean
     */
    public static MutableLiveData<Boolean> isInternetAvailable(Context context) {
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(false);
        Executors.newSingleThreadExecutor().execute(() -> {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                try {
                    URL url = new URL("https://google.com");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.connect();
                    success.postValue(connection.getResponseCode() == 200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        return success;
    }

    public static String getLocalisation(Context context, Address address) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        String addressString = getAddressToString(address);
        List<android.location.Address> pos = geocoder.getFromLocationName(addressString, 1);
        if(pos.size() > 0)
            return pos.get(0).getLatitude() + "," + pos.get(0).getLongitude();
        else
            return null;
    }

    public static String getAddressToString(Address address) {
        return address.getNumber() +  " " +
                address.getStreet() + " " +
                address.getPostCode() + " " +
                address.getCity();

    }
}
