package exam.glrsa.data.entity;

import java.time.LocalDate;

import exam.glrsa.data.enums.TypeBourse;
import exam.glrsa.data.enums.TypeChambre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String telephone;
    private LocalDate dateNaissance;
    private TypeBourse typeBourse;
    private TypeChambre typeChambre;
    private Chambre chambre;
    private NonBoursier nonBoursier;

    private static int nbre=0;

    public Etudiant() {
        id = ++nbre;
        matricule = generateNumero(id, "ET");
    }

    public String generateNumero(int nbre, String format) {
        int size = String.valueOf(nbre).length();
        return format + "0".repeat((4 - size) < 0 ? 0 : 4 - size) + nbre;
    }

    @Override
    public String toString() {
        return "Etudiant [id=" + id + 
                ", matricule=" + matricule + 
                ", nom=" + nom + 
                ", prenom=" + prenom +
                ", telephone=" + telephone + 
                ", dateNaissance=" + dateNaissance + 
                ", typeBourse=" + typeBourse +
                // ", typeChambre=" + (typeChambre != null ? typeChambre.get() + " " + etudiant.getNom() : "null") + 
                ", chambre=" + (chambre != null ? chambre.getNumeroChambre() : "null") + 
                ", adresse=" + ( nonBoursier!= null ? nonBoursier.getAdresse() : "null") + 
                "]";
    }

}
