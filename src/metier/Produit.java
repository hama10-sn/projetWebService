package metier;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Produit implements Serializable {

    private int    id;
    private String nom;
    private int    prixUnit;
    private int    quantite;

    public Produit() {

    }

    public Produit( int id, String nom, int prixUnit, int quantite ) {
        this.id = id;
        this.nom = nom;
        this.prixUnit = prixUnit;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public int getPrixUnit() {
        return prixUnit;
    }

    public void setPrixUnit( int prixUnit ) {
        this.prixUnit = prixUnit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite( int quantite ) {
        this.quantite = quantite;
    }

}
