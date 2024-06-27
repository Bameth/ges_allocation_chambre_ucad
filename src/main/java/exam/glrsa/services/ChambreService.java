package exam.glrsa.services;

import java.util.List;

import exam.glrsa.core.Service;
import exam.glrsa.data.entity.Chambre;
import exam.glrsa.data.repository.ChambreRepositoryImpl;

public class ChambreService implements Service<Chambre> {
    private ChambreRepositoryImpl chambreRepository = new ChambreRepositoryImpl();

    @Override
    public boolean save(Chambre chambre) {
        return chambreRepository.insert(chambre);
    }

    @Override
    public List<Chambre> show() {
        return chambreRepository.selectAll();
    }

    @Override
    public Chambre getBy(String value) {
        return chambreRepository.selectByNumero(value);
    }
    public List<Chambre> getbyPavillonChambres(String value) {
        return chambreRepository.selectBy(value);
    }

    @Override
    public int count() {
        return chambreRepository.count();
    }

    public boolean update(Chambre chambre) {
        return chambreRepository.update(chambre);
    }
}
