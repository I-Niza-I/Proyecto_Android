package com.proyecto.proyecto_android;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private MyApplication myApplication;
    private String nombre, artista,descripcion, direccion, ciudad, fecha;
    private int imagen, precio;
    private Button botonAgregar;
    private TextView txtNombre, txtArtista, txtDireccion, txtCiudad, txtFecha, txtPrecio, txtDescripcion;
    private ImageView imvFoto;
    private Double latitud, longitud;
    private boolean yaEsFavorito = false;

    private SQLiteDatabase db;
    private DBHelper Helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_evento, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myApplication = (MyApplication) requireActivity().getApplication();

        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");
            artista = getArguments().getString("artista");
            descripcion = getArguments().getString("descripcion");
            direccion = getArguments().getString("direccion");
            fecha = getArguments().getString("fecha");
            ciudad = getArguments().getString("ciudad");
            precio = getArguments().getInt("precio", 0);
            imagen = getArguments().getInt("imagen", 0);
            latitud = getArguments().getDouble("latitud", 0);
            longitud = getArguments().getDouble("longitud", 0);
        }

        botonAgregar = (Button) view.findViewById(R.id.btn_agregar_favoritos);

        // Inicializar vistas
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtArtista = view.findViewById(R.id.txtArtista);
        txtDireccion = view.findViewById(R.id.txtDireccion);
        txtCiudad = view.findViewById(R.id.txtCiudad);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        imvFoto = view.findViewById(R.id.imvFoto);

        // Configurar datos
        txtNombre.setText(nombre);
        txtDescripcion.setText(descripcion);
        txtArtista.setText(artista);
        txtDireccion.setText(direccion);
        txtCiudad.setText(ciudad);
        txtFecha.setText(fecha);
        txtPrecio.setText("$" + precio);
        imvFoto.setImageResource(imagen);

        Bundle bundle = getArguments();
        if (bundle != null) {
            yaEsFavorito = bundle.getBoolean("esFavorito", false);

            if (yaEsFavorito) {
                botonAgregar.setText("Eliminar de Favoritos");
            }
        }

        botonAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Eventos eventoActual = new Eventos(imagen, nombre, descripcion, artista, fecha, direccion, ciudad, precio, latitud, longitud);

                Helper = new DBHelper(requireContext());
                db = Helper.getWritableDatabase();

                if (yaEsFavorito) {
                    boolean fueEliminado = myApplication.removerFavorito(eventoActual);

                    if (fueEliminado) {

                        botonAgregar.setText("Agregar a Favoritos");
                        yaEsFavorito = false;

                    }
                } else {
                    // Insertar en BD
                    ContentValues cv = new ContentValues();
                    int id = (int) (System.currentTimeMillis() / 1000);
                    cv.put("Id", id);
                    cv.put("Nombre", nombre);
                    cv.put("Artista", artista);
                    cv.put("Direccion", direccion);
                    cv.put("Ciudad", ciudad);
                    cv.put("Fecha", fecha);
                    cv.put("Precio", precio);
                    cv.put("Imagen", String.valueOf(imagen));

                    long linea = db.insert("Favoritos", null, cv);
                    if (linea != -1) {
                        myApplication.agregarFavorito(eventoActual);
                        Toast.makeText(requireContext(), "Guardado en favoritos", Toast.LENGTH_SHORT).show();
                        botonAgregar.setText("Eliminar de Favoritos");
                        yaEsFavorito = true;
                    } else {
                        Toast.makeText(requireContext(), "Ya está en favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
                db.close();
            }
        });
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Agrega el marcador, mueve la camara y hace zoom
        LatLng ubicacionEvento = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubicacionEvento).title(direccion));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionEvento, 15f), 2000, null);
    }
}