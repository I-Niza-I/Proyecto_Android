package com.proyecto.proyecto_android;

import android.app.AlertDialog;
import android.app.Application;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MyApplication extends Application {

    private static MyApplication instance;
    private static Organizacion cuentaLogueada;
    private static ArrayList<Eventos> listaEventos = new ArrayList<Eventos>();
    private static ArrayList<Eventos> eventosFavoritos = new ArrayList<Eventos>();
    private static ArrayList<Eventos> historialEventos = new ArrayList<Eventos>();
    private static ArrayList<Organizacion> listaCuentas = new ArrayList<Organizacion>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        rellenarOrganizacion();
    }


    public void rellenarOrganizacion(){
        Organizacion o1 = new Organizacion("12345678-9", "NombreGenerico", "ejemplo1@gmail.com", "descripcion", "1234");
        Organizacion o2 = new Organizacion("98765432-1", "NombreGenerico2", "ejemplo2@gmail.com", "descripcion2", "4321");
        listaCuentas.addAll(Arrays.asList(new Organizacion[] {o1,  o2}));
    }


    // Métodos para modificar los arrays
    public void agregarFavorito(Eventos evento) {
        if (!eventosFavoritos.contains(evento)) {
            eventosFavoritos.add(evento);
            Toast.makeText(this, "Evento publicado con éxito", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ya existe un evento con este nombre", Toast.LENGTH_SHORT).show();
        }
    }

    public void removerFavorito(Eventos evento) {
        eventosFavoritos.remove(evento);
    }

    public void agregarAlHistorial(Eventos evento) {
        if (!historialEventos.contains(evento)) {
            historialEventos.add(0, evento);
        }else{
            historialEventos.remove(evento);
            historialEventos.add(0, evento);
        }
    }

    public boolean agregarEvento(Eventos evento) {
        if (!eventoExiste(evento)) {
            listaEventos.add(evento);
            return true;
        } else {
            return false;
        }
    }

    private boolean eventoExiste(Eventos evento) {
        Eventos eventoRecorrido;

        for(int i = 0 ; i < listaEventos.size() ; i++){
            eventoRecorrido = listaEventos.get(i);
            if(eventoRecorrido.getNombre().equals(evento.getNombre())){
                return true;
            }
        }
        return false;
    }

    public void crearCuenta(Organizacion organizacion){
        if(!organizacionExiste(organizacion)){
            listaCuentas.add(organizacion);
        }
    }

    public boolean organizacionExiste(Organizacion organizacion){
        Organizacion cuentaRecorrida;
        for(int i = 0 ; i < listaCuentas.size() ; i++){
            cuentaRecorrida = listaCuentas.get(i);
            if(cuentaRecorrida.getRutEmpresa().equals(organizacion.getRutEmpresa())){
                return true;
            }
        }
        return false;
    }

    public boolean esFavorito(Eventos evento) {
        if(eventosFavoritos.contains(evento)){
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Usted ya agrego este evento \na sus favoritos")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
        return eventosFavoritos.contains(evento);
    }

    public boolean isSesionActiva() {
        return cuentaLogueada != null;
    }

    public void iniciarSesion(Organizacion cuenta) {
        cuentaLogueada = cuenta;
    }

    public void cerrarSesion() {
        cuentaLogueada = null;
    }

    public Organizacion getCuentaLogueada() {
        return cuentaLogueada;
    }


    public static MyApplication getInstance() {
        return instance;
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    public ArrayList<Eventos> getEventosMusicales() {
        return listaEventos;
    }

    public void setEventosMusicales(ArrayList<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public ArrayList<Eventos> getEventosFavoritos() {
        return eventosFavoritos;
    }

    public void setEventosFavoritos(ArrayList<Eventos> eventosFavoritos) {
        this.eventosFavoritos = eventosFavoritos;
    }

    public ArrayList<Eventos> getHistorialEventos() {
        return historialEventos;
    }

    public void setHistorialEventos(ArrayList<Eventos> historialEventos) {
        this.historialEventos = historialEventos;
    }

    public ArrayList<Organizacion> getListaCuentas() {
        return listaCuentas;
    }

    public static void setListaCuentas(ArrayList<Organizacion> listaCuentas) {
        MyApplication.listaCuentas = listaCuentas;
    }
}
