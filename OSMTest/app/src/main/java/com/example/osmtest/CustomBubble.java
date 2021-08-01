package com.example.osmtest;

import android.view.Window;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class CustomBubble extends MarkerInfoWindow {
    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * @param mapView
     */


    public CustomBubble(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }


    @Override
    public void onOpen(Object item) {


    }
}
