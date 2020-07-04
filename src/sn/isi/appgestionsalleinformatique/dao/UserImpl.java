package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements IUser {
    private DB db = new DB();
    @Override
    public int persist(User user) throws Exception {
        String sql = "INSERT INTO user VALUES(NULL,? ,? ,? , ?, ?, ?)";
        db.init(sql);
        db.getPstm().setString(1, user.getNom());
        db.getPstm().setString(2, user.getPrenom());
        db.getPstm().setString(3, user.getEmail());
        db.getPstm().setString(4, user.getPassword());
        db.getPstm().setInt(5, user.getEtat());
        db.getPstm().setString(6, user.getProfile());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public int remove(int id) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        int result = db.executeMaj();
        return result;
    }

    @Override
    public int update(User user) throws Exception {
        String sql = "UPDATE user SET nom=?, prenom=?, email=?, password=?, etat=?, profile=? WHERE id=?";
        db.init(sql);
        db.getPstm().setString(1, user.getNom());
        db.getPstm().setString(2, user.getPrenom());
        db.getPstm().setString(3, user.getEmail());
        db.getPstm().setString(4, user.getPassword());
        db.getPstm().setInt(5, user.getEtat());
        db.getPstm().setString(6, user.getProfile());
        db.getPstm().setInt(7, user.getId());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        db.init(sql);
        ResultSet rs = db.executeSelect();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setNom(rs.getString(2));
            user.setPrenom(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setEtat(rs.getInt(6));
            user.setProfile(rs.getString(7));

            users.add(user);
        }
        rs.close();
        return users;
    }

    @Override
    public User find(int id) throws Exception {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        ResultSet rs = db.executeSelect();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt(1));
            user.setNom(rs.getString(2));
            user.setPrenom(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setEtat(rs.getInt(6));
            user.setProfile(rs.getString(7));
        }
        rs.close();
        return user;
    }

    @Override
    public User getLogin(String email, String password) throws Exception {
        User user = null;
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        db.init(sql);
        db.getPstm().setString(1, email);
        db.getPstm().setString(2, password);
        ResultSet rs = db.executeSelect();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt(1));
            user.setNom(rs.getString(2));
            user.setPrenom(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setEtat(rs.getInt(6));
            user.setProfile(rs.getString(7));
        }
        rs.close();
        return user;
    }
}
