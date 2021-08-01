package com.example.osmtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private CompassOverlay mCompassOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Marker> markers;

    private HashMap<String, Object> hashMap;
    private ArrayList<HashMap<String, Object>> spinnerList;
    private Dialog dialog;


    // stores the image database icons
    private static Integer[] imageIconDatabase = { R.drawable.group_13, R.drawable.content9};

    // stores the image database names
    private String[] imageNameDatabase = { "radar", "catmouse"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.addNewMarker);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewMarker();
            }
        });

        markers = new ArrayList<>();

        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        GeoPoint startPoint = new GeoPoint(50.1234, 47.1234);
        mapController.setCenter(startPoint);
        mapController.setZoom(20.5);


        addCompassOverlay();
//        enableGridLineOverlay();
        enableRotationGestures();
//        addMapScaleBarOverlay();

        initializeImageList();
        createAddDialog();

    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void addCompassOverlay(){

        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);

    }

    public void enableGridLineOverlay(){

        LatLonGridlineOverlay2 overlay = new LatLonGridlineOverlay2();
        map.getOverlays().add(overlay);

    }

    public void enableRotationGestures(){

        mRotationGestureOverlay = new RotationGestureOverlay(this, map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(this.mRotationGestureOverlay);

    }

    public void addMapScaleBarOverlay(){

        final Context context = this;
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
//play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void addNewMarker(){

        GeoPoint geoPoint = new GeoPoint(50.1234, 47.1234);
        Marker startMarker = new Marker(map);
        startMarker.setPosition(geoPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        startMarker.setIcon(getResources().getDrawable(R.drawable.group_344));
        startMarker.getIcon().setAutoMirrored(true);
        startMarker.setTitle("Start point");
        startMarker.setDraggable(true);
        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {

                marker.showInfoWindow();

                return false;
            }
        });
        startMarker.setInfoWindow(new CustomBubble(R.layout.custom_bubble_view, map));
        markers.add(startMarker);
        map.getOverlays().add(startMarker);

    }

    public void createAddDialog() {
        // TODO
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.custom_layout);
        dialog = new Dialog(this);
        dialog.setTitle("title");
        dialog.setContentView(R.layout.custom_layout);
        dialog.setCancelable(false);

        Spinner spin = (Spinner) dialog.findViewById(R.id.spinner);
        dialog.show();
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, spinnerList, R.layout.spinner_view, new String[] { "Name", "Icon" }, new int[] { R.id.imageNameSpinner, R.id.imageIconSpinner });
        spin.setAdapter(adapter);
    }

    private void initializeImageList() {
        spinnerList = new ArrayList<>();
        // TODO Auto-generated method stub
        for (int i = 0; i < imageNameDatabase.length; i++) {
            hashMap = new HashMap<String, Object>();

            hashMap.put("Name", imageNameDatabase[i]);
            hashMap.put("Icon", imageIconDatabase[i]);
            spinnerList.add(hashMap);
        }
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource((Integer) spinnerList.get(0).get("Icon"));
        spinnerList.get(0).get("Name");
    }

}