package model;

public class Medicament {
    public enum TypeMedicament {
        COMPRIME,
        SIROP,
        INJECTABLE
    }

    private String nom;
    private String reference;
    private int quantiteStock;
    private String datePeremption;
    private String type; // générique ou spécifique
    private double prix;
    private TypeMedicament categorie;
    
    // Attributs spécifiques selon le type
    private int uniteBoite;      // pour les comprimés (nombre d'unités dans la boîte)
    private int contenanceMl;    // pour les sirops (en ml)
    private double dosageInjectable; // pour les injectables (en ml)

    public Medicament(String nom, String reference, int quantiteStock, String datePeremption, 
                     String type, double prix, TypeMedicament categorie) {
        this.nom = nom;
        this.reference = reference;
        this.quantiteStock = quantiteStock;
        this.datePeremption = datePeremption;
        this.type = type;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Méthodes spécifiques selon le type
    public void setDetailsComprime(int uniteBoite) {
        if (this.categorie != TypeMedicament.COMPRIME) {
            throw new IllegalStateException("Ce médicament n'est pas un comprimé");
        }
        this.uniteBoite = uniteBoite;
    }

    public void setDetailsSirop(int contenanceMl) {
        if (this.categorie != TypeMedicament.SIROP) {
            throw new IllegalStateException("Ce médicament n'est pas un sirop");
        }
        this.contenanceMl = contenanceMl;
    }

    public void setDetailsInjectable(double dosageInjectable) {
        if (this.categorie != TypeMedicament.INJECTABLE) {
            throw new IllegalStateException("Ce médicament n'est pas injectable");
        }
        this.dosageInjectable = dosageInjectable;
    }

    // Getters
    public String getNom() { return nom; }
    public String getReference() { return reference; }
    public int getQuantiteStock() { return quantiteStock; }
    public String getDatePeremption() { return datePeremption; }
    public String getType() { return type; }
    public double getPrix() { return prix; }
    public TypeMedicament getCategorie() { return categorie; }
    public int getUniteBoite() { return uniteBoite; }
    public int getContenanceMl() { return contenanceMl; }
    public double getDosageInjectable() { return dosageInjectable; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setReference(String reference) { this.reference = reference; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
    public void setDatePeremption(String datePeremption) { this.datePeremption = datePeremption; }
    public void setType(String type) { this.type = type; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setCategorie(TypeMedicament categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medicament{")
          .append("nom='").append(nom).append('\'')
          .append(", reference='").append(reference).append('\'')
          .append(", quantite=").append(quantiteStock)
          .append(", datePeremption='").append(datePeremption).append('\'')
          .append(", type='").append(type).append('\'')
          .append(", prix=").append(prix)
          .append(", categorie=").append(categorie);

        switch (categorie) {
            case COMPRIME:
                sb.append(", unités par boîte=").append(uniteBoite);
                break;
            case SIROP:
                sb.append(", contenance=").append(contenanceMl).append("ml");
                break;
            case INJECTABLE:
                sb.append(", dosage=").append(dosageInjectable).append("ml");
                break;
        }
        
        sb.append('}');
        return sb.toString();
    }
} 