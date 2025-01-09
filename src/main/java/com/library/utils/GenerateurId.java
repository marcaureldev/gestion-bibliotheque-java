package com.library.utils;


public class GenerateurId {
    private static int dernierIdUtilisateur = 0;
    private static int dernierIdLivre = 0;
    private static int dernierIdEmprunt = 0;

    
    public static synchronized String genererIdUtilisateur() {
        dernierIdUtilisateur++;
        return String.format("U%03d", dernierIdUtilisateur);
    }

   
    public static synchronized String genererIdLivre() {
        dernierIdLivre++;
        return String.format("L%03d", dernierIdLivre);
    }

    
    public static synchronized String genererIdEmprunt() {
        dernierIdEmprunt++;
        return String.format("E%03d", dernierIdEmprunt);
    }
}
