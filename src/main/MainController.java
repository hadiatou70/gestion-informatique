package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.isi.appgestionsalleinformatique.dao.IUser;
import sn.isi.appgestionsalleinformatique.dao.UserImpl;
import sn.isi.appgestionsalleinformatique.entities.User;
import tools.Notification;
import tools.Outils;

public class MainController {
    @FXML
    private TextField emailtxt;
    @FXML
    private PasswordField passwordtxt;

    private IUser userdao = new UserImpl();
    public static User u = null;

    public void login(ActionEvent event){
        try {
            String email = emailtxt.getText();
            String pwd = passwordtxt.getText();
            User user = userdao.getLogin(email, pwd);
            if(user != null) {
                u = user;
                Outils.load(event, "Page d'accueil", "/sn/isi/appgestionsalleinformatique/ui/accueil.fxml");
            } else {
                Notification.NotifError("Message", "Email ou mot de passe incorrect !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
