package com.proyecto.proyecto_android;

public class Organizacion {

    private String rutEmpresa;
    private String nombre;
    private String correo;
    private String descripcion;
    private String password;

    public Organizacion(String rutEmpresa, String nombre, String correo, String descripcion, String password) {
        this.rutEmpresa = rutEmpresa;
        this.nombre = nombre;
        this.correo = correo;
        this.descripcion = descripcion;
        this.password = password;
    }

    public String getRutEmpresa() {
        return rutEmpresa;
    }

    public void setRutEmpresa(String rutEmpresa) {
        this.rutEmpresa = rutEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
