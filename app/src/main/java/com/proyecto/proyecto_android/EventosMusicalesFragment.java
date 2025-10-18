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

    private ArrayList<Eventos> listaEventos = new ArrayList<>(); // ArrayList para almacenar los objetos

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos_musicales, container, false);

        rellenarLista(); // Llamada al metodo para rellenar la ArrayList


        // Se define la variable declarada anteriormente haciendo referencia ->
        // al recyclerview creado en el layout.
        recyclerView = (RecyclerView) view.findViewById(R.id.revEventosMusicales);

        // Este metodo indica que el tamaño del RecyclerView no cambiara aunque se modifiquen los elementos.
        recyclerView.setHasFixedSize(true);

        // define la variable layoutManager, en este caso define cómo se van a mostrar los ítems ->
        // uno debajo de otro, osea una lista vertical.
        layoutManager = new LinearLayoutManager(requireContext());

        // Asigna el LayoutManager creado al RecyclerView.
        recyclerView.setLayoutManager(layoutManager);

        // Crea una instancia del adaptador personalizado, pasándole la lista de datos (listaEventos)
        // y el contexto actual (this) para poder inflar layouts o acceder a recursos.
        myAdapter = new RecyclerViewAdapter(listaEventos, requireContext());
        // Asigna el adaptador creado al RecyclerView.
        recyclerView.setAdapter(myAdapter);

        return view;
    }

    public void rellenarLista(){

        Eventos e1 = new Eventos( R.drawable.alberto_plaza, "Tour 40 años Sinfónico", "Alberto Plaza","30/10/2025", "Enjoy Coquimbo - Peñuelas Norte, Coquimbo, Chile", "Coquimbo", 32200,-29.946895, -71.291297 );
        Eventos e2 = new Eventos( R.drawable.sonido_del_misterio, "Lanzamiento nuevo disco “El sonido del Misterio” + Grandes Éxitos en La Serena", "De Saloon", "29/10/2025", "Gregorio Cordovez 391, 1710116 La Serena, Region de Coquimbo, Chile", "La Serena", 25000,-29.903347, -71.251497);
        Eventos e3 = new Eventos( R.drawable.temporada_conciertos, "La Danza del Piano", "David Greilsammer",  "30/10/2025", "Colegio Alemán La Serena Avenida 4 esquinas, La Serena, Región de Coquimbo, Chile", "La Serena", 11000,-29.952094, -71.229236);
        Eventos e4 = new Eventos( R.drawable.danza_del_piano,"Diciembre Temporada de Conciertos La Serena 2025","Abono Octubre", "1/11/2025", "Colegio Alemán La Serena Avenida 4 esquinas, La Serena, Región de Coquimbo, Chile", "La Serena", 10500,-29.952094, -71.229236);


        listaEventos.addAll(Arrays.asList(new Eventos[] {e1, e2, e3, e4}));
    }
}