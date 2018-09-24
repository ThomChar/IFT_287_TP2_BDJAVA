package modele;

import java.util.ArrayList;

public class Equipe {
	
	//Attributes
	
	private String nomEquipe;
	private ArrayList<Participant> listParticipants;
	
	//Builders
	
	public Equipe(String nomEquipe) {
		super();
		this.nomEquipe = nomEquipe;
		this.listParticipants = new ArrayList<Participant>();
	}
	
	public Equipe(String nomEquipe, ArrayList<Participant> listParticipants) {
		super();
		this.nomEquipe = nomEquipe;
		this.listParticipants = listParticipants;
	}

	//Getters & Setters
	
	public String getNomEquipe() {
		return nomEquipe;
	}

	public void setNomEquipe(String nomEquipe) {
		this.nomEquipe = nomEquipe;
	}

	public ArrayList<Participant> getListParticipants() {
		return listParticipants;
	}

	public void setListParticipants(ArrayList<Participant> listParticipants) {
		this.listParticipants = listParticipants;
	}

}
