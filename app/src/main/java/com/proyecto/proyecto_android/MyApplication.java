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
        rellenarEventos();
        rellenarOrganizacion();
    }

    public void rellenarEventos(){
        Eventos e1 = new Eventos( R.drawable.alberto_plaza, "Tour 40 años Sinfónico","El esperado momento ha llegado: Alberto Plaza regresa a Chile en el mes de octubre, y por supuesto a La Serena y Coquimbo para reencontrarse con su público y compartir este viaje musical cargado de recuerdos y emociones. ",  "Alberto Plaza","30/10/2025", "Enjoy Coquimbo - Peñuelas Norte, Coquimbo, Chile", "Coquimbo", 32200,-29.946895, -71.291297 );
        Eventos e2 = new Eventos( R.drawable.sonido_del_misterio, "Lanzamiento nuevo disco “El sonido del Misterio” + Grandes Éxitos en La Serena", "Después de 10 años, la banda penquista, vuelve a la carga con un disco de larga duración. Sonidos que provienen de  la esencia e identidad de De Saloon son la marga registrada de este nuevo trabajo.","De Saloon", "29/10/2025", "Gregorio Cordovez 391, 1710116 La Serena, Region de Coquimbo, Chile", "La Serena", 25000,-29.903347, -71.251497);
        Eventos e3 = new Eventos( R.drawable.temporada_conciertos, "La Danza del Piano", "Durante esta temporada, David Greilsammer se presenta como director y solista en la Philharmonie de París y realiza varias giras por América Latina y Asia, además del lanzamiento internacional de su nuevo álbum como solist", "David Greilsammer",  "30/10/2025", "Colegio Alemán La Serena Avenida 4 esquinas, La Serena, Región de Coquimbo, Chile", "La Serena", 11000,-29.952094, -71.229236);
        Eventos e4 = new Eventos( R.drawable.danza_del_piano,"Diciembre Temporada de Conciertos La Serena 2025", "La joven pianista Konstantina Vidalaki es considerada una de las artistas griegas emergentes más destacadas de su generación. Su interpretación se distingue por un sonido delicado y expresivo, combinado con gran vigor y virtuosismo, así como por su compromiso con el diálogo cultural a través de la música. ","Abono Octubre", "1/11/2025", "Colegio Alemán La Serena Avenida 4 esquinas, La Serena, Región de Coquimbo, Chile", "La Serena", 10500,-29.952094, -71.229236);


        listaEventos.addAll(Arrays.asList(new Eventos[] {e1, e2, e3, e4}));
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
            int index = historialEventos.indexOf(evento);
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
