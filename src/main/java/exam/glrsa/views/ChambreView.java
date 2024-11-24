package exam.glrsa.views;

import java.util.Scanner;
import exam.glrsa.core.ViewImpl;
import exam.glrsa.data.entity.Chambre;
import exam.glrsa.data.entity.Etudiant;
import exam.glrsa.data.entity.Pavillon;
import exam.glrsa.data.enums.TypeChambre;
import exam.glrsa.data.enums.TypeEtat;
import exam.glrsa.services.ChambreService;
import exam.glrsa.services.EtudiantService;
import exam.glrsa.services.PavillonService;

public class ChambreView extends ViewImpl<Chambre> {
    private PavillonService pavillonService;
    private ChambreService chambreService;
    private EtudiantService etudiantService;

    public ChambreView(Scanner scanner, PavillonService pavillonService, ChambreService chambreService,
            EtudiantService etudiantService) {
        super(scanner);
        this.pavillonService = pavillonService;
        this.chambreService = chambreService;
        this.etudiantService = etudiantService;
    }

    @Override
    public Chambre saisie() {
        Chambre chambre = new Chambre();
        System.out.println("Liste des pavillons disponibles :");
        System.out.println("==================================");
        for (Pavillon pavillon : pavillonService.show()) {
            System.out.println(pavillon.getNumPavillon());
        }
        System.out.println("===================================");
        System.out.println("Veuillez saisir les informations de la chambre :");
        System.out.print("Numero Etage : ");
        int numeroEtage = scanner.nextInt();
        TypeChambre typeChambre = saisieChambre();
        System.out.print("Entrez le numéro de pavillon auquel vous ajoutez cette chambre : ");
        String numeroPavillon = scanner.next();
        Pavillon pavillon = pavillonService.selectByNumero(numeroPavillon);
        if (pavillon == null) {
            System.out.println("Le pavillon avec le numéro spécifié n'existe pas.");
            return null;
        }
        if (pavillon.getNbreEtage() < numeroEtage) {
            System.out.println("Le numéro d'étage entré ne correspond pas au nombre d'étages du pavillon.");
            return null;
        }
        boolean chambreExist = pavillon.getChambres().stream()
                .anyMatch(c -> c.getNumeroEtage() == numeroEtage && c.getTypecChambre() == typeChambre);
        if (!chambreExist) {
            chambre.setNumeroEtage(numeroEtage);
            chambre.setTypecChambre(typeChambre);
            chambre.setPavillon(pavillon);
            if (chambreService.save(chambre)) {
                pavillon.getChambres().add(chambre);
                System.out.println("Chambre ajoutée avec succès !");
                return chambre;
            } else {
                System.out.println("Erreur lors de l'ajout de la chambre.");
                return null;
            }
        } else {
            System.out.println("La chambre existe déjà dans ce pavillon.");
            return null;
        }
    }

    public Chambre modification() {
        System.out.println("Veuillez saisir le numéro de la chambre à modifier : ");
        System.out.println("======================================================");
        String numeroChambre = scanner.next();
        Chambre chambre = chambreService.getBy(numeroChambre);

        if (chambre == null) {
            System.out.println("La chambre avec le numéro spécifié n'existe pas.");
            return null;
        }
        System.out.println("Modification des informations de la chambre :");
        System.out.print("Nouveau Numero Etage : ");
        int numeroEtage = scanner.nextInt();
        chambre.setNumeroEtage(numeroEtage);
        TypeChambre typeChambre = saisieChambre();
        chambre.setTypecChambre(typeChambre);
        System.out.print("Entrez le numéro de pavillon : ");
        String numeroPavillon = scanner.next();
        Pavillon pavillon = pavillonService.selectByNumero(numeroPavillon);
        if (pavillon == null) {
            System.out.println("Le pavillon avec le numéro spécifié n'existe pas.");
            return null;
        }
        if (pavillon.getNbreEtage() < numeroEtage) {
            System.out.println("Le numéro d'étage entré ne correspond pas au nombre d'étages du pavillon.");
            return null;
        }
        chambre.setPavillon(pavillon);

        return chambre;
    }

    public Chambre affecterChambre() {
        System.out.println("Veuillez saisir le numéro de la chambre à affecter : ");
        System.out.println("===================================================");
        String numeroChambre = scanner.next();
        Chambre chambre = chambreService.getBy(numeroChambre);
        if (chambre == null) {
            System.out.println("La chambre avec le numéro spécifié n'existe pas.");
            return null;
        }
        System.out.println("Veuillez saisir le numéro de l'étudiant à affecter : ");
        String numeroEtudiant = scanner.next();
        Etudiant etudiant = etudiantService.getBy(numeroEtudiant);
        if (etudiant == null) {
            System.out.println("L'étudiant avec le numéro spécifié n'existe pas.");
            return null;
        }
        if (etudiant.getNonBoursier() != null) {
            System.out.println("Cet étudiant est non boursier et ne peut pas être affecté à une chambre.");
            return null;
        }
        chambre.setEtudiant(etudiant);
        etudiant.setChambre(chambre);
        return chambre;

    }
    public Chambre archiverChambre() {
        System.out.println("Veuillez saisir le numéro de la chambre à archiver : ");
        String numeroChambre = scanner.next();
        Chambre chambre = chambreService.getBy(numeroChambre);
        
        if (chambre == null) {
            System.out.println("La chambre avec le numéro spécifié n'existe pas.");
            return null;
        }
        
        chambre.setTypeEtat(TypeEtat.ARCHIVER);
        if (chambreService.update(chambre)) {
            System.out.println("Chambre archivée avec succès !");
        } else {
            System.out.println("Erreur lors de l'archivage de la chambre.");
        }   
        
        return chambre;
    }
    
    private TypeChambre saisieChambre() {
        int chambreChoice;
        do {
            for (TypeChambre chambre : TypeChambre.values()) {
                System.out.println((chambre.ordinal() + 1) + "-" + chambre.name());
            }
            System.out.println("Veuillez sélectionner une Chambre : ");
            chambreChoice = scanner.nextInt();
        } while (chambreChoice <= 0 || chambreChoice > TypeChambre.values().length);

        return TypeChambre.values()[chambreChoice - 1];
    }
    public TypeEtat saisieEtat() {
        int etatChoice;
        do {
            for (TypeEtat etat: TypeEtat.values()) {
                System.out.println((etat.ordinal() + 1) + "-" + etat.name());
            }
            System.out.println("Veuillez sélectionner un Etat : ");
            etatChoice = scanner.nextInt();
        } while (etatChoice <= 0 || etatChoice > TypeEtat.values().length);

        return TypeEtat.values()[etatChoice - 1];
    }

}
