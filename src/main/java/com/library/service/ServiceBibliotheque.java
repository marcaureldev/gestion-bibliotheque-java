package com.library.service;

import com.library.model.Livre;
import com.library.model.Utilisateur;
import com.library.utils.GenerateurId;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service principal de gestion de la bibliothèque
 */
public class ServiceBibliotheque {
    private Map<String, Livre> livres;
    private Map<String, Utilisateur> utilisateurs;

    public ServiceBibliotheque() {
        this.livres = new HashMap<>();
        this.utilisateurs = new HashMap<>();
    }

    // Gestion des livres
    public void ajouterLivre(Livre livre) {
        livres.put(livre.getId(), livre);
    }

    public String ajouterLivre(String titre, String auteur, int anneePublication, String genre) {
        String id = GenerateurId.genererIdLivre();
        Livre livre = new Livre(id, titre, auteur, anneePublication, genre);
        livres.put(id, livre);
        return id;
    }

    public void supprimerLivre(String idLivre) {
        livres.remove(idLivre);
    }

    public Livre trouverLivreParId(String id) {
        return livres.get(id);
    }

    public List<Livre> rechercherLivresParTitre(String titre) {
        return livres.values().stream()
                .filter(livre -> livre.getTitre().toLowerCase().contains(titre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livre> rechercherLivresParAuteur(String auteur) {
        return livres.values().stream()
                .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livre> rechercherLivresParGenre(String genre) {
        return livres.values().stream()
                .filter(livre -> livre.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Gestion des utilisateurs
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.put(utilisateur.getId(), utilisateur);
    }

    public Utilisateur trouverUtilisateurParId(String id) {
        return utilisateurs.get(id);
    }

    // Opérations d'emprunt
    public boolean emprunterLivre(String idUtilisateur, String idLivre) {
        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        Livre livre = livres.get(idLivre);
        
        if (utilisateur != null && livre != null && livre.estDisponible()) {
            utilisateur.emprunterLivre(livre);
            return true;
        }
        return false;
    }

    public boolean rendreLivre(String idUtilisateur, String idLivre) {
        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        Livre livre = livres.get(idLivre);
        
        if (utilisateur != null && livre != null && !livre.estDisponible()) {
            utilisateur.rendreLivre(livre);
            return true;
        }
        return false;
    }

    // Statistiques
    public List<Livre> getLivresDisponibles() {
        return livres.values().stream()
                .filter(Livre::estDisponible)
                .collect(Collectors.toList());
    }

    public List<Livre> getLivresEmpruntes() {
        return livres.values().stream()
                .filter(livre -> !livre.estDisponible())
                .collect(Collectors.toList());
    }

    public List<Livre> getHistoriqueEmpruntUtilisateur(String idUtilisateur) {
        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        return utilisateur != null ? utilisateur.getHistoriquePrets() : new ArrayList<>();
    }

    // Système de notation
    public void noterLivre(String idLivre, double note) {
        Livre livre = livres.get(idLivre);
        if (livre != null) {
            livre.setNote(note);
        }
    }

    // Nouvelles méthodes de listage
    public void listerUtilisateurs() {
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
        } else {
            utilisateurs.values().forEach(u -> {
                System.out.println("ID: " + u.getId() + " | Nom: " + u.getNom() + 
                                 " | Email: " + u.getEmail() + 
                                 " | Livres empruntés: " + u.getLivresEmpruntes().size());
            });
        }
    }

    public void listerTousLesLivres() {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre dans la bibliothèque.");
        } else {
            livres.values().forEach(l -> {
                System.out.println("ID: " + l.getId() + " | Titre: " + l.getTitre() + 
                                 " | Auteur: " + l.getAuteur() + 
                                 " | Disponible: " + (l.estDisponible() ? "Oui" : "Non"));
            });
        }
    }

    public void listerEmpruntsEnCours() {
        boolean aucunEmprunt = true;
        for (Utilisateur u : utilisateurs.values()) {
            List<Livre> emprunts = u.getLivresEmpruntes();
            if (!emprunts.isEmpty()) {
                System.out.println("Utilisateur " + u.getId() + " (" + u.getNom() + "):");
                emprunts.forEach(l -> System.out.println("  - " + l.getId() + " | " + l.getTitre()));
                aucunEmprunt = false;
            }
        }
        if (aucunEmprunt) {
            System.out.println("Aucun emprunt en cours.");
        }
    }

    // Méthodes utilitaires
    public int nombreUtilisateurs() {
        return utilisateurs.size();
    }

    public int nombreLivres() {
        return livres.size();
    }

    public boolean existeEmpruntsEnCours() {
        return utilisateurs.values().stream()
                .anyMatch(u -> !u.getLivresEmpruntes().isEmpty());
    }
}
