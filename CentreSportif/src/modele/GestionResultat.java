package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionResultat {

	private TableResultats resultat;
	private TableEquipes equipe;
	private Connexion cx;

	public GestionResultat(TableResultats resultat, TableEquipes equipe) throws IFT287Exception {

		if (resultat.getConnexion() != equipe.getConnexion())
			throw new IFT287Exception(
					"Les instances de resultat, et de equipe n'utilisent pas la même connexion au serveur");

		this.cx = resultat.getConnexion();
		this.resultat = resultat;
		this.equipe = equipe;

	}

	public void InscrireResulat(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Verifier si resultat equipeA contre EquipeB existe
			Resultat tupleResultat = resultat.getResultat(nomEquipeA, nomEquipeB);
			if (tupleResultat == null)
				throw new IFT287Exception("Resultat inexistant: " + nomEquipeA + "contre" + nomEquipeB);

			// Creation du resultat
			resultat.ajouter(nomEquipeA, nomEquipeB, scoreEquipeA, scoreEquipeB);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Supprime resultat. Le resultat doit exister
	 */
	public void supprimerResultat(String nomEquipeA, String nomEquipeB)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Verifier si resultat existe
			if (resultat.supprimer(nomEquipeA, nomEquipeB) == 0)
				throw new IFT287Exception("Resultat entre " + nomEquipeA + " et " + nomEquipeB + " n'existe pas");

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * modifier resultat. Le resultat doit exister
	 */
	public void modifierResultat(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Verifier si resultat existe
			if (!resultat.existe(nomEquipeA, nomEquipeB))
				throw new IFT287Exception("Resultat entre " + nomEquipeA + " et " + nomEquipeB + " n'existe pas");

			resultat.modifier(nomEquipeA, nomEquipeB, scoreEquipeA, scoreEquipeB);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * affichage de l'ensemble des résultats de la table.
	 */
	public void affichageResultats() throws SQLException, IFT287Exception, Exception {
		try {
			resultat.afficher();

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}
	
	/**
	 * Obtenir nombre match gagné d'une équipe.
	 * 
	 * @throws SQLException
	 * @throws IFT287Exception 
	 */
	public int ObtenirNbMGagne(String nomEquipe) throws SQLException, IFT287Exception {
		
		if (!equipe.existe(nomEquipe))
			throw new IFT287Exception("Equipe inexistante : " + nomEquipe);
		
		int nbMatchGagne = resultat.ObtenirNbMGagne(nomEquipe);
		
		return nbMatchGagne;
	}
	
	/**
	 * Obtenir nombre match perdu d'une équipe.
	 * 
	 * @throws SQLException
	 * @throws IFT287Exception 
	 */
	public int ObtenirNbMPerdu(String nomEquipe) throws SQLException, IFT287Exception {
		
		if (!equipe.existe(nomEquipe))
			throw new IFT287Exception("Equipe inexistante : " + nomEquipe);
		
		int nbMatchPerdu = resultat.ObtenirNbMPerdu(nomEquipe);
		
		return nbMatchPerdu;
	}

	/**
	 * Obtenir nombre match nul d'une équipe.
	 * 
	 * @throws SQLException
	 * @throws IFT287Exception 
	 */
	public int ObtenirNbMNul(String nomEquipe) throws SQLException, IFT287Exception {
		
		if (!equipe.existe(nomEquipe))
			throw new IFT287Exception("Equipe inexistante : " + nomEquipe);
		
		int nbMatchNul = resultat.ObtenirNbMNul(nomEquipe);
		
		return nbMatchNul;
	}

	
	/**
	 * Affichage des resultats d'une equipe et du nombre de match perdu, gagné et nul
	 */
	public ArrayList<Resultat> lectureParticipants(String nomEquipe) throws SQLException, IFT287Exception, Exception {
		
		// Validation
		if (!equipe.existe(nomEquipe))
			throw new IFT287Exception("Equipe inexistante : " + nomEquipe);
		
		ArrayList<Resultat> listResultats = resultat.lectureResultats(nomEquipe);

		return listResultats;
	}
}
