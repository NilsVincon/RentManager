package com.epf.rentmanager.models;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
public class Reservation {
    private int ID_Reservation;
    private int ID_client;
    private int ID_vehicle;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(){}

    public Reservation(int ID_Reservation,int ID_client,int ID_vehicle,LocalDate debut,LocalDate fin) {
        this.ID_Reservation = ID_Reservation;
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

    public int getID_Reservation() {
        return ID_Reservation;
    }

    public void setID_Reservation(int ID_Reservation) {
        this.ID_Reservation = ID_Reservation;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "ID_Reservation=" + ID_Reservation +
                ", ID_client=" + ID_client +
                ", ID_vehicle=" + ID_vehicle +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}