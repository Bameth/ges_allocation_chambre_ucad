package exam.glrsa.data.entity;

import java.util.List;
import exam.glrsa.data.enums.TypeChambre;
import exam.glrsa.data.enums.TypeEtat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chambre {
    private static int nbre = 0;
    private int id;
    private String numeroChambre;
    private int numeroEtage;
    private TypeChambre typecChambre;
    private TypeEtat typeEtat;
    private Pavillon pavillon;
    private Etudiant etudiant;
    private List<Loge> loge;

    public Chambre() {
        this.id = ++nbre;
        this.numeroChambre = generateNumero(this.id, "CHA");
    }

    public String generateNumero(int nbre, String format) {
        int size = String.valueOf(nbre).length();
        return format + "0".repeat((4 - size) < 0 ? 0 : 4 - size) + nbre;
    }

    @Override
    public String toString() {
        return "Chambre(id=" + id +
                ", numeroChambre=" + numeroChambre +
                ", numeroEtage=" + numeroEtage +
                ", typecChambre=" + typecChambre +
                ", typeEtat=" + typeEtat +
                ", pavillon=" + (pavillon != null ? pavillon.getNumPavillon() : "null") +
                ", etudiant=" + (etudiant != null ? etudiant.getPrenom() + " " + etudiant.getNom() : "null") +
                ')';
    }
}
