package com.example.light.ramdanapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    EditText searchText;
    FloatingActionButton confirmBtn;
    Button locateBtn, infoBtn;
    boolean selected;
    LatLng selectedPos;
    String mail;
    Marker marker;
    String previous;
    String name, phone, number, ccp;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mail = (String) getIntent().getSerializableExtra("email");
        previous = (String) getIntent().getSerializableExtra("previous");
        name = (String) getIntent().getSerializableExtra("name");
        phone = (String) getIntent().getSerializableExtra("phone");
        number = (String) getIntent().getSerializableExtra("number");
        ccp = (String) getIntent().getSerializableExtra("ccp");


        selected = false;


        searchText = findViewById(R.id.input_search);
        searchText.setSingleLine();



        infoBtn = findViewById(R.id.info);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previous.equals("other")){
                    if (marker != null){
                        Intent intent = new Intent(MapActivity.this, ListActivityOther.class);
                        String email = marker.getSnippet().split("\n")[0];
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                }


            }
        });

        locateBtn = findViewById(R.id.locate);
        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        confirmBtn = findViewById(R.id.confirm);

        if (previous.equals("info") || previous.equals("change")){
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected){
                        Toast.makeText(MapActivity.this, "تم اختيار الموقع بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent;

                        if (previous.equals("info")){
                            intent = new Intent(MapActivity.this, InfoActivity.class);
                            intent.putExtra("previous", "map");
                            intent.putExtra("email", mail);
                            intent.putExtra("name", name);
                            intent.putExtra("phone", phone);
                            intent.putExtra("number", number);
                            intent.putExtra("ccp", ccp);

                            String position = String.valueOf(selectedPos.latitude)+" "+String.valueOf(selectedPos.longitude);
                            //position = position.replace('.', ',');
                            intent.putExtra("position", position);

                        }else{
                            intent = new Intent(MapActivity.this, MainJAm3iyaActivity.class);
                            intent.putExtra("previous", "map");
                        }

                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MapActivity.this, "الرجاء اختيار الموقع", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, ChooseActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }



        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!searchText.getText().equals("")){
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                        geoLocate();
                    }
                }
                return false;
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void geoLocate(){
        String searchString = searchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list != null && list.size() != 0){
            Address address = list.get(0);

            LatLng searchLocation = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(searchLocation));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    private void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task location =  mFusedLocationClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Location currentLocation = (Location)task.getResult();

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View mWindow = LayoutInflater.from(MapActivity.this).inflate(R.layout.marker_info, null);
                String title = marker.getTitle();
                TextView tvTitle = mWindow.findViewById(R.id.title);

                if (!title.equals("")){
                    tvTitle.setText(title);
                }

                String snippet = marker.getSnippet();

                TextView email = mWindow.findViewById(R.id.email);
                TextView phone = mWindow.findViewById(R.id.phone);
                TextView number = mWindow.findViewById(R.id.number);
                TextView ccp = mWindow.findViewById(R.id.ccp);

                if (!snippet.equals("")){
                    String semail = snippet.split("\n")[0];
                    String sphone = snippet.split("\n")[1];
                    String snumber = snippet.split("\n")[2];
                    String sccp = snippet.split("\n")[3];

                    email.setText(semail);
                    phone.setText(sphone);
                    number.setText(snumber);
                    ccp.setText(sccp);

                }
                return mWindow;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });


        mMap.setOnMarkerClickListener(this);
        if (previous.equals("client") || previous.equals("other")){
            // Add a markers
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();


            final DatabaseReference JamsInfo = myRef.child("Jam3iyates");
            JamsInfo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataS : dataSnapshot.getChildren()){
                        String position = dataS.child("position").getValue(String.class);

                        double lat = Double.parseDouble(position.split(" ")[0]);
                        double lng = Double.parseDouble(position.split(" ")[1]);
                        final LatLng latLng = new LatLng(lat, lng);

                        String snippet = dataS.getKey() + "\n"+
                                dataS.child("phone").getValue(String.class) + "\n"+
                                dataS.child("number").getValue(String.class) + "\n"+
                                dataS.child("ccp").getValue(String.class);

                        if (dataS.child("ccp").getValue(String.class).equals("")){
                            snippet += "--";
                        }

                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).
                                title(dataS.child("name").getValue(String.class) + "اسم الجمعية : ").snippet(snippet);

                        mMap.addMarker(markerOptions);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        boolean per1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean per2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (per1 && per2) {
            ActivityCompat.requestPermissions(this, permissions, 1234);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        getDeviceLocation();

        if (previous.equals("info") || previous.equals("change")){
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (selected){
                        mMap.clear();
                    }
                    selected = true;
                    selectedPos = latLng;


                    String snippet = mail + "\n"+
                            phone + "\n"+
                            number + "\n"+
                            ccp;

                    if (ccp.equals("")){
                        snippet += "--";
                    }

                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).
                            title(name + "اسم الجمعية : ").snippet(snippet);

                    marker = mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPos));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                }
            }
        }

        if (permissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        this.marker = marker;
        return false;
    }
}
