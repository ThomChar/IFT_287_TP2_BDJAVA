package modele;

import java.util.ArrayList;

public class Equipe {
	
	//Attributes
	
	private String nomEquipe;
	private ArrayList<Participant> listParticipants;
	private Participant capitaine;
	private Ligue ligue;
	private ArrayList<Resultat> listResultats;
	
	//Builders
	
	public Equipe(Ligue ligue, String nomEquipe, Participant capitaine) {
		super();
		this.setLigue(ligue);
		this.nomEquipe = nomEquipe;
		this.listParticipants = new ArrayList<Participant>();
		this.listParticipants.add(capitaine);
		this.capitaine = capitaine;
		this.listResultats = new ArrayList<Resultat>();
	}
	
	
	public Equipe(Ligue ligue, String nomEquipe,  Participant capitaine, ArrayList<Participant> listParticipants) {
		super();
		this.setLigue(ligue);
		this.nomEquipe = nomEquipe;
		this.listParticipants = listParticipants;
		this.capitaine = capitaine;
		this.listResultats = new ArrayList<Resultat>();
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

	public Participant getCapitaine() {
		return capitaine;
	}

	public void setCapitaine(Participant capitaine) {
		this.capitaine = capitaine;
	}

	public Ligue getLigue() {
		return ligue;
	}

	public void setLigue(Ligue ligue) {
		this.ligue = ligue;
	}

	public ArrayList<Resultat> getListResultat() {
		return listResultats;
	}

	public void setListResultat(ArrayList<Resultat> listResultat) {
		this.listResultats = listResultat;
	}

}
