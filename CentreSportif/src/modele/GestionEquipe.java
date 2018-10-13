package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionEquipe {

	private TableEquipes equipe;
	private Connexion cx;

	/**
	 * Creation d'une instance
	 */
	public GestionEquipe(TableEquipes equipe) throws IFT287Exception {
		this.cx = equipe.getConnexion();
		this.equipe = equipe;
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
	
}
