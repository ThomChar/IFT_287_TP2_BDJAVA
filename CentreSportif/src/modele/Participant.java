package modele;

public class Participant {
	
	//Attributes

	private String matricule;
	private String prenom;
	private String nom;
	private String motDePasse;
	
	//Builders
	
	public Participant(String matricule, String prenom, String nom, String motDePasse) {
		super();
		this.matricule = matricule;
		this.prenom = prenom;
		this.nom = nom;
		this.motDePasse = motDePasse;
	}
	
	// Getters & Setters

	public String getMatricule() {
		return matricule;
	}
	
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	@Override
	public String toString() {
		return "Participant [matricule=" + matricule + ", prenom=" + prenom + ", nom=" + nom + ", motDePasse="
				+ motDePasse + "]";
	}
}
