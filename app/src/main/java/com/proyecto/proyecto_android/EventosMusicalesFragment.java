package com.proyecto.proyecto_android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EventosMusicalesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    MyApplication myApplication;
    private ArrayList<Eventos> listaEventos;
    EditText edt_buscar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos_musicales, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Evento");

        listaEventos = new ArrayList<>();

        edt_buscar = view.findViewById(R.id.edtBusqueda);
        recyclerView = (RecyclerView) view.findViewById(R.id.revEventosMusicales);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new RecyclerViewAdapter(listaEventos, requireContext());
        recyclerView.setAdapter(myAdapter);

        rellenarLista();



        edt_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No hace nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No hace nada
            }
        });

        return view;
    }

    private void filtrar(String texto) {
        ArrayList<Eventos> listaFiltrada = new ArrayList<>();

        for (Eventos evento : listaEventos) {
            // Compara el nombre del evento (en minúsculas) con el texto de búsqueda (en minúsculas)
            if (evento.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(evento);
            }
        }
        // Actualiza el adaptador con la nueva lista filtrada
        myAdapter.filtrarLista(listaFiltrada);
    }
    private void filtrarEventosPasados() {
        ArrayList<Eventos> eventosFuturos = new ArrayList<>();

        for (Eventos evento : listaEventos) {
            if (esEventoPasado(evento.getFecha())) {
                eventosFuturos.add(evento);
            }
        }
        // Actualiza con los eventos futuros
        myAdapter.filtrarLista(eventosFuturos);
    }

    private boolean esEventoPasado(String fechaEventoStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date fechaEvento = df.parse(fechaEventoStr);
            Date fechaActual = new Date();

            Calendar calEvento = Calendar.getInstance();
            Calendar calActual = Calendar.getInstance();
            calEvento.setTime(fechaEvento);
            calActual.setTime(fechaActual);

            //para poder comparar años, meses y días del evento con la fecha actual
            int aEvento = calEvento.get(Calendar.YEAR);
            int mEvento = calEvento.get(Calendar.MONTH);
            int dEvento = calEvento.get(Calendar.DAY_OF_MONTH);

            int aActual = calActual.get(Calendar.YEAR);
            int mActual = calActual.get(Calendar.MONTH);
            int dActual = calActual.get(Calendar.DAY_OF_MONTH);

            // retornando false si es pasado y true si es futuro o igual
            if (aEvento < aActual) {
                return false;
            } else if (aEvento == aActual) {
                if (mEvento < mActual) {
                    return false;
                } else if (mEvento == mActual) {
                    if (dEvento < dActual) {
                        return false;
                    } else if (dEvento == dActual) {
                        return true;
                    }
                }
            }
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }
    private void rellenarLista() {
        Organizacion cuentaLogueada = myApplication.getCuentaLogueada();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEventos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String eventoId = dataSnapshot.getKey();

                    Eventos evento = dataSnapshot.getValue(Eventos.class);

                    if (evento != null && eventoId != null) {
                        evento.setId(eventoId);
                        if (cuentaLogueada != null) {
                            if (cuentaLogueada.getRutEmpresa().equals(evento.getRutOrganizacion())) {
                                listaEventos.add(evento);
                            }
                        }
                        else {
                            listaEventos.add(evento);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
                filtrarEventosPasados();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}