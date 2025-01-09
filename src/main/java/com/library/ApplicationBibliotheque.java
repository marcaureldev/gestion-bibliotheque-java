package com.library;

import com.library.model.Livre;
import com.library.model.Utilisateur;
import com.library.model.Emprunt;
import com.library.service.ServiceBibliotheque;
import com.library.service.ClassementService;
import com.library.utils.GenerateurId;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class ApplicationBibliotheque {
    private static ServiceBibliotheque serviceBibliotheque;
    private static ClassementService classementService;
    private static Scanner scanner;

    public static void main(String[] args) {
        serviceBibliotheque = new ServiceBibliotheque();
        classementService = new ClassementService(serviceBibliotheque);
        scanner = new Scanner(System.in);

        while (true) {
            afficherMenu();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    ajouterLivre();
                    break;
                case "2":
                    rechercherLivres();
                    break;
                case "3":
                    emprunterLivre();
                    break;
                case "4":
                    rendreLivre();
                    break;
                case "5":
                    afficherStatistiques();
                    break;
                case "6":
                    supprimerLivre();
                    break;
                case "7":
                    ajouterUtilisateur();
                    break;
                case "8":
                    noterLivre();
                    break;
                case "9":
                    listerUtilisateurs();
                    break;
                case "10":
                    afficherTop10Livres();
                    break;
                case "11":
                    afficherHistoriqueEmprunts();
                    break;
                case "0":
                    System.out.println("Au revoir !");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Option invalide !");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Rechercher des livres");
        System.out.println("3. Emprunter un livre");
        System.out.println("4. Retourner un livre");
        System.out.println("5. Afficher les statistiques");
        System.out.println("6. Supprimer un livre");
        System.out.println("7. Ajouter un utilisateur");
        System.out.println("8. Noter un livre");
        System.out.println("9. Lister les utilisateurs");
        System.out.println("10. Top 10 des livres les plus empruntés");
        System.out.println("11. Afficher l'historique des emprunts");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    private static void ajouterLivre() {
        System.out.println("\n=== Ajouter un livre ===");

        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();

        int anneePublication = 0;
        boolean anneeValide = false;
        int anneeActuelle = Year.now().getValue();

        while (!anneeValide) {
            System.out.print("Année de publication : ");
            try {
                anneePublication = Integer.parseInt(scanner.nextLine());
                if (anneePublication <= anneeActuelle) {
                    anneeValide = true;
                } else {
                    System.out.println("L'année de publication ne peut pas être supérieure à l'année en cours (" + anneeActuelle + ").");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer une année valide.");
            }
        }

        System.out.print("Genre : ");
        String genre = scanner.nextLine();

        Livre nouveauLivre = new Livre(GenerateurId.genererIdLivre(), titre, auteur, anneePublication, genre);
        serviceBibliotheque.ajouterLivre(nouveauLivre);
        classementService.ajouterLivre(nouveauLivre);  // Ajouter le livre au service de classement
        System.out.println("Livre ajouté avec succès !");
    }

    private static void rechercherLivres() {
        System.out.println("\n=== Rechercher des livres ===");
        System.out.println("1. Par titre");
        System.out.println("2. Par auteur");
        System.out.println("3. Par genre");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

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

        // Vérifier s'il y a des utilisateurs
        if (serviceBibliotheque.nombreUtilisateurs() == 0) {
            System.out.println("\nAucun utilisateur enregistré. Veuillez d'abord ajouter un utilisateur.");
            return;
        }

        // Vérifier s'il y a des livres disponibles
        List<Livre> livresDisponibles = serviceBibliotheque.getLivresDisponibles();
        if (livresDisponibles.isEmpty()) {
            System.out.println("\nAucun livre disponible pour l'emprunt.");
            return;
        }

        // Afficher la liste des utilisateurs
        System.out.println("\nListe des utilisateurs :");
        serviceBibliotheque.listerUtilisateurs();

        // Afficher la liste des livres disponibles
        System.out.println("\nLivres disponibles :");
        livresDisponibles.forEach(System.out::println);

        System.out.print("\nID de l'utilisateur (ex: U001) : ");
        String idUtilisateur = scanner.nextLine();
        System.out.print("ID du livre (ex: L001) : ");
        String idLivre = scanner.nextLine();

        Emprunt emprunt = serviceBibliotheque.emprunterLivre(idLivre, idUtilisateur);
        if (emprunt != null) {
            System.out.println("Livre emprunté avec succès! ID de l'emprunt : " + emprunt.getId());
        } else {
            System.out.println("Impossible d'emprunter le livre. Vérifiez les IDs et la disponibilité du livre.");
        }
    }

    private static void rendreLivre() {
        System.out.println("\n=== Retour d'un livre ===");

        // Afficher la liste des emprunts en cours
        List<Emprunt> empruntsEnCours = serviceBibliotheque.getEmpruntsEnCours();
        if (empruntsEnCours.isEmpty()) {
            System.out.println("\nAucun emprunt en cours.");
            return;
        }

        System.out.println("\nEmprunts en cours :");
        for (Emprunt emprunt : empruntsEnCours) {
            System.out.println("ID Emprunt: " + emprunt.getId() +
                    " | Livre: " + emprunt.getLivre().getTitre() +
                    " | Emprunteur: " + emprunt.getUtilisateur().getNom() +
                    " | Date d'emprunt: " + emprunt.getDateEmprunt());
        }

        System.out.print("\nID de l'emprunt (ex: E001) : ");
        String idEmprunt = scanner.nextLine();

        if (serviceBibliotheque.retournerLivre(idEmprunt)) {
            System.out.println("Livre retourné avec succès!");
        } else {
            System.out.println("Impossible de retourner le livre. Vérifiez l'ID de l'emprunt.");
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
        classementService.supprimerLivre(idLivre); // Supprimer le livre du service de classement
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

        // Vérifier s'il y a des livres
        if (serviceBibliotheque.nombreLivres() == 0) {
            System.out.println("\nAucun livre dans la bibliothèque.");
            return;
        }

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
        if (serviceBibliotheque.nombreUtilisateurs() == 0) {
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }
        serviceBibliotheque.listerUtilisateurs();
    }

    private static void listerLivres() {
        System.out.println("\n=== Liste des Livres ===");
        if (serviceBibliotheque.nombreLivres() == 0) {
            System.out.println("Aucun livre dans la bibliothèque.");
            return;
        }
        serviceBibliotheque.listerTousLesLivres();
    }

    private static void afficherHistoriqueEmprunts() {
        System.out.println("\n=== Historique des Emprunts ===");

        List<Emprunt> historique = serviceBibliotheque.getHistoriqueEmprunts();
        if (historique.isEmpty()) {
            System.out.println("Aucun emprunt dans l'historique.");
            return;
        }

        System.out.println("\nTous les emprunts :");
        for (Emprunt emprunt : historique) {
            String statut = emprunt.isEstRetourne() ?
                    "Retourné le " + emprunt.getDateRetour() :
                    "En cours";

            System.out.println("ID Emprunt: " + emprunt.getId() +
                    " | Livre: " + emprunt.getLivre().getTitre() +
                    " | Emprunteur: " + emprunt.getUtilisateur().getNom() +
                    " | Date d'emprunt: " + emprunt.getDateEmprunt() +
                    " | Statut: " + statut);
        }
    }

    private static void afficherTop10Livres() {
        List<Livre> top10 = classementService.getTop10LivresEmpruntes();
        if (top10.isEmpty()) {
            System.out.println("Aucun livre n'a encore été emprunté.");
            return;
        }

        System.out.println("\n=== Top 10 des Livres les Plus Empruntés ===");
        for (int i = 0; i < top10.size(); i++) {
            Livre livre = top10.get(i);
            System.out.printf("%d. %s par %s - %d emprunts%n",
                    i + 1,
                    livre.getTitre(),
                    livre.getAuteur(),
                    livre.getNombreEmprunts());
        }
    }
}
