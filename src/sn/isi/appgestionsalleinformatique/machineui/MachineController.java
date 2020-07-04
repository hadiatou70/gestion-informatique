package sn.isi.appgestionsalleinformatique.machineui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.MainController;
import sn.isi.appgestionsalleinformatique.dao.IMachine;
import sn.isi.appgestionsalleinformatique.dao.ISalle;
import sn.isi.appgestionsalleinformatique.dao.MachineImpl;
import sn.isi.appgestionsalleinformatique.dao.SalleImpl;
import sn.isi.appgestionsalleinformatique.entities.Machine;
import sn.isi.appgestionsalleinformatique.entities.Salle;
import sn.isi.appgestionsalleinformatique.entities.User;
import tools.Notification;
import tools.Outils;

import java.net.URL;
import java.util.ResourceBundle;

public class MachineController implements Initializable {
    @FXML
    private Label messagelb;
    @FXML
    private TextField iptxt;
    @FXML
    private TextField mactxt;
    @FXML
    private TextField marquetxt;
    @FXML
    private ComboBox<Salle> sallecbx;
    @FXML
    private ComboBox<User> usercbx;
    @FXML
    private TableView<Machine> machinetable;
    @FXML
    private TableColumn<Machine, Integer> idColumn;
    @FXML
    private TableColumn<Machine, String> ipColumn;
    @FXML
    private TableColumn<Machine, String> macColumn;
    @FXML
    private TableColumn<Machine, String> marColumn;
    @FXML
    private TableColumn<Machine, Integer> userColumn;
    @FXML
    private TableColumn<Machine, Integer> salColumn;
    @FXML
    private Button annulerbt;
    @FXML
    private Button validerbt;
    @FXML
    private Button modifierbt;
    @FXML
    private Button supprimerbt;

    IMachine machinedao = new MachineImpl();
    ISalle salledao = new SalleImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = MainController.u;
        /*String userparam = "Bienvenue " + user.getPrenom()+" "+user.getNom();
        user.getNom();
        messagelb.setText(userparam);*/

        setOn();

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(user);
        usercbx.setItems(users);

        ObservableList<Salle> salles = FXCollections.observableArrayList();
        try {
            salledao.getAll().forEach(s->salles.add(s));
            sallecbx.setItems(salles);
        } catch (Exception ex) {

        }

        loadTable();

    }

    @FXML
    public void deconnexion(ActionEvent event) {
        try {
            Outils.load(event, "Login", "/main/main.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void Accueil(ActionEvent event) {
        try {
            Outils.load(event, "Accueil", "/sn/isi/appgestionsalleinformatique/ui/Accueil.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void ajout(ActionEvent event) throws Exception {
        String ipadresse = iptxt.getText();
        String macadresse = mactxt.getText();
        String marque = marquetxt.getText();
        User user = usercbx.getSelectionModel().getSelectedItem();
        Salle salle = sallecbx.getSelectionModel().getSelectedItem();

        Machine machine = new Machine();
        machine.setIpadresse(ipadresse);
        machine.setMacadresse(macadresse);
        machine.setMarque(marque);
        machine.setUser(user);
        machine.setSalle(salle);

        int result = machinedao.persist(machine);
        if (result != 0)
        {
            loadTable();
            Notification.NotifSucces("Message","Donnees Ajoutees");
        } else {
            Notification.NotifError("Erreur","Donnees Non Ajoutees");;
        }
        actualiser();
    }

    @FXML
    public void modifier(ActionEvent event) throws Exception {
        Machine machine = new Machine();
        machine.setId(idU);
        machine.setIpadresse(iptxt.getText());
        machine.setMacadresse(mactxt.getText());
        machine.setMarque(marquetxt.getText());
        machine.setUser(usercbx.getSelectionModel().getSelectedItem());
        machine.setSalle(sallecbx.getSelectionModel().getSelectedItem());

        int result = machinedao.update(machine);
        if (result != 0) {
            loadTable();
            Notification.NotifSucces("Message","Donnees Modifiées");
        } else {
            Notification.NotifError("Erreur","Donnees non modifiées");
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
    public void actualiser() {
        iptxt.setText("");
        mactxt.setText("");
        marquetxt.setText("");
        usercbx.getSelectionModel().selectFirst();
        sallecbx.getSelectionModel().selectFirst();
        annulerbt.setText("Annuler");
        setOn();
    }

    private void loadTable()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ipadresse"));
        macColumn.setCellValueFactory(new PropertyValueFactory<>("macadresse"));
        marColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
        userColumn.setCellValueFactory(new  PropertyValueFactory<>("user"));
        salColumn.setCellValueFactory(new PropertyValueFactory<>("salle"));

        ObservableList<Machine> machines = FXCollections.observableArrayList();
        try
        {
            machinedao.getAll().forEach(m->machines.add(m) );
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        machinetable.setItems(machines);
    }

    @FXML
    public void annuler(ActionEvent event) {
        iptxt.setText("");
        mactxt.setText("");
        marquetxt.setText("");
        usercbx.getSelectionModel().clearAndSelect(0);
        sallecbx.getSelectionModel().clearAndSelect(0);
    }
    private int idU;

    @FXML
    public void tableClicked(MouseEvent event) {

        Machine machine = machinetable.getSelectionModel().getSelectedItem();
        setOff();
        idU = machine.getId();
        iptxt.setText(machine.getIpadresse());
        mactxt.setText(machine.getMacadresse());
        marquetxt.setText(machine.getMarque());
        usercbx.getSelectionModel().select(machine.getUser());
        sallecbx.getSelectionModel().select(machine.getSalle());
        annulerbt.setText("Actualiser");
    }

    @FXML
    public void supprimer(ActionEvent event) throws Exception {
        int result = machinedao.remove(idU);
        if (result != 0) {
            loadTable();
            Notification.NotifSucces("Message Success","Donnees Supprimees");
        } else {
            //Notification.NotifError("Message", "Erreur de code !");
        }

        actualiser();
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


