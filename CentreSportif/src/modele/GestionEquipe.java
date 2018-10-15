package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionEquipe {

	private TableEquipes equipe;
	private TableLigues ligue;
	private TableParticipants participant;
<<<<<<< HEAD
	private TableResultats resultat;
=======
>>>>>>> remi
	private Connexion cx;

	/**
	 * Creation d'une instance
	 */
<<<<<<< HEAD
	public GestionEquipe(TableEquipes equipe, TableParticipants participant, TableLigues ligue, TableResultats resultat)
			throws IFT287Exception {
		this.cx = equipe.getConnexion();
		if (equipe.getConnexion() == ligue.getConnexion() && participant.getConnexion() == equipe.getConnexion() && equipe.getConnexion() == resultat.getConnexion() && ligue.getConnexion() == resultat.getConnexion()) {
			this.equipe = equipe;
			this.participant = participant;
			this.ligue = ligue;
			this.resultat = resultat;
		}else{
			throw new IFT287Exception("Les instances de participant et de resultat n'utilisent pas la même connexion au serveur");
		}
=======
	public GestionEquipe(TableEquipes equipe, TableLigues ligue, TableParticipants particpant) throws IFT287Exception {
		this.cx = equipe.getConnexion();
		this.ligue = ligue;
		this.equipe = equipe;
		this.participant = participant;
>>>>>>> remi
	}

	/**
	 * Ajout d'une nouvelle equipe dans la base de données. S'il existe déjà , une
	 * exception est levée.
	 */
	public void ajouter(String nomEquipe, String matriculeCap, String nomLigue)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si l equipe existe déjà
			if (equipe.existe(nomEquipe))
				throw new IFT287Exception("Equipe " + nomEquipe + " existe déjà : ");
			if (ligue.existe(nomLigue))
				throw new IFT287Exception("Ligue " + nomLigue + " n'existe pas : ");

			// Ajout du participant dans la table des participant
			equipe.creer(nomEquipe, matriculeCap, nomLigue);
			// Equipe newEquipe = equipe.getEquipe(nomEquipe);
			// ligue.ajouter(newEquipe);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Supprime Equipe de la base de données.
	 */
	public void supprime(String nomEquipe) throws SQLException, IFT287Exception, Exception {
		try {
			// Validation
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if (tupleEquipe == null)
				throw new IFT287Exception("Equipe inexistant: " + nomEquipe);
			if (!tupleEquipe.isActive())
				throw new IFT287Exception("Equipe " + nomEquipe + "a encore des participants actifs");

			// Suppression de l'equipe.
			int nb = equipe.supprimer(nomEquipe);
			if (nb == 0)
				throw new IFT287Exception("Equipe " + nomEquipe + " inexistante");

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * affichage d'une equipe
	 */
	public void affichageEquipe(String nomEquipe) throws SQLException, IFT287Exception, Exception {
		// Validation
		try {
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if (tupleEquipe == null)
				throw new IFT287Exception("Equipe inexistant: " + nomEquipe);

			tupleEquipe.setListParticipants(participant.lectureParticipants(nomEquipe));
			tupleEquipe.setListResultats(resultat.lectureResultats(nomEquipe));
			System.out.println(tupleEquipe.toString());
			
			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Lecture des equipes d'une ligue
	 */
	public ArrayList<Equipe> lectureEquipesLigue(String nomLigue) throws SQLException, IFT287Exception, Exception {
		// Validation
		try {
			Ligue tupleLigue = ligue.getLigue(nomLigue);
			if (tupleLigue == null)
				throw new IFT287Exception("Ligue inexistant: " + nomLigue);

			ArrayList<Equipe> listEquipes = equipe.lectureEquipes(nomLigue);

			// Commit
			cx.commit();
			return listEquipes;
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * affichage de l'ensemble des equipes de la table.
	 */
	public void affichageEquipes() throws SQLException, IFT287Exception, Exception {
		try {
			System.out.println("Equipe [");
			for(Equipe eq : equipe.lectureEquipes()) {
				System.out.println("nomEquipe=" + eq.getNomEquipe() + ", matriculeCap="+ eq.getMatriculeCap() + ", nomLigue=" + eq.getNomLigue());
			}
			System.out.println("]");
			//System.out.println(equipe.lectureEquipes());
			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

}
