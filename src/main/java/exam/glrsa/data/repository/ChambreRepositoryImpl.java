package exam.glrsa.data.repository;

import java.util.ArrayList;
import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Chambre;

public class ChambreRepositoryImpl implements Repository<Chambre> {
    private List<Chambre> chambres = new ArrayList<>();

    @Override
    public boolean insert(Chambre chambre) {
        if (chambre.getPavillon() == null) {
            return false; 
        }

        for (Chambre existingChambre : chambres) {
            if (existingChambre.getPavillon() != null &&
                    existingChambre.getPavillon().getNumPavillon().equals(chambre.getPavillon().getNumPavillon()) &&
                    existingChambre.getNumeroEtage() == chambre.getNumeroEtage() &&
                    existingChambre.getTypecChambre() == chambre.getTypecChambre()) {
                return false; 
            }
        }

        return chambres.add(chambre);
    }

    @Override
    public boolean update(Chambre chambre) {
        for (int i = 0; i < chambres.size(); i++) {
            if (chambres.get(i).getNumeroChambre().equals(chambre.getNumeroChambre())) {
                chambres.set(i, chambre);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Chambre> selectAll() {
        return chambres;
    }

    @Override
    public Chambre selectByNumero(String numero) {
        for (Chambre chambre : chambres) {
            if (chambre.getNumeroChambre().equals(numero)) {
                return chambre;
            }
        }
        return null;
    }

    @Override
    public int count() {
        return chambres.size();
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Chambre selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public List<Chambre> selectBy(String numero) {
        List<Chambre> chambreInPavillon = new ArrayList<>();
        for (Chambre chambre : chambres) {
            if (chambre.getPavillon() != null && chambre.getPavillon().getNumPavillon().equals(numero)) {
                chambreInPavillon.add(chambre);
            }
        }
        return chambreInPavillon;
    }

}
