package com.example.user.findgarage10;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserListNearestGarageActivity extends FragmentActivity implements OnMapReadyCallback, GpsLocalisation.IGpsLocalisation {
    private ListView listView_garage;
    private MapFragment mapFragment;
    private Position myCurrentlyPosition;
    private TextView label_userConnected;
    private UserDAO userDAO;
    private User userConnected;
    //private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_nearest_garage);

        initFields();
        handlerClickOnItem();
        getUserConnected();
        initListView();

        //getMyPosition();

        displayMap();
    }

    public void initFields(){
        listView_garage = (ListView)findViewById(R.id.lv_list_nearest_garage);
        label_userConnected = (TextView) findViewById(R.id.tv_nearest_garage_name_user_connected);
    }

    private void handlerClickOnItem() {

    }

    public void getUserConnected(){
        userConnected = this.getIntent().getExtras().getParcelable("user");
        label_userConnected.setText(userConnected.toString());

    }

    private void initListView() {
        userDAO = new UserDAO(this);
        userDAO = userDAO.openReadable();
        User[] listUsers = userDAO.getAllUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listUsers);
        listView_garage.setAdapter(adapter);
    }

    public void displayMap(){
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_container_fragment, mapFragment);
        fragmentTransaction.commit();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getMyPosition(){
        GpsLocalisation gps = new GpsLocalisation();
        gps.setCallback(this);
        gps.demande(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //BT 50.8378034, 4.3536477
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(50.8378034, 4.3536477)).title("Your are here!"));

        //map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        map.setMinZoomPreference(5);
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    @Override
    public void localiser(Position position) {
        //myCurrentlyPosition = position;
        Toast.makeText(this, position.toString(), Toast.LENGTH_LONG).show();

        Log.w("LOCALISATION",position.toString());
    }
}
