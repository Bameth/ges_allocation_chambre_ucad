package exam.glrsa.services;

import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.core.Service;
import exam.glrsa.data.entity.Chambre;


public class ChambreService implements Service<Chambre> {
    private Repository<Chambre> chambreRepositoryImpl;
    public ChambreService(Repository<Chambre> chambreRepositoryImpl) {
        this.chambreRepositoryImpl = chambreRepositoryImpl;
    }

    @Override
    public boolean save(Chambre chambre) {
        return chambreRepositoryImpl.insert(chambre);
    }

    @Override
    public List<Chambre> show() {
        return chambreRepositoryImpl.selectAll();
    }

    @Override
    public Chambre getBy(String value) {
        return chambreRepositoryImpl.selectByNumero(value);
    }
    public List<Chambre> getbyPavillonChambres(String value) {
        return chambreRepositoryImpl.selectBy(value);
    }

    @Override
    public int count() {
        return chambreRepositoryImpl.count();
    }

    public boolean update(Chambre chambre) {
        return chambreRepositoryImpl.update(chambre);
    }
}
