package modele;

import java.util.ArrayList;

public class Ligue {

	// Attributes

	private String nomLigue;
	private ArrayList<Equipe> listEquipes;
	private int nbJoueurMaxParEquipe;

	// Builders

	public Ligue(String nomLigue, ArrayList<Equipe> listEquipes, int nbJoueurMaxParEquipe) {
		super();
		this.nomLigue = nomLigue;
		this.listEquipes = listEquipes;
		this.setNbJoueurMaxParEquipe(nbJoueurMaxParEquipe);
	}

	public Ligue(String nomLigue, int nbJoueurMaxParEquipe) {
		super();
		this.nomLigue = nomLigue;
		this.listEquipes = new ArrayList<Equipe>();
		this.setNbJoueurMaxParEquipe(nbJoueurMaxParEquipe);
	}

	// Getters & Setters

	public String getNomLigue() {
		return nomLigue;
	}

	public void setNomLigue(String nomLigue) {
		this.nomLigue = nomLigue;
	}

	public ArrayList<Equipe> getListEquipes() {
		return listEquipes;
	}

	public void setListEquipes(ArrayList<Equipe> listEquipes) {
		this.listEquipes = listEquipes;
	}

	public int getNbJoueurMaxParEquipe() {
		return nbJoueurMaxParEquipe;
	}

	public void setNbJoueurMaxParEquipe(int nbJoueurMaxParEquipe) {
		this.nbJoueurMaxParEquipe = nbJoueurMaxParEquipe;
	}

	@Override
	public String toString() {
		return "Ligue [nomLigue=" + nomLigue + ", listEquipes=" + listEquipes + ", nbJoueurMaxParEquipe="
				+ nbJoueurMaxParEquipe + "]";
	}

	// Methods
	
	

}
