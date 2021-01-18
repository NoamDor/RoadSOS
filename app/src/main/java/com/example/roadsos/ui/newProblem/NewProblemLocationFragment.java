package com.example.roadsos.ui.newProblem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.roadsos.R;
import com.example.roadsos.model.ProblemType;
import com.example.roadsos.ui.newProblem.NewProblemDetailsFragmentArgs;
import com.example.roadsos.ui.newProblem.NewProblemLocationFragmentDirections;
import com.example.roadsos.utils.PermissionUtils;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewProblemLocationFragment extends Fragment implements OnMapReadyCallback {

    View view;
    private GoogleMap map;
    private static final int DEFAULT_ZOOM = 15;
    private Location currentLocation;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_problem_location, container, false);
        ProblemType problemType = NewProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblemType();

        Button continueBtn = view.findViewById(R.id.new_problem_location_continue_btn);
        continueBtn.setOnClickListener(b -> {
            NavDirections direction = NewProblemLocationFragmentDirections.actionNewProblemLocationFragmentToNewProblemDetailsFragment(problemType);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction);
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getCurrentLocation();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(getActivity(), "The app need your permission to display your current location", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            currentLocation = location;
                            moveMap(location);
                        }
                    });
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void moveMap(Location location) {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        if (map != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            map.getUiSettings().setZoomControlsEnabled(true);
            map.addMarker(new MarkerOptions().position(latLng));
        }
    }
}