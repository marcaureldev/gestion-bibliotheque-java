package com.library.utils;

/**
 * Classe utilitaire pour générer des IDs uniques
 */
public class GenerateurId {
    private static int dernierIdUtilisateur = 0;
    private static int dernierIdLivre = 0;
    private static int dernierIdEmprunt = 0;

    /**
     * Génère un ID unique pour un utilisateur au format U001, U002, etc.
     */
    public static synchronized String genererIdUtilisateur() {
        dernierIdUtilisateur++;
        return String.format("U%03d", dernierIdUtilisateur);
    }

    /**
     * Génère un ID unique pour un livre au format L001, L002, etc.
     */
    public static synchronized String genererIdLivre() {
        dernierIdLivre++;
        return String.format("L%03d", dernierIdLivre);
    }

    /**
     * Génère un ID unique pour un emprunt au format E001, E002, etc.
     */
    public static synchronized String genererIdEmprunt() {
        dernierIdEmprunt++;
        return String.format("E%03d", dernierIdEmprunt);
    }
}
