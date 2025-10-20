package com.proyecto.proyecto_android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;


public class EventosMusicalesFragment extends Fragment {

    // Declaracion del atributo de tipo RecyclerView
    private RecyclerView recyclerView;

    // Creacion del adaptador: Este actua como un "puente" entre los datos y lo visual
    // toma los datos y los convierte en vistas
    private RecyclerViewAdapter myAdapter;

    // Creacion del gestor del layout: Organiza los items en el layout
    private RecyclerView.LayoutManager layoutManager;

    MyApplication myApplication;

    private ArrayList<Eventos> listaEventos;

    EditText edt_buscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos_musicales, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();

        listaEventos = myApplication.getEventosMusicales();

        edt_buscar = view.findViewById(R.id.edtBusqueda);
        recyclerView = (RecyclerView) view.findViewById(R.id.revEventosMusicales);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new RecyclerViewAdapter(listaEventos, requireContext());
        recyclerView.setAdapter(myAdapter);

        edt_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
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

}