package model;

public class Fournisseur {
    private String nom;
    private String telephone;
    private String adresse;
    private String email;

    public Fournisseur(String nom, String telephone, String adresse, String email) {
        this.nom = nom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
    }

    // Getters
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
} 