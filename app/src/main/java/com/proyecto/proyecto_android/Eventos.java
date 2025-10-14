package com.proyecto.proyecto_android;

public class Eventos {
    private int imagen;
    private String nombre;
    private String artista;
    private String fecha;
    private String direccion;
    private String ciudad;
    private int precio;

    public Eventos( int imagen, String nombre,  String artista, String fecha, String direccion, String ciudad, int precio) {

        this.imagen = imagen;
        this.nombre = nombre;
        this.artista = artista;
        this.fecha = fecha;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.precio = precio;
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
