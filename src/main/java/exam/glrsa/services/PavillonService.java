package exam.glrsa.services; 

import java.util.List;
import exam.glrsa.core.Service;
import exam.glrsa.data.entity.Pavillon;
import exam.glrsa.data.repository.PavillonRepositoryImpl;

public class PavillonService implements Service<Pavillon> {
    private PavillonRepositoryImpl pavEtudiantRepositoryImpl = new PavillonRepositoryImpl();

    @Override
    public boolean save(Pavillon objet) {
        return pavEtudiantRepositoryImpl.insert(objet);
    }

    @Override
    public List<Pavillon> show() {
        return pavEtudiantRepositoryImpl.selectAll();
    }

    @Override
    public Pavillon getBy(String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
    
    public Pavillon selectByNumero(String numero) {
        return pavEtudiantRepositoryImpl.selectByNumero(numero);
    }

    public boolean update(Pavillon pavillon) {
        return pavEtudiantRepositoryImpl.update(pavillon);
    }
}
