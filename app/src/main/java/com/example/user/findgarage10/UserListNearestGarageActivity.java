package com.example.user.findgarage10;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserListNearestGarageActivity extends FragmentActivity implements OnMapReadyCallback, GpsLocalisation.IGpsLocalisation, View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ListView listView_garage;
    private MapFragment mapFragment;
    private Position myCurrentlyPosition;
    private TextView label_userConnected;
    private UserDAO userDAO;
    private OfferDAO offerDAO;
    private Position[] garagesKnown;
    private User userConnected;
    private Button btnGoToSendDevis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_nearest_garage);

        initFields();
        getMyPosition();
        handlerClickOnItem();
        getUserConnected();
        initListView();
        garagesKnown = getKnownGarage();

        /*listView_garage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user =   (User) listView_garage.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), user.getNum_user(), Toast.LENGTH_LONG).show();
                goToMyDevis();
            }
        });*/
        btnGoToSendDevis.setOnClickListener(this);
        displayMap();
    }
    public void goToSendDevis(){
        Intent intent = new Intent(this, DetailsGarageActivity.class);
        intent.putExtra("user", userConnected.getNum_user());
        intent.putExtra("garage", 1);
        startActivity(intent);
    }
    public void initFields() {
        listView_garage = (ListView) findViewById(R.id.lv_list_nearest_garage);
        label_userConnected = (TextView) findViewById(R.id.tv_nearest_garage_name_user_connected);
        btnGoToSendDevis = (Button) findViewById(R.id.btn_goToSendDevis);
    }

    private void handlerClickOnItem() {

    }

    public void getUserConnected() {
        userConnected = getIntent().getExtras().getParcelable("user");
        label_userConnected.setText(userConnected.toString());

    }

    private void initListView() {
        userDAO = new UserDAO(this);
        userDAO = userDAO.openReadable();
        User[] listUsers = userDAO.getAllUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listUsers);
        listView_garage.setAdapter(adapter);
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
        getMyPosition();
        //BT 50.8378034, 4.3536477
        if(myCurrentlyPosition == null)
            myCurrentlyPosition = new Position(50.8378034, 4.3536477);
        garagesKnown = getKnownGarage();
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(myCurrentlyPosition.getX(), myCurrentlyPosition.getY())).title("Your are here!"));
        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        /*for (int i = 0; i < garagesKnown.length; i++) {
            //Marker marker =
            map.addMarker(new MarkerOptions().position(new LatLng(garagesKnown[i].getX(), garagesKnown[i].getY())).title("Your are here!"));
            //map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        }*/
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_goToSendDevis :
                goToSendDevis();
                break;
            default:
                break;
        }
    }

    private Position[] getKnownGarage() {
        Position[] garages = new Position[3];
        Position p1 = new Position(50.823315, 4.379876);
        garages[0] = p1;
        p1 = new Position(50.822444, 4.361338);
        garages[1] = p1;
        p1 = new Position(50.818639, 4.307758);
        garages[2] = p1;
        return garages;
    }

    public void setMyMap(GoogleMap map) {
        Position[] myGarage = getKnownGarage();
        for (int i = 0; i < myGarage.length; i++) {
            map.addMarker(new MarkerOptions().position(new LatLng(garagesKnown[i].getX(), garagesKnown[i].getY())).title("Your are here!"));
            //map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        }
    }
}
