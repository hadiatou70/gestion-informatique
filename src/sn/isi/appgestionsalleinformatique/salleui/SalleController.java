package sn.isi.appgestionsalleinformatique.salleui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.MainController;
import sn.isi.appgestionsalleinformatique.dao.ISalle;
import sn.isi.appgestionsalleinformatique.dao.SalleImpl;
import sn.isi.appgestionsalleinformatique.entities.Salle;
import sn.isi.appgestionsalleinformatique.entities.User;
import tools.Notification;
import tools.Outils;

import java.net.URL;
import java.util.ResourceBundle;

public class SalleController implements Initializable {

    @FXML
    private TableView<Salle> salletable;
    @FXML
    private TableColumn<Salle, Integer> idColumn;
    @FXML
    private TableColumn<Salle, Integer> nomColumn;
    @FXML
    private TextField nomtxt;
    @FXML
    private Label messagelb;
    @FXML
    private Button annulerbt;
    @FXML
    private Button  validerbt;
    @FXML
    private Button modifierbt;
    @FXML
    private Button supprimerbt;
    private ISalle salledao = new SalleImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = MainController.u;
        /*String userparam = "Bienvenue " + user.getPrenom()+" "+
                user.getNom();
        messagelb.setText(userparam);*/

        loadTable();
        setOn();
    }

    @FXML
    public void ajoutSalle() throws Exception {

        String nom = nomtxt.getText();

        Salle salle = new Salle();
        salle.setNom(nom);

        int result = salledao.persist(salle);
        if (result != 0)
        {
            loadTable();
            Notification.NotifSucces("Message", "Donnees ajoutees");
        } else {
            Notification.NotifError("Message", "Error de code");
        }
        actualiser();
    }

    private int idS;
    @FXML
    public void modifier(ActionEvent event) throws Exception {
        Salle salle = new Salle();
        salle.setId(idS);
        salle.setNom(nomtxt.getText());

        int result = salledao.update(salle);
        if (result != 0) {
            loadTable();
            System.out.println("Donnees modifiees");
        } else {
            System.out.println("Erreur");
        }

        actualiser();
    }

    @FXML
    public void actualiser() {
        nomtxt.setText("");
        annulerbt.setText("Annuler");
        setOn();
    }


    private void loadTable()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        ObservableList<Salle> salles = FXCollections.observableArrayList();
        try
        {
            salledao.getAll().forEach(u ->salles.add(u) );

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        salletable.setItems(salles);
    }

    @FXML
    public void annuler(ActionEvent event) {
        nomtxt.setText("");
    }


    @FXML
    public void tableClicked(MouseEvent event) {

        Salle salle = salletable.getSelectionModel().getSelectedItem();
        setOff();
        idS= salle.getId();
        nomtxt.setText(salle.getNom());
        annulerbt.setText("Actualiser");
    }

    @FXML
    public void supprimer(ActionEvent event) throws Exception {
        int result = salledao.remove(idS);
        if (result != 0) {
            loadTable();
            System.out.println("Donnees supprimes");
        } else {
            //Notification.NotifError("Message", "Erreur de code !");
        }

        actualiser();
    }

    private void setOn() {
        validerbt.setDisable(false);
        annulerbt.setDisable(false);
        modifierbt.setDisable(true);
        supprimerbt.setDisable(true);
    }
    private void setOff() {
        validerbt.setDisable(true);
        modifierbt.setDisable(false);
        supprimerbt.setDisable(false);
    }


    @FXML
    public void deconnexion(ActionEvent event) {
        try {
            Outils.load(event, "Quitter", "/main/main.fxml");
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

}
