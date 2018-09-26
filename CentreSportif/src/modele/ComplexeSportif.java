package modele;

import java.util.ArrayList;

public class ComplexeSportif {
	
	//Attributes
	
	private ArrayList<Ligue> listLigues;
	private ArrayList<Participant> listParticipants;
	private ArrayList<Resultat>listResultats;
	
	//Builders
	
	public ComplexeSportif() {
		super();
		this.listLigues = new ArrayList<Ligue>();
		this.listParticipants = new ArrayList<Participant>();
		this.listResultats = new ArrayList<Resultat>();
	}
	
	public ComplexeSportif(ArrayList<Ligue> listLigues, ArrayList<Participant> listParticipants) {
		super();
		this.listLigues = listLigues;
		this.listParticipants = listParticipants;
		this.listResultats = new ArrayList<Resultat>();
	}
	
	public ComplexeSportif(ArrayList<Ligue> listLigues, ArrayList<Participant> listParticipants,
			ArrayList<Resultat> listResultats) {
		super();
		this.listLigues = listLigues;
		this.listParticipants = listParticipants;
		this.listResultats = listResultats;
	}
	
	// Getters & Setters

	public ArrayList<Ligue> getListLigues() {
		return listLigues;
	}
	public void setListLigues(ArrayList<Ligue> listLigues) {
		this.listLigues = listLigues;
	}
	public ArrayList<Participant> getListParticipants() {
		return listParticipants;
	}
	public void setListParticipants(ArrayList<Participant> listParticipants) {
		this.listParticipants = listParticipants;
	}
	public ArrayList<Resultat> getListResultats() {
		return listResultats;
	}
	public void setListResultats(ArrayList<Resultat> listResultats) {
		this.listResultats = listResultats;
	}
}
