package com.proyecto.proyecto_android;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.proyecto_android.databinding.ActivityMapsBinding;

import java.util.Calendar;
import java.util.Locale;

public class CrearEventoFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MyApplication myApplication;

    private EditText nombre, descripcion, direccion, fecha, precio, artista;
    private Spinner ciudad;
    private LatLng ubicacionSeleccionada;
    private MarkerOptions marcadorPuesto;
    private Button btn_publicar;
    private Calendar fechaSeleccionada;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myApplication = (MyApplication) getActivity().getApplication();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Evento");

        nombre = (EditText) view.findViewById(R.id.edt_nombre);
        artista = (EditText) view.findViewById(R.id.edt_artista);
        descripcion = (EditText) view.findViewById(R.id.edt_descripcion);
        direccion = (EditText) view.findViewById(R.id.edt_direccion);
        fecha = (EditText) view.findViewById(R.id.edt_fecha);
        precio = (EditText) view.findViewById(R.id.edt_coste);

        ciudad = (Spinner) view.findViewById(R.id.spn_ciudades);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSelectorFecha();
            }
        });

        btn_publicar = (Button) view.findViewById(R.id.btn_publicar);

        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ubicacionSeleccionada == null) {
                    Toast.makeText(getContext(), "Por favor, selecciona una ubicación en el mapa", Toast.LENGTH_SHORT).show();
                    return;
                }

                int costeEvento = 0;
                // Obtener los datos del formulario
                String nombreEvento = nombre.getText().toString().trim();
                String artistaEvento = artista.getText().toString().trim();
                String descripcionEvento = descripcion.getText().toString().trim();
                String direccionEvento = direccion.getText().toString().trim();
                String fechaEvento = fecha.getText().toString().trim();
                String costeEventoString = precio.getText().toString().trim();
                String ciudadEvento = ciudad.getSelectedItem().toString().trim();

                // Obtener latitud y longitud
                double latitud = ubicacionSeleccionada.latitude;
                double longitud = ubicacionSeleccionada.longitude;

                if (!validarCampos(nombreEvento, artistaEvento, descripcionEvento, direccionEvento, fechaEvento, costeEventoString, ciudadEvento)) {
                    return;
                }

                if (!validarFecha()) {
                    return;
                }

                try {
                    costeEvento = Integer.parseInt(costeEventoString);
                    if(costeEvento < 0){
                        Toast.makeText(getContext(), "Introduce un precio valido", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Introduce un precio valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear el objeto Evento
                Eventos nuevoEvento = new Eventos(
                        R.drawable.place_holder,
                        nombreEvento,
                        descripcionEvento,
                        artistaEvento,
                        fechaEvento,
                        direccionEvento,
                        ciudadEvento,
                        costeEvento,
                        latitud,
                        longitud
                );

                String id = databaseReference.push().getKey();
                databaseReference.child(id).setValue(nuevoEvento)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(requireContext(), "Evento agregado", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Agrega el marcador, mueve la camara y hace zoom
        LatLng ubicacionEvento = new LatLng(-29.958288, -71.339126);
        mMap.addMarker(new MarkerOptions().position(ubicacionEvento).title("Coquimbo"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionEvento, 10f), 2000, null);

        // Configurar el listener para clicks en el mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                agregarMarcador(latLng);
            }
        });
    }

    private void agregarMarcador(LatLng latLng) {
        // Limpiar marcadores anteriores
        mMap.clear();

        // Agregar nuevo marcador
        marcadorPuesto = new MarkerOptions()
                .position(latLng)
                .title("Ubicación del evento");
        mMap.addMarker(marcadorPuesto);

        // Guardar la ubicación seleccionada
        ubicacionSeleccionada = latLng;

        // Mover la cámara a la nueva ubicacion
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        Toast.makeText(getContext(), "Ubicación seleccionada: " + latLng.latitude + ", " + latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }

    public void limpiar(){
        nombre.setText("");
        artista.setText("");
        descripcion.setText("");
        direccion.setText("");
        fecha.setText("");
        precio.setText("");
        mMap.clear();

        fechaSeleccionada = null;

        // Restablece la ubicación inicial del mapa
        LatLng ubicacionInicial = new LatLng(-29.958288, -71.339126);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 10f));
        ubicacionSeleccionada = null;
    }

    private void mostrarDialogoSelectorFecha() {
        // Obtener la fecha actual para mostrarla por defecto en el DatePicker
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear una instancia de DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String fechaFormateada = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);

            fechaSeleccionada = Calendar.getInstance();
            fechaSeleccionada.set(year, monthOfYear, dayOfMonth);

            fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 0);
            fechaSeleccionada.set(Calendar.MINUTE, 0);
            fechaSeleccionada.set(Calendar.SECOND, 0);
            fechaSeleccionada.set(Calendar.MILLISECOND, 0);

            fecha.setText(fechaFormateada);
        },
        ano, mes, dia);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private boolean validarUbicacion() {
        if (ubicacionSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor, selecciona una ubicación en el mapa", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarCampos(String... campos) {
        for (String campo : campos) {
            if (campo.trim().isEmpty()) {
                Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean validarFecha() {
        if (fechaSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor, selecciona una fecha.", Toast.LENGTH_LONG).show();
            return false;
        }

        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        if (fechaSeleccionada.before(hoy)) {
            Toast.makeText(getContext(), "La fecha del evento no puede ser anterior al día de hoy.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}