package com.library.model;

import java.util.ArrayList;
import java.util.List;


public class Utilisateur {
    private String id;
    private String nom;
    private String email;
    private List<Livre> livresEmpruntes;
    private List<Livre> historiquePrets;

   
    public Utilisateur(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.livresEmpruntes = new ArrayList<>();
        this.historiquePrets = new ArrayList<>();
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Livre> getLivresEmpruntes() {
        return livresEmpruntes;
    }

    public List<Livre> getHistoriquePrets() {
        return historiquePrets;
    }

    
    public void emprunterLivre(Livre livre) {
        if (livre.isDisponible()) {
            livresEmpruntes.add(livre);
            historiquePrets.add(livre);
            livre.setDisponible(false);
        }
    }

    
    public void rendreLivre(Livre livre) {
        livresEmpruntes.remove(livre);
        livre.setDisponible(true);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", livresEmpruntes=" + livresEmpruntes.size() +
                '}';
    }
}
