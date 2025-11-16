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

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

// Adaptador personalizado para el RecyclerView.
// Se encarga de "conectar" los datos de la lista con las vistas de cada ítem en el RecyclerView.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<Eventos> listaEventos;
    Context contexto;
    MyApplication myApplication;
    private boolean esDesdeFavoritosOHistorial;

    // Metodo que recibe la lista de datos y el contexto desde la Activity donde se usa el RecyclerView.
    public RecyclerViewAdapter(ArrayList<Eventos> listaEventos, Context contexto, boolean esDesdeFavoritosOHistorial) {
        this.listaEventos = listaEventos;
        this.contexto = contexto;
        this.myApplication = (MyApplication) contexto.getApplicationContext();
        this.esDesdeFavoritosOHistorial = esDesdeFavoritosOHistorial;
    }

    public RecyclerViewAdapter(ArrayList<Eventos> listaEventos, Context contexto) {
        this(listaEventos, contexto, false);
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
        Eventos eventoActual = listaEventos.get(position);

        holder.tv_nombre_evento.setText(listaEventos.get(position).getNombre());
        holder.tv_fecha_evento.setText("Fecha: " + listaEventos.get(position).getFecha());
        holder.tv_ciudad_evento.setText("Ciudad: " + listaEventos.get(position).getCiudad());
        holder.tv_precio_evento.setText("Precio: $" + listaEventos.get(position).getPrecio());

        Glide.with(contexto)
                .load(eventoActual.getUrlImagen()) // Usamos la URL del objeto Evento
                .placeholder(R.drawable.place_holder) // Imagen mientras carga
                .error(R.drawable.place_holder)       // Imagen si hay un error
                .into(holder.iv_imagen_evento);


        holder.cv_tarjeta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Eventos evento = listaEventos.get(holder.getBindingAdapterPosition());
                if (!esDesdeFavoritosOHistorial) {
                    myApplication.agregarAlHistorial(evento);
                }
                DetalleEventoFragment fragment = new DetalleEventoFragment();
                Bundle bundle = new Bundle();

                bundle.putString("id", evento.getId());
                bundle.putString("nombre", evento.getNombre());
                bundle.putString("descripcion", evento.getDescripcion());
                bundle.putString("artista", evento.getArtista());
                bundle.putString("direccion", evento.getDireccion());
                bundle.putString("fecha", evento.getFecha());
                bundle.putString("ciudad", evento.getCiudad());
                bundle.putInt("precio", evento.getPrecio());
                bundle.putString("urlImagen", evento.getUrlImagen());
                bundle.putDouble("latitud", evento.getLatitud());
                bundle.putDouble("longitud", evento.getLongitud());

                if (esDesdeFavoritosOHistorial || myApplication.esFavorito(evento)) {
                    bundle.putBoolean("esFavorito", true);
                } else {
                    bundle.putBoolean("esFavorito", false);
                }

                fragment.setArguments(bundle);

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

    public void filtrarLista(ArrayList<Eventos> listaFiltrada) {
        this.listaEventos = listaFiltrada;
        notifyDataSetChanged();
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