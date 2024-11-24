package exam.glrsa.views;

import java.time.LocalDate;
import java.util.Scanner;

import exam.glrsa.core.ViewImpl;
import exam.glrsa.data.entity.Etudiant;
import exam.glrsa.data.entity.NonBoursier;
import exam.glrsa.data.enums.TypeBourse;

public class EtudiantView extends ViewImpl<Etudiant> {

    public EtudiantView(Scanner scanner) {
        super(scanner);
    }

    @Override
    public Etudiant saisie() {
        Etudiant etudiant = new Etudiant();
        System.out.println("Veuillez saisir les informations de l'étudiant :");
        System.out.println("===============================================");
        System.out.print("Nom : ");
        String nom = scanner.next();
        etudiant.setNom(nom);
        System.out.print("Prénom : ");
        String prenom = scanner.next();
        etudiant.setPrenom(prenom);
        scanner.nextLine();
        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();
        etudiant.setTelephone(telephone);
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        String dateNaissanceStr = scanner.next();
        LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);
        etudiant.setDateNaissance(dateNaissance);
        System.out.print("Est-ce que l'étudiant est boursier ? (oui/non) : ");
        String choixBoursier = scanner.next();
        scanner.nextLine();

        if (choixBoursier.equalsIgnoreCase("oui")) {
            TypeBourse bourse = saisieBourse();
            etudiant.setTypeBourse(bourse);
        } else {
            System.out.print("Adresse de domicile : ");
            String adresse = scanner.nextLine();
            NonBoursier nonBoursier = new NonBoursier();
            nonBoursier.setAdresse(adresse);
            etudiant.setNonBoursier(nonBoursier);
        }
        return etudiant;    
    }

    private TypeBourse saisieBourse() {
        int bourseChoice;
        do {
            for (TypeBourse bourse : TypeBourse.values()) {
                System.out.println((bourse.ordinal() + 1) + "-" + bourse.name());
            }
            System.out.println("Veuillez sélectionner une Bourse : ");
            bourseChoice = scanner.nextInt();
        } while (bourseChoice <= 0 || bourseChoice > TypeBourse.values().length);

        return TypeBourse.values()[bourseChoice - 1];
    }

    
}
