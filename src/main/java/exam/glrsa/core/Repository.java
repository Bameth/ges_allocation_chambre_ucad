package exam.glrsa.core;

import java.util.List;

public interface Repository<T> {
    boolean insert(T objet);
    boolean update(T objet);
    boolean delete(int id);
    List<T> selectAll();
    List<T> selectBy(String numero);
    T selectById(int id);
    int count();
    T selectByNumero(String numero);
}
