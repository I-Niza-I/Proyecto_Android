package com.proyecto.proyecto_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class FavoritosFragment extends Fragment {

    // Declaracion del atributo de tipo RecyclerView
    private RecyclerView recyclerView;

    // Creacion del adaptador: Este actua como un "puente" entre los datos y lo visual
    // toma los datos y los convierte en vistas
    private RecyclerViewAdapter myAdapter;

    // Creacion del gestor del layout: Organiza los items en el layout
    private RecyclerView.LayoutManager layoutManager;
    MyApplication myApplication;
    ArrayList<Eventos> listaFavoritos;

    private DBHelper Helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();
        listaFavoritos = myApplication.getEventosFavoritos();

        // Se define la variable declarada anteriormente haciendo referencia ->
        // al recyclerview creado en el layout.
        recyclerView = (RecyclerView) view.findViewById(R.id.rev_eventos_favoritos);

        // Este metodo indica que el tamaño del RecyclerView no cambiara aunque se modifiquen los elementos.
        recyclerView.setHasFixedSize(true);

        // define la variable layoutManager, en este caso define cómo se van a mostrar los ítems ->
        // uno debajo de otro, osea una lista vertical.
        layoutManager = new LinearLayoutManager(requireContext());

        // Asigna el LayoutManager creado al RecyclerView.
        recyclerView.setLayoutManager(layoutManager);

        // Crea una instancia del adaptador personalizado, pasándole la lista de datos (listaEventos)
        // y el contexto actual (this) para poder inflar layouts o acceder a recursos.
        myAdapter = new RecyclerViewAdapter(listaFavoritos, requireContext(), true);
        // Asigna el adaptador creado al RecyclerView.
        recyclerView.setAdapter(myAdapter);

        return view;
    }

}