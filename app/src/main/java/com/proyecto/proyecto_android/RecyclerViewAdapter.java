package com.proyecto.proyecto_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

// Adaptador personalizado para el RecyclerView.
// Se encarga de "conectar" los datos de la lista con las vistas de cada ítem en el RecyclerView.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    ArrayList<Eventos> listaEventos;
    Context contexto;

    // Metodo que recibe la lista de datos y el contexto desde la Activity donde se usa el RecyclerView.
    public RecyclerViewAdapter(ArrayList<Eventos> listaEventos, Context contexto) {
        this.listaEventos = listaEventos;
        this.contexto = contexto;
    }


    // Metodo donde se "infla" o "carga" el layout XML
    // representa un ítem individual de la lista (evento_layout.xml)
    // y se devuelve dentro de un objeto ViewHolder.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evento_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


     // Este metodo sirve basicamente para ingresar el texto que mostrara cada textview
     // como se trabaja con objetos se utilizan los setters y getters
     // Este metodo  se ejecuta cada vez que el RecyclerView necesita mostrar un elemento en pantalla.
     // aca se vinculan los datos de la lista (listaEventos) con los componentes visuales del layout.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_nombre_evento.setText(listaEventos.get(position).getNombre());
        holder.tv_fecha_evento.append(" " + listaEventos.get(position).getFecha());
        holder.tv_ciudad_evento.append(" " + listaEventos.get(position).getCiudad());
        holder.tv_precio_evento.append(" $"+(listaEventos.get(position).getPrecio()));
        holder.iv_imagen_evento.setImageResource(listaEventos.get(position).getImagen());


        holder.cv_tarjeta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Eventos evento = listaEventos.get(holder.getBindingAdapterPosition());

                DetalleEventoFragment fragment = new DetalleEventoFragment();
                Bundle args = new Bundle();

                args.putString("nombre", evento.getNombre());
                args.putString("artista", evento.getArtista());
                args.putString("direccion", evento.getDireccion());
                args.putString("fecha", evento.getFecha());
                args.putString("ciudad", evento.getCiudad());
                args.putInt("precio", evento.getPrecio());
                args.putInt("imagen", evento.getImagen());
                args.putDouble("latitud", evento.getLatitud());
                args.putDouble("longitud", evento.getLongitud());

                fragment.setArguments(args);

                // Navegar al fragment (depende de tu implementación de Navigation)
                FragmentManager fragmentManager = ((AppCompatActivity)contexto).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment); // Ajusta el ID del contenedor
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    // Este metodo solo obtiene la cantidad de items por lo que solo devuelve el tamaño de la lista.
    @Override
    public int getItemCount() {
        return listaEventos.size();
    }


    // Clase para el view holder, en este caso se trabajo con una clase anidada
    // pero no es estrictamente necesario.
    // mantiene las referencias a los elementos visuales (vistas) de cada ítem.
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_evento;
        TextView tv_fecha_evento;
        TextView tv_ciudad_evento;
        TextView tv_precio_evento;
        ImageView iv_imagen_evento;
        CardView cv_tarjeta;


        // Metodo constructor de la clasen (MyViewHolder)
        // Recibe la vista del ítem (itemView) y obtiene las referencias a cada componente.
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre_evento = itemView.findViewById(R.id.tv_nombre_Evento);
            tv_fecha_evento = itemView.findViewById(R.id.tv_fecha_Evento);
            tv_ciudad_evento = itemView.findViewById(R.id.tv_ciudad_Evento);
            tv_precio_evento = itemView.findViewById(R.id.tv_precio_Evento);
            iv_imagen_evento = itemView.findViewById(R.id.iv_imagen_Evento);
            cv_tarjeta = itemView.findViewById(R.id.cv_tarjeta);
        }
    }
}