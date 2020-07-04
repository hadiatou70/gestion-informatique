package sn.isi.appgestionsalleinformatique.dao;

import sn.isi.appgestionsalleinformatique.entities.Machine;
import sn.isi.appgestionsalleinformatique.entities.Salle;
import sn.isi.appgestionsalleinformatique.entities.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MachineImpl implements IMachine {
    private DB db = new DB();
    @Override
    public int persist(Machine machine) throws Exception {
        String sql = "INSERT INTO machine VALUES(NULL,? ,?, ?, ?, ?)";
        db.init(sql);
        db.getPstm().setString(1, machine.getIpadresse());
        db.getPstm().setString(2, machine.getMacadresse());
        db.getPstm().setString(3, machine.getMarque());
        db.getPstm().setInt(4, machine.getSalle().getId());
        db.getPstm().setInt(5, machine.getUser().getId());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public int remove(int id) throws Exception {
        String sql = "DELETE FROM machine WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        int result = db.executeMaj();
        return result;
    }

    @Override
    public int update(Machine machine) throws Exception {
        String sql = "UPDATE machine SET ipadresse=?, macadresse=?, marque=?, salle=? user=? WHERE id=?";
        db.init(sql);
        db.getPstm().setString(1, machine.getIpadresse());
        db.getPstm().setString(2, machine.getMacadresse());
        db.getPstm().setString(3, machine.getMarque());
        db.getPstm().setInt(4, machine.getSalle().getId());
        db.getPstm().setInt(5, machine.getUser().getId());
        db.getPstm().setInt(6, machine.getId());

        int result = db.executeMaj();
        return result;
    }

    @Override
    public List<Machine> getAll() throws Exception {
        List<Machine> machineList = new ArrayList<>();
        String sql = "SELECT * FROM machine";
        db.init(sql);
        ResultSet rs = db.executeSelect();
        while (rs.next()) {
            Machine machine = new Machine();
            machine.setId(rs.getInt(1));
            machine.setIpadresse(rs.getString(2));
            machine.setMacadresse(rs.getString(3));
            machine.setMarque(rs.getString(4));

            ISalle salledao = new SalleImpl();
            int idSalle = rs.getInt(5);
            Salle salle = salledao.find(idSalle);
            machine.setSalle(salle);

            IUser userdao = new UserImpl();
            int idUser = rs.getInt(6);
            User user = userdao.find(idUser);
            machine.setUser(user);

            machineList.add(machine);
        }
        rs.close();
        return machineList;
    }

    @Override
    public Machine find(int id) throws Exception {
        Machine machine = null;
        String sql = "SELECT * FROM machine WHERE id = ?";
        db.init(sql);
        db.getPstm().setInt(1, id);
        ResultSet rs = db.executeSelect();
        if (rs.next()) {
            machine = new Machine();
            machine.setId(rs.getInt(1));
            machine.setIpadresse(rs.getString(2));
            machine.setMacadresse(rs.getString(3));
            machine.setMarque(rs.getString(4));

            ISalle salledao = new SalleImpl();
            int idSalle = rs.getInt(5);
            Salle salle = salledao.find(idSalle);
            machine.setSalle(salle);

            IUser userdao = new UserImpl();
            int idUser = rs.getInt(6);
            User user = userdao.find(idUser);
            machine.setUser(user);
        }
        rs.close();
        return machine;
    }
}
