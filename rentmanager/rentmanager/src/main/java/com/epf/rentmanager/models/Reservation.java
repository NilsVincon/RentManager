package com.epf.rentmanager.models;

import java.time.LocalDate;

public class Reservation {
    private int ID_reservation;
    private int ID_client;
    private int ID_vehicle;
    private LocalDate debut;
    private LocalDate fin;
    private String vehicleName;
    private String clientName;
    public Reservation(){}

    public Reservation(int ID_reservation,int ID_client,int ID_vehicle,LocalDate debut,LocalDate fin) {
        this.ID_reservation = ID_reservation;
        this.ID_client = ID_client;
        this.ID_vehicle = ID_vehicle;
        this.debut = debut;
        this.fin = fin;
    }
    public Reservation(int ID_client,int ID_vehicle,LocalDate debut,LocalDate fin) {
        this.ID_client = ID_client;
        this.ID_vehicle = ID_vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public int getID_reservation() {
        return ID_reservation;
    }

    public void setID_reservation(int ID_reservation) {
        this.ID_reservation = ID_reservation;
    }

    public int getID_client() {
        return ID_client;
    }

    public void setID_client(int ID_client) {
        this.ID_client = ID_client;
    }

    public int getID_vehicle() {
        return ID_vehicle;
    }

    public void setID_vehicle(int ID_vehicle) {
        this.ID_vehicle = ID_vehicle;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public void setVehicleName(String constructeur,String model){
        this.vehicleName = constructeur +" "+ model;
    }
    public String getVehicleName(){
        return this.vehicleName;
    }
    public void setClientName(String nom,String prenom){
        this.clientName = nom + " "+ prenom;
    }
    public String getClientName(){
        return this.clientName;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "ID_reservation=" + ID_reservation +
                ", ID_client=" + ID_client +
                ", ID_vehicle=" + ID_vehicle +
                ", debut=" + debut +
                ", fin=" + fin +
                ", nom_client=" + clientName +
                "; nom_vehicle" + vehicleName +
                '}';
    }
}