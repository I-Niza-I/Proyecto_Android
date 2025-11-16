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

import java.util.ArrayList;
import java.util.Arrays;


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
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}