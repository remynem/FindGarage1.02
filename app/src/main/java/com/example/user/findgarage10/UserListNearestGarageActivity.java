package com.example.user.findgarage10;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.findgarage10.db.GarageDAO;
import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListNearestGarageActivity extends FragmentActivity implements OnMapReadyCallback, GpsLocalisation.IGpsLocalisation{
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ListView listView_garage;
    private MapFragment mapFragment;
    private Position myCurrentlyPosition;
    private TextView label_userConnected;
    private UserDAO userDAO;
    private GarageDAO garageDAO;
    private Button btn_goToMyDevis;
    private Map<String, Position> garagesKnown;
    private User userConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_nearest_garage);

        initFields();
        getMyPosition();
        getUserConnected();
        initListView();

        garagesKnown = getKnownGarage();

        listView_garage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomGarage =   (String) listView_garage.getItemAtPosition(i);
                goToSendDevis(nomGarage);
            }
        });
        displayMap();
        btn_goToMyDevis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMyDevis();
            }
        });
    }

    private void goToMyDevis() {
        Intent intent = new Intent(this, UserMyDevisActivity.class);
        intent.putExtra("user", userConnected);
        startActivity(intent);
    }

    public void goToSendDevis(String nomGarage){
        garageDAO = new GarageDAO(this);
        garageDAO = garageDAO.openReadable();
        Garage garageTarget = garageDAO.getGarageByName(nomGarage);

        Intent intent = new Intent(this, DetailsGarageActivity.class);
        intent.putExtra("user", userConnected);
        intent.putExtra("garage", garageTarget);
        startActivity(intent);
    }
    public void initFields() {
        listView_garage = (ListView) findViewById(R.id.lv_list_nearest_garage);
        label_userConnected = (TextView) findViewById(R.id.tv_nearest_garage_name_user_connected);
        btn_goToMyDevis = (Button) findViewById(R.id.btn_goToMyDevis);
    }

    public void getUserConnected() {
        userConnected = getIntent().getExtras().getParcelable("user");
        label_userConnected.setText(userConnected.toString());

    }

    private void initListView() {
        garagesKnown = getKnownGarage();
        List<String> garages = new ArrayList<>();
        for (Map.Entry entry : garagesKnown.entrySet()){
            garages.add((String) entry.getKey());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, garages);
        listView_garage.setAdapter(adapter1);
    }

    public void displayMap() {
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_container_fragment, mapFragment);
        fragmentTransaction.commit();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getMyPosition() {
        GpsLocalisation gps = new GpsLocalisation();
        gps.setCallback(this);
        gps.demande(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.clear();
        setMyMap(map);
    }

    @Override
    public void localiser(Position position) {
        myCurrentlyPosition = new Position(position.getX(), position.getY());
        Log.i("LOCALISATION",position.toString());
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyPosition();
                } else {
                }

            }
        }
    }

    private Map<String, Position> getKnownGarage() {
        Map<String, Position> knownGarages = new HashMap<>();
        garageDAO = new GarageDAO(this);

        /*garageDAO = garageDAO.openWritable();
        garageDAO.initTableGarage();*/

        garageDAO = garageDAO.openReadable();
        Garage[] listGarages = garageDAO.getAllGarages();

        for(int i = 0; i < listGarages.length; i++){
            List<Address> adresse = new ArrayList<>();
            Geocoder geocoder = new Geocoder(this);
            try {
                adresse = geocoder.getFromLocationName(listGarages[i].getAdress_garage(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(adresse.size() > 0){
                Position position = new Position(adresse.get(0).getLongitude(), adresse.get(0).getLatitude());
                knownGarages.put(listGarages[i].getName_garage(), position);
            }
        }
        return knownGarages;
    }

    public void setMyMap(GoogleMap map) {
        map.clear();
        garagesKnown = getKnownGarage();

        LatLngBounds.Builder bld = new LatLngBounds.Builder();
        List<Marker> mMarkers = new ArrayList<>();
        for(Map.Entry entry : garagesKnown.entrySet()){
            Position p = (Position) entry.getValue();
            mMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(p.getX(), p.getY())).title((String)entry.getKey())));
            bld.include(new LatLng(p.getX(), p.getY()));
        }
        LatLngBounds bounds =  bld.build();
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100, 100,0));

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        map.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);
    }
}
