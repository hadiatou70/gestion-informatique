package sn.isi.appgestionsalleinformatique.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstm;

    private void ouvrirConnexion() {

     String mysql_user = "root";
     String mysql_password = "";
     String mysql_host = "localhost";
     String mysql_database = "gestioninformatique";
     String url = "jdbc:mysql://"+mysql_host+":3306/"+mysql_database;

     try {
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection(url, mysql_user, mysql_password);
         } catch (Exception e) {
             e.printStackTrace();
            }

        }

        public void init(String sql) {
            try {
                ouvrirConnexion();
                pstm = con.prepareStatement(sql);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public ResultSet executeSelect() {

            try {
                rs = pstm.executeQuery();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return rs;
        }
        public int executeMaj() {
            int ok = 0;
            try {
                ok = pstm.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return ok;
        }

        public PreparedStatement getPstm() {
            return pstm;
        }

        public void fermerConnection() {
            try {
                if(con != null) {
                    con.close();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}
