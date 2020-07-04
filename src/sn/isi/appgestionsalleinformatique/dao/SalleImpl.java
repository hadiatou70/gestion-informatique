package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.Salle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalleImpl implements ISalle{
    private DB db = new DB();
    @Override
    public int persist(Salle salle) throws Exception {
        String sql = "INSERT INTO salle VALUES(NULL,?)";
        db.init(sql);
        db.getPstm().setString(1, salle.getNom());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public int remove(int id) throws Exception {
        String sql = "DELETE FROM salle WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        int result = db.executeMaj();
        return result;
    }

    @Override
    public int update(Salle salle) throws Exception {
        String sql = "UPDATE salle SET nom=? WHERE id=?";
        db.init(sql);
        db.getPstm().setString(1, salle.getNom());
        db.getPstm().setInt(2, salle.getId());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public List<Salle> getAll() throws Exception {
        List<Salle> salleList = new ArrayList<>();
        String sql = "SELECT * FROM salle";
        db.init(sql);
        ResultSet rs = db.executeSelect();
        while (rs.next()) {
            Salle salle = new Salle();
            salle.setId(rs.getInt(1));
            salle.setNom(rs.getString(2));


            salleList.add(salle);
        }
        rs.close();
        return salleList;
    }

    @Override
    public Salle find(int id) throws Exception {
        Salle salle = null;
        String sql = "SELECT * FROM salle WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        ResultSet rs = db.executeSelect();
        if (rs.next()) {
            salle = new Salle();
            salle.setId(rs.getInt(1));
            salle.setNom(rs.getString(2));
        }
        rs.close();
        return salle;
    }
}
