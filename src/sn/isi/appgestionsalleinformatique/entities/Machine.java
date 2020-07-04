package sn.isi.appgestionsalleinformatique.entities;

public class Machine {
    private int id;
    private String ipadresse;
    private String macadresse;
    private String marque;
    private Salle salle;
    private User user;

    public Machine() {
    }

    public Machine(int id, String ipadresse, String macadresse, String marque, Salle salle, User user) {
        this.id = id;
        this.ipadresse = ipadresse;
        this.macadresse = macadresse;
        this.marque = marque;
        this.salle = salle;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpadresse() {
        return ipadresse;
    }

    public void setIpadresse(String ipadresse) {
        this.ipadresse = ipadresse;
    }

    public String getMacadresse() {
        return macadresse;
    }

    public void setMacadresse(String macadresse) {
        this.macadresse = macadresse;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
