package com.example.enrique.oroverde.clases;

/**
 * Created by enrique on 24/04/2017.
 */

public class Item2 {

    private String folio;
    private String fecha;
    private String total;

    public Item2(String folio, String fecha, String total) {
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
