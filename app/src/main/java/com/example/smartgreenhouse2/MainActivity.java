package com.example.smartgreenhouse2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private final String apiKey="BLRhKP7sm10AXrIdYHIvGSooyF4xjEeb";

    //Location
    LocationManager locationManager;
    LocationListener locationListener;

    MainActivity activity;
    Context context;

    String CurrentWeather,CurrentTemperatureValue;
    URL result;
    double longitude;
    double latitude;

    boolean gps_enabled, network_enabled;

    //Firebase
    DatabaseReference dbRef;

    private TextView optStatusAtap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        //Permission
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,100,this);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        //Get Firebase Instance
        dbRef = FirebaseDatabase.getInstance().getReference("SensorHujan");

        optStatusAtap = findViewById(R.id.ll3_card1_tv_optAtap);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getStatusAtap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tb_btn_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"logout berhasil",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getStatusAtap(){
        //Get Value From Database
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer value = snapshot.getValue(Integer.class);

                assert value != null;
                if (value.equals(1)){
                    optStatusAtap.setText("Terbuka");
                } else if (value.equals(0)){
                    optStatusAtap.setText("Tertutup");
                } else {
                    optStatusAtap.setText("Gagal Dimuat");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Gagal retrieve status hujan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        AsyncTask asyncTask = new AsyncTask();

        Toast.makeText(this, "lat" + latitude + "long" + longitude, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class AsyncTask extends android.os.AsyncTask<Void, Void, String>{
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Menunggu");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Memuat Data...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String urll = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey="+apiKey+"&q=" +latitude+"%2C"+longitude;
            URLConnection urlConnection = new URLConnection();
            result = urlConnection.getURL(urll);

        }
    }
}