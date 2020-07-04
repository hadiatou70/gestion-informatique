package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.Salle;

import java.util.List;

public interface ISalle {
    public int persist(Salle salle) throws Exception;
    public int remove(int id) throws Exception;
    public int update(Salle salle) throws Exception;
    public List<Salle> getAll() throws Exception;
    public Salle find(int id) throws Exception;
}
