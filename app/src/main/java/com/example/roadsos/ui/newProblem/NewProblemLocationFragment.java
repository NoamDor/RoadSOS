package com.example.roadsos.ui.newProblem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadsos.R;
import com.example.roadsos.model.MyLocation;
import com.example.roadsos.model.ProblemType;
import com.example.roadsos.utils.PermissionUtils;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewProblemLocationFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private MyLocation currentLocation;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LiveData<List<Address>> addresses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_problem_location, container, false);
        ProblemType problemType = NewProblemLocationFragmentArgs.fromBundle(getArguments()).getProblemType();

        Button continueBtn = view.findViewById(R.id.new_problem_location_continue_btn);
        continueBtn.setOnClickListener(b -> {
            NavDirections direction = NewProblemLocationFragmentDirections
                    .actionNewProblemLocationFragmentToNewProblemDetailsFragment(problemType, currentLocation);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction);
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.new_problem_location_map);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(latLng -> {
            moveMap(latLng.latitude, latLng.longitude);
            getAddress(latLng.latitude, latLng.longitude, new Listener<List<Address>>() {
                @Override
                public void onComplete(List<Address> data) {
                    String address = data.get(0).getAddressLine(0);
                    setTextAddress(address);
                    currentLocation = new MyLocation(latLng.latitude, latLng.longitude, address);
                }
            });
        });

        setCurrentLocation();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    setCurrentLocation();
                } else {
                    Toast.makeText(getActivity(), "The app need your permission to display your current location", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void setCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            moveMap(latitude, longitude);
                            getAddress(latitude, longitude, new Listener<List<Address>>() {
                                @Override
                                public void onComplete(List<Address> data) {
                                    String address = data.get(0).getAddressLine(0);
                                    setTextAddress(address);
                                    currentLocation = new MyLocation(latitude, longitude, address);
                                }
                            });
                        }
                    });
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getAddress(double latitude, double longitude, Listener<List<Address>> listener) {
        Geocoder geocoder;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            listener.onComplete(addresses);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTextAddress(String address) {
        TextView addressTv = view.findViewById(R.id.new_problem_location_address_tv);
        addressTv.setText(address);
    }

    private void moveMap(double latitude, double longitude) {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        if (map != null) {
            LatLng latLng = new LatLng(latitude, longitude);
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
            map.getUiSettings().setZoomControlsEnabled(true);
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng));
        }
    }

    private interface Listener<T> {
        void onComplete(T data);
    }
}