package exam.glrsa;

import java.util.List;
import java.util.Scanner;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Chambre;
import exam.glrsa.data.entity.Etudiant;
import exam.glrsa.data.entity.Pavillon;
import exam.glrsa.data.repository.EtudiantBdImpl;
import exam.glrsa.data.repository.EtudiantRepositoryBDO;
import exam.glrsa.services.ChambreService;
import exam.glrsa.services.EtudiantService;
import exam.glrsa.services.PavillonService;
import exam.glrsa.views.ChambreView;
import exam.glrsa.views.EtudiantView;
import exam.glrsa.views.PavillonView;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Repository<Etudiant> etudiantRepository=new EtudiantBdImpl();
        EtudiantService etudiantService = new EtudiantService(etudiantRepository);
        EtudiantView etudiantView = new EtudiantView(scanner);
        PavillonService pavillonService = new PavillonService();
        PavillonView pavillonView = new PavillonView(scanner, pavillonService);
        ChambreService chambreService = new ChambreService();
        ChambreView chambreView = new ChambreView(scanner, pavillonService, chambreService, etudiantService);
        int choix;
        do {
            choix = menu();
            switch (choix) {
                case 1:
                    pavillonService.save(pavillonView.saisie());
                    break;
                case 2:
                    Pavillon pavillonModifie = pavillonView.modification();
                    if (pavillonModifie != null) {
                        if (pavillonService.update(pavillonModifie)) {
                            System.out.println("Pavillon modifié avec succès !");
                        } else {
                            System.out.println("Erreur lors de la modification du pavillon.");
                        }
                    }
                    break;
                case 3:
                    pavillonView.affiche(pavillonService.show());
                    break;
                case 4:
                    chambreService.save(chambreView.saisie());
                    break;
                case 5:
                    Chambre chambreModifiee = chambreView.modification();
                    if (chambreModifiee != null) {
                        if (chambreService.update(chambreModifiee)) {
                            System.out.println("Chambre modifiée avec succès !");
                        } else {
                            System.out.println("Erreur lors de la modification de la chambre.");
                        }
                    }
                    break;
                case 6:
                    chambreService.save(chambreView.affecterChambre());
                    break;
                case 7:
                    chambreView.affiche(chambreService.show());
                    break;
                case 8:
                    System.out.print("Veuillez entrer le numéro du pavillon : ");
                    String numeroPavillon = scanner.next();
                    List<Chambre> chambreDePavillon = chambreService.getbyPavillonChambres(numeroPavillon);
                    if (chambreDePavillon.isEmpty()) {
                        System.out.println("Aucun étudiant trouvé dans cette chambre.");
                    } else {
                        chambreView.affiche(chambreDePavillon);
                    }
                    break;
                case 9:
                    etudiantService.save(etudiantView.saisie());
                    break;
                case 10:
                    etudiantView.affiche(etudiantService.show());
                    break;
                case 11:
                    System.out.print("Veuillez entrer le numéro de la chambre : ");
                    String numeroChambre = scanner.next();
                    List<Etudiant> etudiantsDeChambre = etudiantService.getByChambre(numeroChambre);
                    if (etudiantsDeChambre.isEmpty()) {
                        System.out.println("Aucun étudiant trouvé dans cette chambre.");
                    } else {
                        etudiantView.affiche(etudiantsDeChambre);
                    }
                    break;
            }
        } while (choix != 12);
    }

    public static int menu() {
        System.out.println("1. Ajouter Pavillon");
        System.out.println("2. Modifier Pavillon");
        System.out.println("3. Lister Pavillons");
        System.out.println("4. Ajouter Chambre");
        System.out.println("5. Modifier Chambre");
        System.out.println("6. Affecter chambre a un etudiant");
        System.out.println("7. Lister Chambres");
        System.out.println("8. Lister Chambres d'un Pavillon");
        System.out.println("9. Ajouter Etudiant");
        System.out.println("10. Lister Etudiants");
        System.out.println("11. Lister les Etudiants d'une Chambre");
        System.out.println("12. Quitter");
        return scanner.nextInt();
    }
}
