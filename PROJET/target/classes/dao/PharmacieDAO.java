package dao;

import model.*;
import java.util.*;

public class PharmacieDAO {
    private List<Client> clients;
    private List<Fournisseur> fournisseurs;
    private List<Medicament> medicaments;
    private List<Commande> commandes;
    private List<Vente> ventes;
    private int nextCommandeId;
    private int nextVenteId;

    public PharmacieDAO() {
        this.clients = new ArrayList<>();
        this.fournisseurs = new ArrayList<>();
        this.medicaments = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.ventes = new ArrayList<>();
        this.nextCommandeId = 1;
        this.nextVenteId = 1;
    }

    // Gestion des clients
    public void ajouterClient(Client client) {
        clients.add(client);
    }

    public void supprimerClient(Client client) {
        clients.remove(client);
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public Client rechercherClient(String nom, String prenom) {
        return clients.stream()
                .filter(c -> c.getNom().equals(nom) && c.getPrenom().equals(prenom))
                .findFirst()
                .orElse(null);
    }

    public void modifierClient(Client client) {
        // Trouver et remplacer le client existant
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getNom().equals(client.getNom()) && 
                clients.get(i).getPrenom().equals(client.getPrenom())) {
                clients.set(i, client);
                break;
            }
        }
    }

    // Gestion des fournisseurs
    public void ajouterFournisseur(Fournisseur fournisseur) {
        fournisseurs.add(fournisseur);
    }

    public void supprimerFournisseur(Fournisseur fournisseur) {
        fournisseurs.remove(fournisseur);
    }

    public List<Fournisseur> getFournisseurs() {
        return new ArrayList<>(fournisseurs);
    }

    public void modifierFournisseur(Fournisseur fournisseur) {
        // Trouver et remplacer le fournisseur existant
        for (int i = 0; i < fournisseurs.size(); i++) {
            if (fournisseurs.get(i).getNom().equals(fournisseur.getNom())) {
                fournisseurs.set(i, fournisseur);
                break;
            }
        }
    }

    // Gestion des médicaments
    public void ajouterMedicament(Medicament medicament) {
        medicaments.add(medicament);
    }

    public void supprimerMedicament(Medicament medicament) {
        medicaments.remove(medicament);
    }

    public List<Medicament> getMedicaments() {
        return new ArrayList<>(medicaments);
    }

    public List<Medicament> getMedicamentsProchesPeremption(int joursAvantPeremption) {
        Date dateLimit = new Date(System.currentTimeMillis() + (long)joursAvantPeremption * 24 * 60 * 60 * 1000);
        return medicaments.stream()
                .filter(m -> {
                    try {
                        Date datePeremption = new Date(m.getDatePeremption());
                        return datePeremption.before(dateLimit);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public void modifierMedicament(Medicament medicament) {
        // Trouver et remplacer le médicament existant
        for (int i = 0; i < medicaments.size(); i++) {
            if (medicaments.get(i).getReference().equals(medicament.getReference())) {
                medicaments.set(i, medicament);
                break;
            }
        }
    }

    // Gestion des commandes
    public Commande creerCommande(Fournisseur fournisseur) {
        Commande commande = new Commande(nextCommandeId++, fournisseur);
        commandes.add(commande);
        return commande;
    }

    public List<Commande> getCommandes() {
        return new ArrayList<>(commandes);
    }

    // Gestion des ventes
    public Vente creerVente(Client client) {
        Vente vente = new Vente(nextVenteId++, client);
        ventes.add(vente);
        return vente;
    }

    public List<Vente> getVentes() {
        return new ArrayList<>(ventes);
    }

    public List<Vente> getHistoriqueAchatsClient(Client client) {
        return ventes.stream()
                .filter(v -> v.getClient().equals(client))
                .collect(java.util.stream.Collectors.toList());
    }
} 