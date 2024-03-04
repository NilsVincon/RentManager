package com.epf.rentmanager.models;

import java.time.LocalDate;


public class Client{
    private int ID_client;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(){}
    public Client(int ID_client,String nom,String prenom,String email,LocalDate naissance){
        this.ID_client = ID_client;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.naissance=naissance;
    }
    public Client(String nom,String prenom,String email,LocalDate naissance){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.naissance=naissance;
    }

    public int getID_client() {
        return ID_client;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setID_client(int ID_client) {
        this.ID_client = ID_client;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID_client=" + ID_client +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}