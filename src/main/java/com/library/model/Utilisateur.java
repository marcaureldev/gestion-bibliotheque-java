package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un utilisateur de la bibliothèque
 */
public class Utilisateur {
    private String id;
    private String nom;
    private String email;
    private List<Livre> livresEmpruntes;
    private List<Livre> historiquePrets;

    /**
     * Constructeur de la classe Utilisateur
     * @param id Identifiant unique de l'utilisateur
     * @param nom Nom de l'utilisateur
     * @param email Email de l'utilisateur
     */
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

    /**
     * Emprunte un livre
     * @param livre Le livre à emprunter
     */
    public void emprunterLivre(Livre livre) {
        if (livre.isDisponible()) {
            livresEmpruntes.add(livre);
            historiquePrets.add(livre);
            livre.setDisponible(false);
        }
    }

    /**
     * Retourne un livre
     * @param livre Le livre à retourner
     */
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
