package com.library;

import com.library.model.Livre;
import com.library.model.Utilisateur;
import com.library.service.ServiceBibliotheque;
import com.library.utils.GenerateurId;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale de l'application de gestion de bibliothèque
 */
public class ApplicationBibliotheque {
    private static ServiceBibliotheque serviceBibliotheque;
    private static Scanner scanner;

    public static void main(String[] args) {
        serviceBibliotheque = new ServiceBibliotheque();
        scanner = new Scanner(System.in);

        while (true) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    ajouterLivre();
                    break;
                case 2:
                    rechercherLivres();
                    break;
                case 3:
                    emprunterLivre();
                    break;
                case 4:
                    rendreLivre();
                    break;
                case 5:
                    afficherStatistiques();
                    break;
                case 6:
                    supprimerLivre();
                    break;
                case 7:
                    ajouterUtilisateur();
                    break;
                case 8:
                    noterLivre();
                    break;
                case 9:
                    listerUtilisateurs();
                    break;
                case 10:
                    listerLivres();
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer s'il vous plaît.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n=== Bibliothèque Virtuelle ===");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Rechercher des livres");
        System.out.println("3. Emprunter un livre");
        System.out.println("4. Retourner un livre");
        System.out.println("5. Afficher les statistiques");
        System.out.println("6. Supprimer un livre");
        System.out.println("7. Ajouter un utilisateur");
        System.out.println("8. Noter un livre");
        System.out.println("9. Lister les utilisateurs");
        System.out.println("10. Lister tous les livres");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static void ajouterLivre() {
        System.out.println("\n=== Ajouter un livre ===");
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        System.out.print("Année de publication : ");
        int annee = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne
        System.out.print("Genre : ");
        String genre = scanner.nextLine();

        String id = GenerateurId.genererIdLivre();
        Livre livre = new Livre(id, titre, auteur, annee, genre);
        serviceBibliotheque.ajouterLivre(livre);
        System.out.println("Livre ajouté avec succès! ID: " + id);
    }

    private static void rechercherLivres() {
        System.out.println("\n=== Rechercher des livres ===");
        System.out.println("1. Par titre");
        System.out.println("2. Par auteur");
        System.out.println("3. Par genre");
        System.out.print("Votre choix : ");
        
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne
        
        System.out.print("Entrez votre recherche : ");
        String recherche = scanner.nextLine();
        
        List<Livre> resultats = null;
        switch (choix) {
            case 1:
                resultats = serviceBibliotheque.rechercherLivresParTitre(recherche);
                break;
            case 2:
                resultats = serviceBibliotheque.rechercherLivresParAuteur(recherche);
                break;
            case 3:
                resultats = serviceBibliotheque.rechercherLivresParGenre(recherche);
                break;
            default:
                System.out.println("Option invalide.");
                return;
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            resultats.forEach(System.out::println);
        }
    }

    private static void emprunterLivre() {
        System.out.println("\n=== Emprunter un livre ===");
        
        // Afficher la liste des utilisateurs
        System.out.println("\nListe des utilisateurs :");
        serviceBibliotheque.listerUtilisateurs();
        
        // Afficher la liste des livres disponibles
        System.out.println("\nLivres disponibles :");
        serviceBibliotheque.getLivresDisponibles().forEach(System.out::println);
        
        System.out.print("\nID de l'utilisateur (ex: U001) : ");
        String idUtilisateur = scanner.nextLine();
        System.out.print("ID du livre (ex: L001) : ");
        String idLivre = scanner.nextLine();

        if (serviceBibliotheque.emprunterLivre(idUtilisateur, idLivre)) {
            System.out.println("Livre emprunté avec succès!");
        } else {
            System.out.println("Impossible d'emprunter le livre. Vérifiez les IDs et la disponibilité du livre.");
        }
    }

    private static void rendreLivre() {
        System.out.println("\n=== Retourner un livre ===");
        
        // Afficher la liste des utilisateurs et leurs livres empruntés
        System.out.println("\nLivres actuellement empruntés :");
        serviceBibliotheque.listerEmpruntsEnCours();
        
        System.out.print("\nID de l'utilisateur (ex: U001) : ");
        String idUtilisateur = scanner.nextLine();
        System.out.print("ID du livre (ex: L001) : ");
        String idLivre = scanner.nextLine();

        if (serviceBibliotheque.rendreLivre(idUtilisateur, idLivre)) {
            System.out.println("Livre retourné avec succès!");
        } else {
            System.out.println("Impossible de retourner le livre. Vérifiez les IDs.");
        }
    }

    private static void afficherStatistiques() {
        System.out.println("\n=== Statistiques ===");
        System.out.println("Livres disponibles :");
        serviceBibliotheque.getLivresDisponibles().forEach(System.out::println);
        
        System.out.println("\nLivres empruntés :");
        serviceBibliotheque.getLivresEmpruntes().forEach(System.out::println);
    }

    private static void supprimerLivre() {
        System.out.println("\n=== Supprimer un livre ===");
        
        // Afficher la liste des livres
        System.out.println("\nListe des livres :");
        serviceBibliotheque.listerTousLesLivres();
        
        System.out.print("\nID du livre à supprimer (ex: L001) : ");
        String idLivre = scanner.nextLine();
        
        serviceBibliotheque.supprimerLivre(idLivre);
        System.out.println("Livre supprimé avec succès!");
    }

    private static void ajouterUtilisateur() {
        System.out.println("\n=== Ajouter un utilisateur ===");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();

        String id = GenerateurId.genererIdUtilisateur();
        Utilisateur utilisateur = new Utilisateur(id, nom, email);
        serviceBibliotheque.ajouterUtilisateur(utilisateur);
        System.out.println("Utilisateur ajouté avec succès! ID: " + id);
    }

    private static void noterLivre() {
        System.out.println("\n=== Noter un livre ===");
        
        // Afficher la liste des livres
        System.out.println("\nListe des livres :");
        serviceBibliotheque.listerTousLesLivres();
        
        System.out.print("\nID du livre (ex: L001) : ");
        String idLivre = scanner.nextLine();
        
        boolean noteValide = false;
        double note = 0.0;
        
        while (!noteValide) {
            System.out.print("Note (entre 0 et 5, par pas de 0.5) : ");
            try {
                String input = scanner.nextLine();
                note = Double.parseDouble(input);
                
                // Vérifie si la note est un multiple de 0.5 et comprise entre 0 et 5
                if (note >= 0 && note <= 5 && Math.abs(note * 2 - Math.round(note * 2)) < 0.001) {
                    noteValide = true;
                } else {
                    System.out.println("La note doit être comprise entre 0 et 5 et être un multiple de 0.5 (ex: 0, 0.5, 1, 1.5, ...)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }

        serviceBibliotheque.noterLivre(idLivre, note);
        System.out.println("Note ajoutée avec succès!");
    }

    private static void listerUtilisateurs() {
        System.out.println("\n=== Liste des Utilisateurs ===");
        serviceBibliotheque.listerUtilisateurs();
    }

    private static void listerLivres() {
        System.out.println("\n=== Liste des Livres ===");
        serviceBibliotheque.listerTousLesLivres();
    }
}
