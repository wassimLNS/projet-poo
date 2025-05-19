package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Commande {
    private int id;
    private Date date;
    private Fournisseur fournisseur;
    private List<LigneCommande> lignesCommande;
    private double montantTotal;
    private String statut; // "En cours", "Livrée", "Annulée"

    public Commande(int id, Fournisseur fournisseur) {
        this.id = id;
        this.date = new Date();
        this.fournisseur = fournisseur;
        this.lignesCommande = new ArrayList<>();
        this.montantTotal = 0.0;
        this.statut = "En cours";
    }
    public void ajouterLigne(Medicament medicament, int quantite) {
        LigneCommande ligne = new LigneCommande(medicament, quantite);
        lignesCommande.add(ligne);
        calculerMontantTotal();
    }
    private void calculerMontantTotal() {
        montantTotal = 0.0;
        for (LigneCommande ligne : lignesCommande) {
            montantTotal += ligne.getMontantTotal();
        }
    }
    // Getters
    public int getId() { return id; }
    public Date getDate() { return date; }
    public Fournisseur getFournisseur() { return fournisseur; }
    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public double getMontantTotal() { return montantTotal; }
    public String getStatut() { return statut; }
    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }
    public void setStatut(String statut) { this.statut = statut; }
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date=" + date +
                ", fournisseur=" + fournisseur +
                ", montantTotal=" + montantTotal +
                ", statut='" + statut + '\'' +
                '}';
    }
} 