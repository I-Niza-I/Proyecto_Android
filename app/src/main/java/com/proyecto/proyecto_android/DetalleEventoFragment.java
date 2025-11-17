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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.proyecto_android.databinding.ActivityMapsBinding;

public class DetalleEventoFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private MyApplication myApplication;
    private String id, nombre, artista,descripcion, direccion, ciudad, fecha, urlImagen, rutOrganizacionEvento;
    private int precio;
    private Button botonAgregar, botonEliminar, botonModificar;
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
            id = getArguments().getString("id");
            nombre = getArguments().getString("nombre");
            artista = getArguments().getString("artista");
            descripcion = getArguments().getString("descripcion");
            direccion = getArguments().getString("direccion");
            fecha = getArguments().getString("fecha");
            ciudad = getArguments().getString("ciudad");
            precio = getArguments().getInt("precio", 0);
            urlImagen = getArguments().getString("urlImagen");
            latitud = getArguments().getDouble("latitud", 0);
            longitud = getArguments().getDouble("longitud", 0);
            rutOrganizacionEvento = getArguments().getString("rutOrganizacion");
        }

        // Inicializar vistas
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtArtista = view.findViewById(R.id.txtArtista);
        txtDireccion = view.findViewById(R.id.txtDireccion);
        txtCiudad = view.findViewById(R.id.txtCiudad);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        imvFoto = view.findViewById(R.id.imvFoto);
        botonAgregar = (Button) view.findViewById(R.id.btn_agregar_favoritos);
        botonEliminar = (Button) view.findViewById(R.id.btn_eliminar_evento);
        botonModificar = (Button) view.findViewById(R.id.btn_modificar_evento);


        // Configurar datos
        txtNombre.setText(nombre);
        txtDescripcion.setText(descripcion);
        txtArtista.setText(artista);
        txtDireccion.setText(direccion);
        txtCiudad.setText(ciudad);
        txtFecha.setText(fecha);
        txtPrecio.setText("$" + precio);
        Glide.with(requireContext())
                .load(urlImagen)
                .placeholder(R.drawable.place_holder) // Muestra el placeholder como una imagen de carga
                .error(R.drawable.place_holder) // Muestra un el placeholder si hay error
                .into(imvFoto);

        Bundle bundle = getArguments();
        if (bundle != null) {
            yaEsFavorito = bundle.getBoolean("esFavorito", false);

            if (yaEsFavorito) {
                botonAgregar.setText("Eliminar de Favoritos");
            }
        }

        Organizacion cuentaLogueada = myApplication.getCuentaLogueada();


        if (cuentaLogueada != null && cuentaLogueada.getRutEmpresa().equals(rutOrganizacionEvento)) {
            // MODO ORGANIZACION: Es el dueño del evento
            botonEliminar.setVisibility(View.VISIBLE);
            botonModificar.setVisibility(View.VISIBLE);
            botonAgregar.setVisibility(View.GONE);
        } else {
            // MODO USUARIO COMUN: No es el dueño o no hay sesión
            botonEliminar.setVisibility(View.GONE);
            botonModificar.setVisibility(View.GONE);
            botonAgregar.setVisibility(View.VISIBLE);
        }

        botonAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Eventos eventoActual = new Eventos(urlImagen, rutOrganizacionEvento, nombre, descripcion, artista, fecha, direccion, ciudad, precio, latitud, longitud);
                eventoActual.setId(id);

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
                    cv.put("Id", eventoActual.getId());
                    cv.put("Nombre", eventoActual.getNombre());
                    cv.put("Artista", eventoActual.getArtista());
                    cv.put("Descripcion", eventoActual.getDescripcion());
                    cv.put("Direccion", eventoActual.getDireccion());
                    cv.put("Ciudad", eventoActual.getCiudad());
                    cv.put("Fecha", eventoActual.getFecha());
                    cv.put("Precio", eventoActual.getPrecio());
                    cv.put("urlImagen", eventoActual.getUrlImagen());
                    cv.put("Latitud", eventoActual.getLatitud());
                    cv.put("Longitud", eventoActual.getLongitud());
                    cv.put("RutOrganizacion", eventoActual.getRutOrganizacion());

                    long linea = db.insert("Favoritos", null, cv);
                    if (linea != -1) {
                        myApplication.agregarFavorito(eventoActual);
                        Toast.makeText(requireContext(), "Guardado en favoritos", Toast.LENGTH_SHORT).show();
                        botonAgregar.setText("Eliminar de Favoritos");
                        yaEsFavorito = true;
                    } else {
                        Toast.makeText(requireContext(), "Ya estÃ¡ en favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
                db.close();
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearEventoFragment modificarFragment = new CrearEventoFragment();

                Bundle bundle = getArguments();

                if (bundle != null) {
                    bundle.putBoolean("esModoModificacion", true);
                }

                modificarFragment.setArguments(bundle);

                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, modificarFragment)
                            .addToBackStack(null) // Permite volver atrás al detalle.
                            .commit();
                }
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Eventos eventoAEliminar = new Eventos(urlImagen, rutOrganizacionEvento, nombre, descripcion, artista, fecha, direccion, ciudad, precio, latitud, longitud);
                eventoAEliminar.setId(id);

                myApplication.removerFavorito(eventoAEliminar);

                if (id != null) {
                    FirebaseDatabase.getInstance().getReference("Evento")
                            .child(id)
                            .removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Evento eliminado con éxito", Toast.LENGTH_SHORT).show();
                                // Regresar al fragmento anterior para que la lista se actualice
                                if (getActivity() != null) {
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Error al eliminar el evento", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Muestra los botones de Zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Agrega el marcador, mueve la camara y hace zoom
        LatLng ubicacionEvento = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubicacionEvento).title(direccion));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionEvento, 15f), 2000, null);
    }
}