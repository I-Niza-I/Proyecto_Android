package com.proyecto.proyecto_android;

import com.google.android.gms.maps.model.LatLng;

public class Eventos {
    private int imagen;
    private String nombre;
    private String artista;
    private String fecha;
    private String direccion;
    private String ciudad;
    private int precio;
    private Double latitud;
    private Double longitud;

    public Eventos( int imagen, String nombre,  String artista, String fecha, String direccion, String ciudad, int precio, double latitud, double longitud) {

        this.imagen = imagen;
        this.nombre = nombre;
        this.artista = artista;
        this.fecha = fecha;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Eventos{" +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", precio=" + precio +
                '}';
    }
}
