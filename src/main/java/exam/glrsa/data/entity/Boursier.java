package exam.glrsa.data.entity;

import exam.glrsa.data.enums.TypeBourse;

public class Boursier extends Etudiant {
    private TypeBourse typeBourse;
    private Chambre chambre;

    public Boursier(TypeBourse typeBourse) {
        this.typeBourse = typeBourse;
    }

    @Override
    public TypeBourse getTypeBourse() {
        return typeBourse;
    }
    @Override
    public void setTypeBourse(TypeBourse typeBourse) {
        this.typeBourse = typeBourse;
    }
    @Override
    public Chambre getChambre() {
        return chambre;
    }
    @Override
    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }
}
