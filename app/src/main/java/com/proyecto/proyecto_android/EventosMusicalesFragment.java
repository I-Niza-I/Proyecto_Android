package com.proyecto.proyecto_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private RecyclerView.Adapter myAdapter;

    // Creacion del gestor del layout: Organiza los items en el layout
    private RecyclerView.LayoutManager layoutManager;

    MyApplication myApplication;

    private ArrayList<Eventos> listaEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos_musicales, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();

        listaEventos = myApplication.getEventosMusicales();
        recyclerView = (RecyclerView) view.findViewById(R.id.revEventosMusicales);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecyclerViewAdapter(listaEventos, requireContext());
        recyclerView.setAdapter(myAdapter);

        return view;
    }

}