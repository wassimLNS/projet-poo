package model;

public class LigneCommande {
    private Medicament medicament;
    private int quantite;

    public LigneCommande(Medicament medicament, int quantite) {
        this.medicament = medicament;
        this.quantite = quantite;
    }

    // Getters
    public Medicament getMedicament() { return medicament; }
    public int getQuantite() { return quantite; }

    // Setters
    public void setMedicament(Medicament medicament) { this.medicament = medicament; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getMontantTotal() {
        return medicament.getPrix() * quantite;
    }
} 