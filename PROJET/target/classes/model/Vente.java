package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Vente {
    private int id;
    private Date date;
    private Client client;
    private List<LigneVente> lignesVente;
    private double montantTotal;
    private double montantApresReduction;

    public Vente(int id, Client client) {
        this.id = id;
        this.date = new Date();
        this.client = client;
        this.lignesVente = new ArrayList<>();
        this.montantTotal = 0.0;
        this.montantApresReduction = 0.0;
    }

    public void ajouterLigne(Medicament medicament, int quantite) {
        if (medicament.getQuantiteStock() >= quantite) {
            LigneVente ligne = new LigneVente(medicament, quantite);
            lignesVente.add(ligne);
            medicament.setQuantiteStock(medicament.getQuantiteStock() - quantite);
            calculerMontantTotal();
        } else {
            throw new IllegalArgumentException("Stock insuffisant");
        }
    }

    private void calculerMontantTotal() {
        montantTotal = 0.0;
        for (LigneVente ligne : lignesVente) {
            montantTotal += ligne.getMedicament().getPrix() * ligne.getQuantite();
        }
        
        if (client.isCarteAssuranceActive()) {
            montantApresReduction = montantTotal * (1 - client.getTauxReduction());
        } else {
            montantApresReduction = montantTotal;
        }
    }
    // Getters
    public int getId() { return id; }
    public Date getDate() { return date; }
    public Client getClient() { return client; }
    public List<LigneVente> getLignesVente() { return lignesVente; }
    public double getMontantTotal() { return montantTotal; }
    public double getMontantApresReduction() { return montantApresReduction; }
    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setClient(Client client) { this.client = client; }

    @Override
    public String toString() {
        return "Vente{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", montantTotal=" + montantTotal +
                ", montantApresReduction=" + montantApresReduction +
                '}';
    }
}