package com.proyecto.proyecto_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyecto.proyecto_android.databinding.ActivityMapsBinding;

public class DetalleEventoFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String nombre, artista, direccion, ciudad, fecha;
    private int imagen, precio;
    private Button boton;
    private TextView txtNombre, txtArtista, txtDireccion, txtCiudad, txtFecha, txtPrecio;
    private ImageView imvFoto;
    private Double latitud;
    private Double longitud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_evento, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");
            artista = getArguments().getString("artista");
            direccion = getArguments().getString("direccion");
            fecha = getArguments().getString("fecha");
            ciudad = getArguments().getString("ciudad");
            precio = getArguments().getInt("precio", 0);
            imagen = getArguments().getInt("imagen", 0);
            latitud = getArguments().getDouble("latitud", 0);
            longitud = getArguments().getDouble("longitud", 0);
        }

        // Inicializar vistas
        txtNombre = view.findViewById(R.id.txtNombre);
        txtArtista = view.findViewById(R.id.txtArtista);
        txtDireccion = view.findViewById(R.id.txtDireccion);
        txtCiudad = view.findViewById(R.id.txtCiudad);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        imvFoto = view.findViewById(R.id.imvFoto);

        // Configurar datos
        txtNombre.setText(nombre);
        txtArtista.setText(artista);
        txtDireccion.setText(direccion);
        txtCiudad.setText(ciudad);
        txtFecha.setText(fecha);
        txtPrecio.setText("$" + precio);
        imvFoto.setImageResource(imagen);

        txtNombre.setText(nombre);
        txtArtista.setText(artista);
        txtDireccion.setText(direccion);
        txtCiudad.setText(ciudad);
        txtFecha.setText(fecha);
        txtPrecio.setText("$"+precio);
        imvFoto.setImageResource(imagen);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ubicacionEvento = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubicacionEvento).title("Marcador de: "+direccion));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionEvento, 15f), 2000, null);
    }
}