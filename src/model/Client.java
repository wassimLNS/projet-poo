package model;

public class Client {
    private String nom;
    private String prenom;
    private String telephone;
    private String numSecuriteSociale;
    private boolean carteAssuranceActive;
    private double tauxReduction;

    public Client(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.carteAssuranceActive = false;
        this.tauxReduction = 0.0;
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public String getNumSecuriteSociale() { return numSecuriteSociale; }
    public boolean isCarteAssuranceActive() { return carteAssuranceActive; }
    public double getTauxReduction() { return tauxReduction; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setNumSecuriteSociale(String numSecuriteSociale) { this.numSecuriteSociale = numSecuriteSociale; }
    public void setCarteAssuranceActive(boolean carteAssuranceActive) { this.carteAssuranceActive = carteAssuranceActive; }
    public void setTauxReduction(double tauxReduction) { this.tauxReduction = tauxReduction; }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", numSecuriteSociale='" + numSecuriteSociale + '\'' +
                ", carteAssuranceActive=" + carteAssuranceActive +
                ", tauxReduction=" + tauxReduction +
                '}';
    }
} 