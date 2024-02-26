package com.epf.rentmanager.models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.persistence.ConnectionManager;
public class Vehicle{
    private int ID_vehicle;
    private String constructeur;
    private String model;
    private int nb_place;

    public Vehicle(){}
    public Vehicle(int ID_vehicle,String constructeur,String model,int nb_place){
        this.ID_vehicle = ID_vehicle;
        this.constructeur=constructeur;
        this.model=model;
        this.nb_place=nb_place;
    }


    public int getID_vehicle() {
        return ID_vehicle;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModel() {
        return model;
    }

    public int getNb_place() {
        return nb_place;
    }

    public void setID_vehicle(int ID_vehicle) {
        this.ID_vehicle = ID_vehicle;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setNb_place(int nb_place) {
        this.nb_place = nb_place;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "ID_vehicle=" + ID_vehicle +
                ", constructeur='" + constructeur + '\'' +
                ", model='" + model + '\'' +
                ", nb_place=" + nb_place +
                '}';
    }
}