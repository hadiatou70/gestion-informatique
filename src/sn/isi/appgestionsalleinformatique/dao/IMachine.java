package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.Machine;

import java.util.List;

public interface IMachine {
    public int persist(Machine machine) throws Exception;
    public int remove(int id) throws Exception;
    public int update(Machine machine) throws Exception;
    public List<Machine> getAll() throws Exception;
    public Machine find(int id) throws Exception;
}
