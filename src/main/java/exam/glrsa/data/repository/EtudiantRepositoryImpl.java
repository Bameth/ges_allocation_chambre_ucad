package exam.glrsa.data.repository;

import java.util.ArrayList;
import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Etudiant;

public class EtudiantRepositoryImpl implements Repository<Etudiant> {
    private List<Etudiant> etudiants = new ArrayList<>();

    @Override
    public boolean insert(Etudiant etudiant) {
        if (!etudiants.contains(etudiant)) {
            return etudiants.add(etudiant);
        }
        return false;
    }

    @Override
    public boolean update(Etudiant objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Etudiant> selectAll() {
        return etudiants;
    }

    @Override
    public Etudiant selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Etudiant selectByNumero(String numero) {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getMatricule().equals(numero)) {
                return etudiant;
            }
        }
        return null;
    }

    @Override
    public List<Etudiant> selectBy(String numero) {
        List<Etudiant> etudiantsInChambre = new ArrayList<>();
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getChambre() != null && etudiant.getChambre().getNumeroChambre().equals(numero)) {
                etudiantsInChambre.add(etudiant);
            }
        }
        return etudiantsInChambre;
    }


}
