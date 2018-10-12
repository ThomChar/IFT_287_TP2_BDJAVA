package modele;

import java.sql.SQLException;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionResultat {

	private TableResultats resultat;
	private Connexion cx;

	public GestionResultat(TableResultats resultat) throws IFT287Exception {
		/*
		 * if (resultat.getConnexion() != membre.getConnexion() ||
		 * reservation.getConnexion() != membre.getConnexion()) throw new
		 * BiblioException(
		 * "Les instances de livre, de membre et de reservation n'utilisent pas la mÃªme connexion au serveur"
		 * );
		 */
		this.cx = resultat.getConnexion();
		this.resultat = resultat;

	}

	public void InscrireResulat(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Verifier si resultat equipeA contre EquipeB existe
			Resultat tupleResultat = resultat.getResultat(nomEquipeA,nomEquipeB);
			if (tupleResultat == null)
				throw new IFT287Exception("Resultat inexistant: " + nomEquipeA + "contre" + nomEquipeB );


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
	public void supprimerResultat(String nomEquipeA, String nomEquipeB) throws SQLException, IFT287Exception, Exception {
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
	public void modifierResultat(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB) throws SQLException, IFT287Exception, Exception {
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

}
