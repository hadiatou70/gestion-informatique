package sn.isi.appgestionsalleinformatique.adminui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.MainController;
import sn.isi.appgestionsalleinformatique.dao.IUser;
import sn.isi.appgestionsalleinformatique.dao.UserImpl;
import sn.isi.appgestionsalleinformatique.entities.User;
import tools.Notification;
import tools.Outils;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField nomtxt;
    @FXML
    private TextField prenomtxt;
    @FXML
    private TextField emailtxt;
    @FXML
    private PasswordField passwordtxt;
    /*@FXML
    private TextField etattxt;*/
    @FXML
    private ComboBox<String> profilecbx;
    @FXML
    private TableView<User> admintable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> prenomColumn;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> profileColumn;
    @FXML
    private Label messagelb;
    @FXML
    private Button annulerbt;
    @FXML
    private Button validerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button modifierbt;

    IUser userdao = new UserImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        User user = MainController.u;
        /*String userparam = "Bienvenue " + user.getPrenom()+" "+
                user.getNom();
        messagelb.setText(userparam);*/
        setOn();

        ObservableList<String> profiles = FXCollections.observableArrayList();
        profiles.add("Admin");
        profiles.add("Maintenancier");
        profiles.add("Secretaire");
        profiles.add("Etudiant");
        profiles.add("Développeur");

        profilecbx.setItems(profiles);
        loadTable();
    }

    public void setOn(){
        validerbt.setDisable(false);
        annulerbt.setDisable(false);
        modifierbt.setDisable(true);
        supprimerbt.setDisable(true);
    }

    @FXML
    public void ajout(ActionEvent event) throws Exception {
        String nom = nomtxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        //String etat = etattxt.getText();
        String profile = profilecbx.getSelectionModel().getSelectedItem();

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        //user.setEtat(etat);
        user.setProfile(profile);

        int result = userdao.persist(user);
        if(result != 0){
            loadTable();
            Notification.NotifSucces("Message", "Données ajoutées");
        }else{
            Notification.NotifError("Message", "Erreur de code !!!");
        }

        actualiser();

    }

    private int IdU;
    @FXML
    public void supprimer(ActionEvent event) throws Exception {
        int result = userdao.remove(IdU);
        if (result != 0) {
            loadTable();
            Notification.NotifSucces("Message ", "Données supprimées");
        } else {
            Notification.NotifError("Message", "Erreur de code !");
        }

        actualiser();
    }

    @FXML
    public void modifier(ActionEvent event) throws Exception {
        User user = new User();
        user.setId(IdU);
        user.setNom(nomtxt.getText());
        user.setPrenom(prenomtxt.getText());
        user.setEmail(emailtxt.getText());
        user.setPassword(passwordtxt.getText());
        //user.setEtat(etattxt.getText());
        user.setProfile(profilecbx.getSelectionModel().getSelectedItem());

        int result = userdao.update(user);
        if (result != 0) {
            loadTable();
            Notification.NotifSucces("Message ", "Données modifiées");
        } else {
            Notification.NotifError("Message", "Erreur de code !");
        }

        actualiser();
    }


    @FXML
    public void annuler(ActionEvent event) {
        nomtxt.setText("");
        prenomtxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");
        profilecbx.getSelectionModel().clearAndSelect(0);
    }

    @FXML
    public void actualiser() {
        prenomtxt.setText("");
        nomtxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");
        profilecbx.getSelectionModel().selectFirst();
        annulerbt.setText("Annuler");
        setOn();
    }

    @FXML
    public void deconnexion(ActionEvent event) {
        try {
            Outils.load(event, "Login", "/main/main.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        //etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        profileColumn.setCellValueFactory(new PropertyValueFactory<>("profile"));

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            userdao.getAll().forEach(u->users.add(u));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        admintable.setItems(users);
    }

    private void setOff() {
        validerbt.setDisable(true);
        modifierbt.setDisable(false);
        supprimerbt.setDisable(false);
    }
    @FXML
    public void tableClicked(MouseEvent event) {

        User user = admintable.getSelectionModel().getSelectedItem();
        setOff();
        IdU = user.getId();
        prenomtxt.setText(user.getPrenom());
        nomtxt.setText(user.getNom());
        emailtxt.setText(user.getEmail());
        passwordtxt.setText(user.getPassword());
        profilecbx.getSelectionModel().select(user.getProfile());
        annulerbt.setText("Actualiser");
    }

}
