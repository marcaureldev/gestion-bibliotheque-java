package com.library.service;

import com.library.model.Emprunt;
import com.library.model.Livre;
import com.library.model.Utilisateur;
import com.library.utils.GenerateurId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ServiceBibliotheque {
    private List<Livre> livres;
    private List<Utilisateur> utilisateurs;
    private List<Emprunt> emprunts;

    public ServiceBibliotheque() {
        this.livres = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
        this.emprunts = new ArrayList<>();
        initialiserLivres();
    }

    private void initialiserLivres() {
        // Ajout de 10 livres minimum
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "1984", "George Orwell", 1949, "Science-fiction"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "Conte philosophique"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Les Misérables", "Victor Hugo", 1862, "Roman historique"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "L'Étranger", "Albert Camus", 1942, "Roman philosophique"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Notre-Dame de Paris", "Victor Hugo", 1831, "Roman historique"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Le Rouge et le Noir", "Stendhal", 1830, "Roman psychologique"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Madame Bovary", "Gustave Flaubert", 1857, "Roman réaliste"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Les Fleurs du Mal", "Charles Baudelaire", 1857, "Poésie"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Le Comte de Monte-Cristo", "Alexandre Dumas", 1844, "Roman d'aventure"));
        ajouterLivre(new Livre(GenerateurId.genererIdLivre(), "Germinal", "Émile Zola", 1885, "Roman social"));
    }

    // Gestion des livres
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
    }

    public String ajouterLivre(String titre, String auteur, int anneePublication, String genre) {
        String id = GenerateurId.genererIdLivre();
        Livre livre = new Livre(id, titre, auteur, anneePublication, genre);
        livres.add(livre);
        return id;
    }

    public void supprimerLivre(String idLivre) {
        livres.removeIf(livre -> livre.getId().equals(idLivre));
    }

    public Livre trouverLivreParId(String id) {
        return livres.stream()
                .filter(livre -> livre.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Livre> rechercherLivresParTitre(String titre) {
        return livres.stream()
                .filter(livre -> livre.getTitre().toLowerCase().contains(titre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livre> rechercherLivresParAuteur(String auteur) {
        return livres.stream()
                .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livre> rechercherLivresParGenre(String genre) {
        return livres.stream()
                .filter(livre -> livre.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Gestion des utilisateurs
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public Utilisateur trouverUtilisateurParId(String id) {
        return utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Opérations d'emprunt
    public Emprunt emprunterLivre(String idLivre, String idUtilisateur) {
        Optional<Livre> livre = getLivreById(idLivre);
        Optional<Utilisateur> utilisateur = getUtilisateurById(idUtilisateur);

        if (livre.isPresent() && utilisateur.isPresent() && livre.get().isDisponible()) {
            livre.get().setDisponible(false);
            Emprunt emprunt = new Emprunt(GenerateurId.genererIdEmprunt(), livre.get(), utilisateur.get(), LocalDateTime.now());
            emprunts.add(emprunt);
            return emprunt;
        }
        return null;
    }

    public boolean retournerLivre(String idEmprunt) {
        Optional<Emprunt> emprunt = emprunts.stream()
                .filter(e -> e.getId().equals(idEmprunt) && !e.isEstRetourne())
                .findFirst();

        if (emprunt.isPresent()) {
            emprunt.get().setDateRetour(LocalDateTime.now());
            emprunt.get().getLivre().setDisponible(true);
            return true;
        }
        return false;
    }

    public List<Emprunt> getHistoriqueEmprunts() {
        return new ArrayList<>(emprunts);
    }

    public List<Emprunt> getEmpruntsEnCours() {
        return emprunts.stream()
                .filter(emprunt -> !emprunt.isEstRetourne())
                .collect(Collectors.toList());
    }

    public List<Emprunt> getEmpruntsUtilisateur(String idUtilisateur) {
        return emprunts.stream()
                .filter(emprunt -> emprunt.getUtilisateur().getId().equals(idUtilisateur))
                .collect(Collectors.toList());
    }

    // Statistiques
    public List<Livre> getLivresDisponibles() {
        return livres.stream()
                .filter(livre -> livre.isDisponible())
                .collect(Collectors.toList());
    }

    public List<Livre> getLivresEmpruntes() {
        return livres.stream()
                .filter(livre -> !livre.isDisponible())
                .collect(Collectors.toList());
    }

    public List<Livre> getHistoriqueEmpruntUtilisateur(String idUtilisateur) {
        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        return utilisateur != null ? utilisateur.getHistoriquePrets() : new ArrayList<>();
    }

    // Système de notation
    public void noterLivre(String idLivre, double note) {
        Livre livre = trouverLivreParId(idLivre);
        if (livre != null) {
            livre.setNote(note);
        }
    }

    // Nouvelles méthodes de listage
    public void listerUtilisateurs() {
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
        } else {
            utilisateurs.forEach(u -> {
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
            livres.forEach(l -> {
                System.out.println("ID: " + l.getId() + " | Titre: " + l.getTitre() + 
                                 " | Auteur: " + l.getAuteur() + 
                                 " | Disponible: " + (l.isDisponible() ? "Oui" : "Non"));
            });
        }
    }

    public void listerEmpruntsEnCours() {
        boolean aucunEmprunt = true;
        for (Utilisateur u : utilisateurs) {
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
        return utilisateurs.stream()
                .anyMatch(u -> !u.getLivresEmpruntes().isEmpty());
    }

    private Optional<Livre> getLivreById(String id) {
        return livres.stream()
                .filter(livre -> livre.getId().equals(id))
                .findFirst();
    }

    private Optional<Utilisateur> getUtilisateurById(String id) {
        return utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getId().equals(id))
                .findFirst();
    }
}
