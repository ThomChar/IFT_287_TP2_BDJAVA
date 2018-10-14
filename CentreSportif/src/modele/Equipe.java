package modele;

import java.util.ArrayList;

public class Equipe {
	
	//Attributes
	
	private String nomEquipe;
	private ArrayList<Participant> listParticipants;
	private String matriculeCap;
	private Participant capitaine;
	private String nomLigue;
	private Ligue ligue;
	private ArrayList<Resultat> listResultats;
	private int nbMGagne;
	private int nbMPerdu;
	private int nbMNul;
	
	//Builders
	
	public Equipe() {
	}
	
	public Equipe(String nomLigue, String nomEquipe, String matriculeCap) {
		super();
		this.setLigue(ligue);
		this.nomEquipe = nomEquipe;
		this.listParticipants = new ArrayList<Participant>();
		//this.listParticipants.add(capitaine);
		this.matriculeCap = matriculeCap;
		this.capitaine = null;
		this.nomLigue = nomLigue;
		this.ligue = null;
		this.listResultats = new ArrayList<Resultat>();
		this.nbMGagne = 0;
		this.nbMPerdu = 0;
		this.nbMNul = 0;
	}
	
	
	public Equipe(String nomLigue, String nomEquipe,  String matriculeCap, ArrayList<Participant> listParticipants) {
		super();
		this.setLigue(ligue);
		this.nomEquipe = nomEquipe;
		this.listParticipants = listParticipants;
		this.matriculeCap = matriculeCap;
		this.capitaine = null;
		this.nomLigue = nomLigue;
		this.ligue = null;
		this.listResultats = new ArrayList<Resultat>();
		this.nbMGagne = 0;
		this.nbMPerdu = 0;
		this.nbMNul = 0;
	}

	//Getters & Setters
	


	public int getNbMGagne() {
		return nbMGagne;
	}

	public void setNbMGagne(int nbMGagne) {
		this.nbMGagne = nbMGagne;
	}

	public int getNbMPerdu() {
		return nbMPerdu;
	}

	public void setNbMPerdu(int nbMPerdu) {
		this.nbMPerdu = nbMPerdu;
	}

	public int getNbMNul() {
		return nbMNul;
	}

	public void setNbMNul(int nbMNul) {
		this.nbMNul = nbMNul;
	}

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

	public ArrayList<Resultat> getListResultats() {
		return listResultats;
	}

	public void setListResultats(ArrayList<Resultat> listResultat) {
		this.listResultats = listResultat;
	}


	public String getMatriculeCap() {
		return matriculeCap;
	}


	public void setMatriculeCap(String matriculeCap) {
		this.matriculeCap = matriculeCap;
	}


	public String getNomLigue() {
		return nomLigue;
	}


	public void setNomLigue(String nomLigue) {
		this.nomLigue = nomLigue;
	}

	public boolean isActive() {
		boolean testIsActive = true;
		if(this.getListParticipants().size() == 0) {
			testIsActive = false;
		}
		return testIsActive;
	}

}
