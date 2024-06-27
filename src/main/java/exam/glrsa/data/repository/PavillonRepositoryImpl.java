package exam.glrsa.data.repository;

import java.util.ArrayList;
import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Pavillon;

public class PavillonRepositoryImpl implements Repository<Pavillon> {
    private List<Pavillon> pavillons = new ArrayList<>();

    @Override
    public boolean insert(Pavillon pavillon) {
        if (!pavillons.contains(pavillon)) {
            return pavillons.add(pavillon);
        }
        return false;
    }


    @Override
    public boolean update(Pavillon pavillon) {
        for (int i = 0; i < pavillons.size(); i++) {
            if (pavillons.get(i).getNumPavillon().equals(pavillon.getNumPavillon())) {
                pavillons.set(i, pavillon);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Pavillon pavillonToRemove = selectById(id);
        if (pavillonToRemove != null) {
            return pavillons.remove(pavillonToRemove);
        }
        return false;
    }

    @Override
    public List<Pavillon> selectAll() {
        return new ArrayList<>(pavillons); 
    }

    @Override
    public Pavillon selectById(int id) {
        for (Pavillon pavillon : pavillons) {
            if (pavillon.getId() == id) {
                return pavillon;
            }
        }
        return null;
    }

    @Override
    public int count() {
        return pavillons.size();
    }

    @Override
    public Pavillon selectByNumero(String numero) {
        for (Pavillon pavillon : pavillons) {
            if (pavillon.getNumPavillon().equalsIgnoreCase(numero)) {
                return pavillon;
            }
        }
        return null;
    }


    @Override
    public List<Pavillon> selectBy(String numero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBy'");
    }
}
