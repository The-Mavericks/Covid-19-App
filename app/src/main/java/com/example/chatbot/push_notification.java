package com.example.chatbot;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class push_notification extends IntentService {

    JSONObject jo , jData , j;
    JSONArray jArray1 , jArray2;

    Geocoder geocoder;
    List<Address> addresses , addr;
    Location location;

    String myAddr = "" , state = "";
    int  k=0;
    String addrs;

    int flag = 0;

    public push_notification() {
        super("push_notification");
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("Info","Reached Here by NSP");

        geocoder = new Geocoder(this,Locale.getDefault());

        location = getLastKnownLocation();
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAddr = addresses.get(0).getLocality() + "," + addresses.get(0).getAdminArea();

        String urlStr = "https://api.covid19india.org/v2/state_district_wise.json";
        URL url = null;
        try {
            url = new URL(urlStr);
                /*con = new HttpURLConnection(url) {
                    @Override
                    public void disconnect() {

                    }

                    @Override
                    public boolean usingProxy() {
                        return false;
                    }

                    @Override
                    public void connect() throws IOException {

                    }
                };*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                Log.i("JSONDATA" , sb.toString());
            }
        }
        catch (MalformedURLException e) {
            Log.i("MalformedURL" , "Url not correct");
            try {
                throw new MalformedURLException("URL is malformed!!");
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            Log.i("IOException" , "While reading json");
        }

        String a = sb.toString();

        try {
            jArray1 = new JSONArray(a);
        } catch (JSONException e) {
            Log.i("INFO" , "ERROR in push_notification in jArray1");
        }

        for (int i=0 ; i<jArray1.length() ; i++){
            try {
                j = new JSONObject(jArray1.getJSONObject(i).toString());
            } catch (JSONException e) {
                Log.i("JSONException", "Error at outer loop in AddrRetrieval");
            }

            try {
                if (j.getString("state").isEmpty() != true)
                    state = j.getString("state");
                else
                    continue;
            } catch (JSONException e) {
                Log.i("JSONException" , "Error in getting state");
            }

            try {
                jArray2 = new JSONArray(j.getJSONArray("districtData").toString());
            } catch (JSONException e) {
                Log.i("JSONException" , "Error in getting district data json array");
            }

            if (jArray2 == null){
                Log.i("NULL error" , "jArray2 is null");
            }

            for (int j=0 ; j < jArray2.length() ; j++){

                Log.i("LOOP COUNTER" , k + "");

                try {
                    jo = new JSONObject(jArray2.getJSONObject(j).toString());
                } catch (JSONException e) {
                    Log.i("JSONException" , "Error in getting json object of district");
                }

                try {
                    if (jo.getString("district").isEmpty() != true){
                        addrs = jo.getString("district") + "," + state;
                    }

                    if (myAddr.equals(addrs) == true){
                        createNotificationChannel();
                        notification(myAddr , jo.getInt("confirmed"));
                        flag =1;
                        break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flag == 0){
            createNotificationChannel();
            notification(myAddr , 0);
            flag =0;
        }

    }

    private void createNotificationChannel() {
        Log.i("Info","Reached notification channel");
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyEvent", "MyEvent", importance);
            //channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notification(String myAddr, int confirmed) {
        Log.i("Info","Reached notification");
        Intent intent = new Intent(this, MapsActivity.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(MainActivity.class);
        //stackBuilder.addNextIntent(intent);
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, 0);
        //PendingIntent pendingintent = stackBuilder.getPendingIntent(0 , PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyEvent")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentTitle(myAddr)
                .setContentText("Confirmed:" + confirmed)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Confirmed:" + confirmed))
                .setContentIntent(pendingintent);;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(12, builder.build());
    }

    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
