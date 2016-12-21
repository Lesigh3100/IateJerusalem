package com.kevin.android.iatejerusalem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RestaurantDistanceList extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    Address mAddress;
    protected boolean mAddressRequested;
    public boolean resultReceived = false;
    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";
    protected String mAddressOutput;
    URL url;
    Button mFetchAddressButton;
    protected TextView addressTextView;
    public int retryAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_distance_list);
        addressTextView = (TextView) findViewById(R.id.current_location_address);
        mResultReceiver = new AddressResultReceiver(new Handler());

        Button mFetchAddressButton = (Button) findViewById(R.id.refresh_button);
        mFetchAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryAsync = 0;
                fetchAddressButtonHandler();
            }
        });

        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();
        fetchAddressButtonHandler();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
            }
        }
    }

    public void fetchAddressButtonHandler() {
        if (mGoogleApiClient.isConnected() && mLastLocation != null) {
            startIntentService();
        }
        mAddressRequested = true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        mResultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }
            if (mAddressRequested) {
                startIntentService();
            }
        }
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            DistanceAsyncTask distanceAsyncTask = new DistanceAsyncTask();
            distanceAsyncTask.execute();
            if (resultCode == Constants.SUCCESS_RESULT) {
                resultReceived = true;
            }
            mAddressRequested = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    private URL buildUrl() {
        URL url = null;
        String currentAddress = mAddressOutput;
        StringBuilder urlString = new StringBuilder();
        urlString.append(getString(R.string.stringbuilder1));
        urlString.append(currentAddress.replaceAll(getString(R.string.space), getString(R.string.plus)));
        urlString.append(getString(R.string.stringbuilder2));
        ArrayList<Restaurant> restaurantDistanceList = buildList();
        String addresses = getString(R.string.delete);

        for (int i = 0; i < restaurantDistanceList.size(); i++) {
            String address = restaurantDistanceList.get(i).getRestaurantAddress();
            if (address.contains(" ") || address.contains(getString(R.string.comma))) {
                address = address.replaceAll(getString(R.string.space), getString(R.string.plus));
                address = address.replaceAll(getString(R.string.comma), getString(R.string.delete));
                address = address + getString(R.string.address_format);
                addresses = addresses + address;
            }
        }
        if (addresses.contains(getString(R.string.comma))) {
            addresses = addresses.replace(getString(R.string.comma), getString(R.string.delete));
        }
        urlString.append(addresses);
        urlString.append(getString(R.string.stringbuilder3));

        try {
            url = new URL(urlString.toString());
        } catch (MalformedURLException exception) {
            Toast.makeText(this, R.string.distance_service_failed, Toast.LENGTH_LONG).show();
            return null;
        }
        return url;
    }


    private class DistanceAsyncTask extends AsyncTask<URL, Void, ArrayList<Restaurant>> {

        @Override
        protected ArrayList<Restaurant> doInBackground(URL... urls) {

            // Create URL object
            URL url = buildUrl();
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = getString(R.string.delete);
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                return null;
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<Restaurant> distanceRestaurantList = extractDistanceFromJson(jsonResponse);

            return distanceRestaurantList;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> distanceRestaurantList) {
            if (distanceRestaurantList == null) {
                return;
            }
            if (distanceRestaurantList.get(0).getRestaurantDistance() == 0 && retryAsync < 3) {
                retryAsync++;
                DistanceAsyncTask distanceAsyncTask = new DistanceAsyncTask();
                distanceAsyncTask.execute();
                return;
            }
            retryAsync = 0;
            Collections.sort(distanceRestaurantList, new Comparator<Restaurant>() {
                @Override
                public int compare(Restaurant o1, Restaurant o2) {
                    if (o1.getRestaurantDistance() > o2.getRestaurantDistance()) {
                        return 1;
                    } else if (o2.getRestaurantDistance() > o1.getRestaurantDistance()) {
                        return -1;
                    }
                    return 0;
                }
            });

            RestaurantDistanceAdapter adapter = new RestaurantDistanceAdapter(RestaurantDistanceList.this, distanceRestaurantList);
            ListView listView = (ListView) findViewById(R.id.restaurant_distance_list);
            listView.setAdapter(adapter);
        }


        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = getString(R.string.delete);
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(getString(R.string.get));
                urlConnection.setReadTimeout(5000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(getString(R.string.utf_8)));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        private ArrayList<Restaurant> extractDistanceFromJson(String distanceJSON) {
            ArrayList<Restaurant> distanceList = buildList();
            try {
                JSONObject baseJsonResponse = new JSONObject(distanceJSON);
                JSONArray rowsArray = baseJsonResponse.optJSONArray(getString(R.string.rows));

                // If there are results in the features array
                if (rowsArray.length() > 0) {
                    JSONObject jsonObject = rowsArray.getJSONObject(0);
                    JSONArray elementsArray = jsonObject.getJSONArray(getString(R.string.elements));
                    if (elementsArray.length() > 0) {

                        for (int j = 0; j < elementsArray.length(); j++) {

                            JSONObject elementsArrayJSONObject = elementsArray.getJSONObject(j);

                            JSONObject distanceObject = elementsArrayJSONObject.optJSONObject(getString(R.string.distance1));
                            if (distanceObject != null) {
                                String distance = distanceObject.optString(getString(R.string.text));
                                String[] parts = distance.split(getString(R.string.space));
                                String part1 = parts[0];
                                Double distanceFormatted = Double.parseDouble(part1);
                                distanceList.get(j).setRestaurantDistance(distanceFormatted);
                            }
                        }
                    }

                }
            } catch (JSONException e) {
                return null;
            }
            return distanceList;
        }
    }

    public ArrayList<Restaurant> buildList() {
        final ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(getString(R.string.a1), getString(R.string.a2), getString(R.string.a3), getString(R.string.a4), getString(R.string.a5), getString(R.string.a6)));
        restaurantList.add(new Restaurant(getString(R.string.b1), getString(R.string.b2), getString(R.string.b3), getString(R.string.b4), getString(R.string.b5), getString(R.string.b6)));
        restaurantList.add(new Restaurant(getString(R.string.c1), getString(R.string.c2), getString(R.string.c3), getString(R.string.c4), getString(R.string.c5), getString(R.string.c6)));
        restaurantList.add(new Restaurant(getString(R.string.d1), getString(R.string.d2), getString(R.string.d3), getString(R.string.d4), getString(R.string.d5), getString(R.string.d6)));
        restaurantList.add(new Restaurant(getString(R.string.e1), getString(R.string.e2), getString(R.string.e3), getString(R.string.e4), getString(R.string.e5), getString(R.string.e6)));
        restaurantList.add(new Restaurant(getString(R.string.f1), getString(R.string.f2), getString(R.string.f3), getString(R.string.f4), getString(R.string.f5), getString(R.string.f6)));
        restaurantList.add(new Restaurant(getString(R.string.g1), getString(R.string.g2), getString(R.string.g3), getString(R.string.g4), getString(R.string.g5), getString(R.string.g6)));
        restaurantList.add(new Restaurant(getString(R.string.h1), getString(R.string.h2), getString(R.string.h3), getString(R.string.h4), getString(R.string.h5), getString(R.string.h6)));
        restaurantList.add(new Restaurant(getString(R.string.i1), getString(R.string.i2), getString(R.string.i3), getString(R.string.i4), getString(R.string.i5), getString(R.string.i6)));
        restaurantList.add(new Restaurant(getString(R.string.j1), getString(R.string.j2), getString(R.string.j3), getString(R.string.j4), getString(R.string.j5), getString(R.string.j6)));
        restaurantList.add(new Restaurant(getString(R.string.k1), getString(R.string.k2), getString(R.string.k3), getString(R.string.k4), getString(R.string.k5), getString(R.string.k6)));
        restaurantList.add(new Restaurant(getString(R.string.l1), getString(R.string.l2), getString(R.string.l3), getString(R.string.l4), getString(R.string.l5), getString(R.string.l6)));
        restaurantList.add(new Restaurant(getString(R.string.m1), getString(R.string.m2), getString(R.string.m3), getString(R.string.m4), getString(R.string.m5), getString(R.string.m6)));
        restaurantList.add(new Restaurant(getString(R.string.n1), getString(R.string.n2), getString(R.string.n3), getString(R.string.n4), getString(R.string.n5), getString(R.string.n6)));
        restaurantList.add(new Restaurant(getString(R.string.o1), getString(R.string.o2), getString(R.string.o3), getString(R.string.o4), getString(R.string.o5), getString(R.string.o6)));
        restaurantList.add(new Restaurant(getString(R.string.p1), getString(R.string.p2), getString(R.string.p3), getString(R.string.p4), getString(R.string.p5), getString(R.string.p6)));
        restaurantList.add(new Restaurant(getString(R.string.q1), getString(R.string.q2), getString(R.string.q3), getString(R.string.q4), getString(R.string.q5), getString(R.string.q6)));
        restaurantList.add(new Restaurant(getString(R.string.r1), getString(R.string.r2), getString(R.string.r3), getString(R.string.r4), getString(R.string.r5), getString(R.string.r6)));
        restaurantList.add(new Restaurant(getString(R.string.s1), getString(R.string.s2), getString(R.string.s3), getString(R.string.s4), getString(R.string.s5), getString(R.string.s6)));
        restaurantList.add(new Restaurant(getString(R.string.t1), getString(R.string.t2), getString(R.string.t3), getString(R.string.t4), getString(R.string.t5), getString(R.string.t6)));
        restaurantList.add(new Restaurant(getString(R.string.u1), getString(R.string.u2), getString(R.string.u3), getString(R.string.u4), getString(R.string.u5), getString(R.string.u6)));
        restaurantList.add(new Restaurant(getString(R.string.v1), getString(R.string.v2), getString(R.string.v3), getString(R.string.v4), getString(R.string.v5), getString(R.string.v6)));
        restaurantList.add(new Restaurant(getString(R.string.w1), getString(R.string.w2), getString(R.string.w3), getString(R.string.w4), getString(R.string.w5), getString(R.string.w6)));
        restaurantList.add(new Restaurant(getString(R.string.x1), getString(R.string.x2), getString(R.string.x3), getString(R.string.x4), getString(R.string.x5), getString(R.string.x6)));
        restaurantList.add(new Restaurant(getString(R.string.y1), getString(R.string.y2), getString(R.string.y3), getString(R.string.y4), getString(R.string.y5), getString(R.string.y6)));
        restaurantList.add(new Restaurant(getString(R.string.z1), getString(R.string.z2), getString(R.string.z3), getString(R.string.z4), getString(R.string.z5), getString(R.string.z6)));
        restaurantList.add(new Restaurant(getString(R.string.aa1), getString(R.string.aa2), getString(R.string.aa3), getString(R.string.aa4), getString(R.string.aa5), getString(R.string.aa6)));
        restaurantList.add(new Restaurant(getString(R.string.ab1), getString(R.string.ab2), getString(R.string.ab3), getString(R.string.ab4), getString(R.string.ab5), getString(R.string.ab6)));
        restaurantList.add(new Restaurant(getString(R.string.ac1), getString(R.string.ac2), getString(R.string.ac3), getString(R.string.ac4), getString(R.string.ac5), getString(R.string.ac6)));
        restaurantList.add(new Restaurant(getString(R.string.ad1), getString(R.string.ad2), getString(R.string.ad3), getString(R.string.ad4), getString(R.string.ad5), getString(R.string.ad6)));
        restaurantList.add(new Restaurant(getString(R.string.ae1), getString(R.string.ae2), getString(R.string.ae3), getString(R.string.ae4), getString(R.string.ae5), getString(R.string.ae6)));
        restaurantList.add(new Restaurant(getString(R.string.af1), getString(R.string.af2), getString(R.string.af3), getString(R.string.af4), getString(R.string.af5), getString(R.string.af6)));
        return restaurantList;
    }
}