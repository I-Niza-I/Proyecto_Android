package com.proyecto.proyecto_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyecto.proyecto_android.databinding.ActivityMapsBinding;

public class DetalleEvento extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String nombre, artista, direccion, ciudad, fecha;
    private int imagen, precio;
    private Button boton;
    private TextView txtNombre, txtArtista, txtDireccion, txtCiudad, txtFecha, txtPrecio;
    private ImageView imvFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_evento);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtArtista = (TextView) findViewById(R.id.txtArtista);
        txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        txtCiudad = (TextView) findViewById(R.id.txtCiudad);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        imvFoto = (ImageView) findViewById(R.id.imvFoto);
        boton = (Button) findViewById(R.id.btnVolver);

        nombre = getIntent().getStringExtra("nombre");
        artista = getIntent().getStringExtra("artista");
        direccion = getIntent().getStringExtra("direccion");
        fecha = getIntent().getStringExtra("fecha");
        ciudad = getIntent().getStringExtra("ciudad");
        precio = getIntent().getIntExtra("precio", 0);
        imagen = getIntent().getIntExtra("imagen", 0);

        txtNombre.setText(nombre);
        txtArtista.setText(artista);
        txtDireccion.setText(direccion);
        txtCiudad.setText(ciudad);
        txtFecha.setText(fecha);
        txtPrecio.setText("$"+precio);
        imvFoto.setImageResource(imagen);

        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(DetalleEvento.this, EventosMusicales.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}