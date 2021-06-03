package Serie9;

public class Etudiant {

    private String matricule, nom, prenom, ddnaissance, section;
    private Boolean boursier, etranger, inscription, reinscription;

    Etudiant(String matricule, String nom, String prenom, String ddnaissance, String section, Boolean boursier,
            Boolean etranger, Boolean inscription, Boolean reinscription) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.ddnaissance = ddnaissance;
        this.section = section;
        this.boursier = boursier;
        this.etranger = etranger;
        this.inscription = inscription;
        this.reinscription = reinscription;
    }

    public String toString() {
        return String.format(
                "Matricule: %s\nNom: %s\nPrenom: %s\nDate de Naissance %s\nSection: %s\nBoursier: %b\nEtranger: %b\nInscription: %b\nReinscription: %b",
                this.matricule, this.nom, this.prenom, this.ddnaissance, this.section, this.boursier, this.etranger,
                this.inscription, this.reinscription);
    }
}
