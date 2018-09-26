package modele;

public class Resultat {
 
	//Attributes
	
	private Equipe EquipeA;
	private Equipe EquipeB;
	private int scoreEquipeA;
	private int scoreEquipeB;
		
	//Builders
	
	public Resultat(Equipe equipeA, Equipe equipeB, int scoreEquipeA, int scoreEquipeB) {
		super();
		EquipeA = equipeA;
		EquipeB = equipeB;
		this.scoreEquipeA = scoreEquipeA;
		this.scoreEquipeB = scoreEquipeB;
	}
	
	//Getters & Setters
	
	public Equipe getEquipeA() {
		return EquipeA;
	}
	
	public void setEquipeA(Equipe equipeA) {
		EquipeA = equipeA;
	}
	public Equipe getEquipeB() {
		return EquipeB;
	}
	public void setEquipeB(Equipe equipeB) {
		EquipeB = equipeB;
	}
	public int getScoreEquipeA() {
		return scoreEquipeA;
	}
	public void setScoreEquipeA(int scoreEquipeA) {
		this.scoreEquipeA = scoreEquipeA;
	}
	public int getScoreEquipeB() {
		return scoreEquipeB;
	}
	public void setScoreEquipeB(int scoreEquipeB) {
		this.scoreEquipeB = scoreEquipeB;
	}
	
}
