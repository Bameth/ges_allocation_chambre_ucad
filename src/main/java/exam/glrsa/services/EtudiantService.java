package exam.glrsa.services;

import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.core.Service;
import exam.glrsa.data.entity.Etudiant;

public class EtudiantService implements Service<Etudiant> {
    private Repository<Etudiant> etudiantRepositoryImpl;
    public EtudiantService(Repository<Etudiant> etudiantRepositoryImpl) {
        this.etudiantRepositoryImpl = etudiantRepositoryImpl;
    }

    @Override
    public boolean save(Etudiant objet) {
        return etudiantRepositoryImpl.insert(objet);
    }

    @Override
    public List<Etudiant> show() {
        return etudiantRepositoryImpl.selectAll();
    }

    @Override
    public Etudiant getBy(String value) {
        return etudiantRepositoryImpl.selectByNumero(value);
    }
    
    public List<Etudiant> getByChambre(String value) {
        return etudiantRepositoryImpl.selectBy(value);
    }
    

    @Override
    public int count() {
        return etudiantRepositoryImpl.count();
    }
    
    public boolean update(Etudiant etudiant) {
        return etudiantRepositoryImpl.update(etudiant);
    }
}
