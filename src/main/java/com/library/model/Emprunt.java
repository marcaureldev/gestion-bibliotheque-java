package com.library.model;

import java.time.LocalDateTime;

public class Emprunt {
    private String id;
    private Livre livre;
    private Utilisateur utilisateur;
    private LocalDateTime dateEmprunt;
    private LocalDateTime dateRetour;
    private boolean estRetourne;

    public Emprunt(String id, Livre livre, Utilisateur utilisateur, LocalDateTime dateEmprunt) {
        this.id = id;
        this.livre = livre;
        this.utilisateur = utilisateur;
        this.dateEmprunt = dateEmprunt;
        this.estRetourne = false;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public Livre getLivre() {
        return livre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public LocalDateTime getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    public boolean isEstRetourne() {
        return estRetourne;
    }

    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
        this.estRetourne = true;
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id='" + id + '\'' +
                ", livre=" + livre.getTitre() +
                ", utilisateur=" + utilisateur.getNom() +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + (dateRetour != null ? dateRetour : "Non retourné") +
                ", estRetourne=" + estRetourne +
                '}';
    }
}
