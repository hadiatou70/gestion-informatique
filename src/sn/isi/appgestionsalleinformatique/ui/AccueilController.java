package sn.isi.appgestionsalleinformatique.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.MainController;
import sn.isi.appgestionsalleinformatique.entities.User;
import tools.Outils;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    @FXML
    private Label messageLb;
    @FXML
    private Button adminbt;
    @FXML
    private Button sallebt;
    @FXML
    private Button machinebt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = MainController.u;
        /*String userparams = "Bienvenue " + user.getPrenom() + " " + user.getNom();
        messageLb.setText(userparams);*/

        if(user.getProfile().equals("Développeur")){
            adminbt.setVisible(false);
        }

        if(user.getProfile().equals("Etudiant") || user.getProfile().equals("Secretaire")){
            adminbt.setVisible(false);
            machinebt.setVisible(false);
        }
    }


    public void deconnexion(ActionEvent event){
        try {
            Outils.load(event, "Page d'accueil", "/main/main.fxml");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void admin(ActionEvent event) {
        try {
            Outils.load(event, "Admin", "/sn/isi/appgestionsalleinformatique/adminui/admin.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void salle(ActionEvent event) {
        try {
            Outils.load(event, "Accueil", "/sn/isi/appgestionsalleinformatique/salleui/salle.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void machine(ActionEvent event) {
        try {
            Outils.load(event, "Accueil", "/sn/isi/appgestionsalleinformatique/machineui/machine.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
