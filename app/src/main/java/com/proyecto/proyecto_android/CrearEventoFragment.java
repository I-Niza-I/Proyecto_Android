package com.proyecto.proyecto_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyecto.proyecto_android.databinding.ActivityMapsBinding;

public class CrearEventoFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MyApplication myApplication;

    private EditText nombre, descripcion, direccion, fecha, coste;
    private Spinner ciudad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        nombre = (EditText) view.findViewById(R.id.edt_nombre);
        descripcion = (EditText) view.findViewById(R.id.edt_descripcion);
        direccion = (EditText) view.findViewById(R.id.edt_direccion);
        fecha = (EditText) view.findViewById(R.id.edt_fecha);
        coste = (EditText) view.findViewById(R.id.edt_coste);
        ciudad = (Spinner) view.findViewById(R.id.spn_ciudades);

        // Eventos evento = new Eventos(R.drawable.place_holder, nombre,fecha, direccion, ciudad, coste);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Agrega el marcador, mueve la camara y hace zoom
        LatLng ubicacionEvento = new LatLng(-29.958288, -71.339126);
        mMap.addMarker(new MarkerOptions().position(ubicacionEvento).title("Coquimbo"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionEvento, 40f), 2000, null);
    }

}