package com.lugudu.marketplanner.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;

import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.FragmentHomeBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng miPosicion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initPlacesClient();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng currentLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Mi Ubicación"));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f));

                    getNearbySupermarkets();
                } else {
                    Toast.makeText(requireContext(), "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initPlacesClient() {
        String apiKey = getString(R.string.google_maps_key); // Reemplaza con tu propia clave de API de Google Maps
        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(), apiKey);
        }
    }

    private void getNearbySupermarkets() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();

        PlacesClient placesClient = Places.createClient(this.getContext());

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FindCurrentPlaceResponse response = task.getResult();
                if (response != null) {
                    List<PlaceLikelihood> placeLikelihoods = response.getPlaceLikelihoods();
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        Place place = placeLikelihood.getPlace();
                        LatLng location = place.getLatLng();

                        // Filtrar los supermercados de comida
                        List<Integer> supermarketTypes = Arrays.asList(
                                129 //Place.Type.SUPERMARKET,
                                //55, //Place.Type.GROCERY_OR_SUPERMARKET,
                                //49 //Place.Type.FOOD
                        );
                        List<Place.Type> placeTypes = place.getTypes();
                        if (placeTypes != null && !Collections.disjoint(placeTypes, supermarketTypes)) {
                            mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
                        }}
                }
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("TAG", "Error al obtener lugares cercanos: " + exception.getMessage());
                }
            }
        });
    }
}




