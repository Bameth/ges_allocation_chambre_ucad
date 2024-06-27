package exam.glrsa.views;

import java.util.List;
import java.util.Scanner;

import exam.glrsa.core.ViewImpl;
import exam.glrsa.data.entity.Pavillon;
import exam.glrsa.services.PavillonService;

public class PavillonView extends ViewImpl<Pavillon> {
    private PavillonService pavillonService;

    public PavillonView(Scanner scanner, PavillonService pavillonService) {
        super(scanner);
        this.pavillonService = pavillonService;
    }

    @Override
    public Pavillon saisie() {
        Pavillon pavillon = new Pavillon();
        System.out.println("Veuillez saisir les informations du Pavillon :");

        System.out.print("Quel est le nombre d'étages du pavillon ? : ");
        int nbreEtage = scanner.nextInt();
        pavillon.setNbreEtage(nbreEtage);
        return pavillon;
    }

    public void affiche(List<Pavillon> pavillons) {
        for (Pavillon pavillon : pavillons) {
            System.out.println(pavillon);
        }
    }

    public Pavillon modification() {
        System.out.println("Veuillez saisir les informations de modification du Pavillon :");

        System.out.print("Numero Pavillon : ");
        String numeroPavillon = scanner.next();
        Pavillon pavillon = pavillonService.selectByNumero(numeroPavillon);

        if (pavillon == null) {
            System.out.println("Le pavillon avec le numéro spécifié n'existe pas.");
            return null;
        }

        System.out.print("Nouveau Nombre d'Etages : ");
        int nbreEtage = scanner.nextInt();
        pavillon.setNbreEtage(nbreEtage);

        return pavillon;
    }
}
