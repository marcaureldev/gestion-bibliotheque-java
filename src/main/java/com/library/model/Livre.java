package com.library.model;

import java.time.LocalDate;


public class Livre {
    private String id;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String genre;
    private boolean disponible;
    private double note;
    
   
    public Livre(String id, String titre, String auteur, int anneePublication, String genre) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
        this.disponible = true;
        this.note = 0.0;
    }
    
    // Getters et Setters
    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        // Vérifie si la note est un multiple de 0.5 et comprise entre 0 et 5
        if (note >= 0 && note <= 5 && Math.abs(note * 2 - Math.round(note * 2)) < 0.001) {
            this.note = note;
        }
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", anneePublication=" + anneePublication +
                ", genre='" + genre + '\'' +
                ", disponible=" + disponible +
                ", note=" + note +
                '}';
    }
}
