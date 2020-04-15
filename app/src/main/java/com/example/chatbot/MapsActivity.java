package com.example.chatbot;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Geocoder coder ;

    LocationManager locationManager;
    Location location;

    String a;

    JSONObject jo , jData , j;
    JSONArray jArray1 , jArray2;

    public ArrayList<LatLng> ll;

    DistrictData dd[];
    LatLng[] latLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        DataRetrieval dR = new DataRetrieval();
        try {
            a = dR.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (a == null)
            Toast.makeText(this,"String is empty" , Toast.LENGTH_SHORT).show();

        try {
            jArray1 = new JSONArray(a);
        } catch (JSONException e) {
            Log.i("JSONException" , "IN jArray1");
        }

        dd = new DistrictData[500];
        latLngs = new LatLng[500];

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        location = getLastKnownLocation();
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        coder = new Geocoder(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker on my current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
        //mMap.animateCamera(CameraUpdateFactory.zoomIn());



        AddrRetrieval aR = new AddrRetrieval();
        aR.execute();


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

    public class DataRetrieval extends AsyncTask<String , String , String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection con = null;

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

            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            //System.out.println(s);
        }
    }

    public class AddrRetrieval extends AsyncTask<String ,String , DistrictData[]>{

        int k=0;
        ProgressDialog progressDialog;
        List<Address> addr = null;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MapsActivity.this,
                    "ProgressDialog",
                    "Wait for data processing");
        }


        @Override
        protected DistrictData[] doInBackground(String... strings) {

            String state = "";


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
                    Toast.makeText(MapsActivity.this , "WHY THE FUCK ITS NULL" , Toast.LENGTH_SHORT).show();
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
                            String addrs = jo.getString("district") + "," + state;

                            //Log.i("Data processing" , "Address: " + addr + ", " + "i:" + i + ", " + "j: " + j);

                            try {
                                addr = coder.getFromLocationName(addrs , 5);
                            } catch (IOException e) {
                                Log.i("IOException" , "Error in onPostExecute method while using coder");
                            }

                            LatLng latLng;

                            if (addr != null && addr.size() > 0){
                                latLng = new LatLng(addr.get(0).getLatitude() , addr.get(0).getLongitude());
                                dd[k++] = new DistrictData(addrs , jo.getInt("confirmed") , latLng);
                            }else{
                                continue;
                            }

                            //latLngs[k++] = latLng;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            return dd;
        }

        @Override
        protected void onPostExecute(DistrictData[] districtData) {

            progressDialog.dismiss();



            /*for (int i=0 ; i<k ; i++){
                Log.i("District Data" , "District: " + dd[i].getAddr() + " Confirmed: "+dd[i].getTnc());
            }

            LatLng MELBOURNE = new LatLng(-37.813, 144.962);
            mMap.addMarker(new MarkerOptions()
                            .position(MELBOURNE)
                            .title("Melbourne"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));*/


            for (int i=0 ; i<k ; i++){

                Log.i("LOOP COUNTER" , i + "");
                mMap.addMarker(new MarkerOptions().position(dd[i].getLatLng()).title("District: "+dd[i].getAddr())).setSnippet( "Confirmed:" + dd[i].getTnc());
            }
        }
    }
}
