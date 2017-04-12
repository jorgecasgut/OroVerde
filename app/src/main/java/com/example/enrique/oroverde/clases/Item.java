package com.example.enrique.oroverde.clases;

/**
 * Created by enrique on 12/04/2017.
 */

//clase que guarda los items
public class Item {

    private String imagen;
    private String titulo;
    private String contenido;

    public Item(String titulo, String contenido, String imagen) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
