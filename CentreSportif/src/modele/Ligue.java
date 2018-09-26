package modele;

import java.util.ArrayList;

public class Ligue {

	// Attributes

	private String NomLigue;
	private ArrayList<Equipe> listEquipes;

	// Builders

	public Ligue(String nomLigue, ArrayList<Equipe> listEquipes) {
		super();
		NomLigue = nomLigue;
		this.listEquipes = listEquipes;
	}

	public Ligue(String nomLigue) {
		super();
		NomLigue = nomLigue;
		this.listEquipes = new ArrayList<Equipe>();
	}

	// Getters & Setters

	public String getNomLigue() {
		return NomLigue;
	}

	public void setNomLigue(String nomLigue) {
		NomLigue = nomLigue;
	}

	public ArrayList<Equipe> getListEquipes() {
		return listEquipes;
	}

	public void setListEquipes(ArrayList<Equipe> listEquipes) {
		this.listEquipes = listEquipes;
	}

	// Methods

	/*public void inscrireParticipant(String matricule, String prenom, String nom, String motDePasse) {
		
		
		Participant participant = new Participant(matricule, prenom, nom, motDePasse);
		
	}
	
	 public void supprimerParticipant(String matricule){
		 
	 }*/

	@Override
	public String toString() {
		return "Ligue [NomLigue=" + NomLigue + ", listEquipes=" + listEquipes + "]";
	}

}
