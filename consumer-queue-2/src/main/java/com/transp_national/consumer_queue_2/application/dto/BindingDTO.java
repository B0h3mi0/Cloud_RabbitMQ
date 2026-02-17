package com.transp_national.consumer_queue_2.application.dto;

public class BindingDTO {

    private String nombreCola;
    private String nombreExchange;

    // Constructores
    public BindingDTO() {}

    public BindingDTO(String nombreCola, String nombreExchange) {
        this.nombreCola = nombreCola;
        this.nombreExchange = nombreExchange;
    }

    // Getters y Setters
    public String getNombreCola() {
        return nombreCola;
    }

    public void setNombreCola(String nombreCola) {
        this.nombreCola = nombreCola;
    }

    public String getNombreExchange() {
        return nombreExchange;
    }

    public void setNombreExchange(String nombreExchange) {
        this.nombreExchange = nombreExchange;
    }
}