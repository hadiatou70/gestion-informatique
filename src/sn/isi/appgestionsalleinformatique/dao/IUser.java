package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.User;

import java.util.List;

public interface IUser {
    public int persist(User user) throws Exception;
    public int remove(int id) throws Exception;
    public int update(User user) throws Exception;
    public List<User> getAll() throws Exception;
    public User find(int id) throws Exception;
    public User getLogin(String email, String password) throws Exception;
}
