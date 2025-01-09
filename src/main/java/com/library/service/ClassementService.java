package com.library.service;

import com.library.model.Livre;
import com.library.service.ServiceBibliotheque;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ClassementService {
    private ServiceBibliotheque serviceBibliotheque;

    public ClassementService(ServiceBibliotheque serviceBibliotheque) {
        this.serviceBibliotheque = serviceBibliotheque;
    }

    public void ajouterLivre(Livre livre) {
        serviceBibliotheque.ajouterLivre(livre);
    }

    public List<Livre> getTopLivresEmpruntes(int limite) {
        return serviceBibliotheque.getLivresEmpruntes().stream()
                .sorted(Comparator.comparingInt(Livre::getNombreEmprunts).reversed())
                .limit(limite)
                .collect(Collectors.toList());
    }

    public List<Livre> getTop10LivresEmpruntes() {
        return getTopLivresEmpruntes(10);
    }

    public List<Livre> getLivresParNombreEmprunts() {
        return serviceBibliotheque.getLivresEmpruntes().stream()
                .sorted(Comparator.comparingInt(Livre::getNombreEmprunts).reversed())
                .collect(Collectors.toList());
    }

    public void supprimerLivre(String idLivre) {
        serviceBibliotheque.supprimerLivre(idLivre);
    }
}
