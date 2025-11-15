package com.proyecto.proyecto_android;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

public class Eventos {
    @Exclude // Evita que Firebase guarde este campo en el objeto, ya que es la clave principal.
    private String id;
    private String urlImagen;
    private String nombre;
    private String descripcion;
    private String artista;
    private String fecha;
    private String direccion;
    private String ciudad;
    private int precio;
    private Double latitud;
    private Double longitud;

    public Eventos() {}

    public Eventos(String urlImagen, String nombre, String descripcion, String artista, String fecha, String direccion, String ciudad, int precio, Double latitud, Double longitud) {
        this.urlImagen = urlImagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.artista = artista;
        this.fecha = fecha;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Eventos{" +
                "id='" + id + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", artista='" + artista + '\'' +
                ", fecha='" + fecha + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", precio=" + precio +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
