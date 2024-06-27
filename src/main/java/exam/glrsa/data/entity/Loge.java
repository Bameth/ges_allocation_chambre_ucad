package exam.glrsa.data.entity;

import exam.glrsa.data.enums.TypeBourse;

public class Loge extends Boursier {
    private Chambre chambre;

    public Loge(TypeBourse typeBourse, Chambre chambre) {
        super(typeBourse);
        this.chambre = chambre;
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
