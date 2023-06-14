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

        // Inicializar el cliente de Places API
        initPlacesClient();

        // Inicializar el proveedor de ubicación fusionada
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Obtener el fragmento del mapa y registrar el callback
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);


        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Verificar los permisos de ubicación
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no están concedidos, solicitarlos al usuario
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        // Mostrar el botón de mi ubicación y habilitar la capa de tráfico
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);

        // Obtener la ubicación actual del dispositivo
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Obtener las coordenadas de la ubicación actual
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Crear un marcador para la ubicación actual
                    LatLng currentLocation = new LatLng(latitude, longitude);
                    miPosicion = currentLocation;
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Mi Ubicación"));

                    // Mover la cámara al marcador de la ubicación actual
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f));
                } else {
                    Toast.makeText(requireContext(), "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Obtener la ubicación actual del dispositivo
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Obtener las coordenadas de la ubicación actual
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Crear un marcador para la ubicación actual
                    LatLng currentLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Mi Ubicación"));

                    // Mover la cámara al marcador de la ubicación actual
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f));

                    // Obtener los supermercados cercanos y agregar marcadores en el mapa
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


    // Inicializar el cliente de Places API
    private void initPlacesClient() {
        String apiKey = getString(R.string.google_maps_key); // Reemplaza con tu propia clave de API de Google Maps
        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(), apiKey);
        }
    }

    // Obtener los supermercados cercanos a la ubicación actual
    private void getNearbySupermarkets() {
        // Crear una solicitud para encontrar los lugares cercanos a la ubicación actual
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();

        // Obtener el cliente de Places API
        PlacesClient placesClient = Places.createClient(this.getContext());

        // Hacer la solicitud para encontrar los lugares cercanos
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FindCurrentPlaceResponse response = task.getResult();
                if (response != null) {
                    // Obtener los lugares encontrados
                    List<PlaceLikelihood> placeLikelihoods = response.getPlaceLikelihoods();
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        Place place = placeLikelihood.getPlace();
                        LatLng location = place.getLatLng();

                        // Filtrar los supermercados de comida
                        List<Integer> supermarketTypes = Arrays.asList(
                                129, //Place.Type.SUPERMARKET,
                                55, //Place.Type.GROCERY_OR_SUPERMARKET,
                                49 //Place.Type.FOOD
                        );
                        List<Place.Type> placeTypes = place.getTypes();
                        if (placeTypes != null && !Collections.disjoint(placeTypes, supermarketTypes)) {
                            // Agregar un marcador en el mapa para cada lugar encontrado
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




