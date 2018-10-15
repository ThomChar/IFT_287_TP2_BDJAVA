package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.IFT287Exception;
import CentreSportif.Connexion;

public class GestionParticipant {

	private TableParticipants participant;
	private TableEquipes equipe;
	private Connexion cx;

	/**
	 * Creation d'une instance
	 */
	public GestionParticipant(TableParticipants participant , TableEquipes equipe) throws IFT287Exception {
		this.cx = participant.getConnexion();

		if (participant.getConnexion() != equipe.getConnexion())
			throw new IFT287Exception(
					"Les instances de participant et de equipe n'utilisent pas la m�me connexion au serveur");

		this.participant = participant;
		this.equipe = equipe;
	}

	/**
	 * Ajout d'un nouveau particpant dans la base de donn�es. S'il existe d�j�, une
	 * exception est lev�e.
	 */
	public void ajouter(String matricule, String prenom, String nom, String motDePasse, String nomEquipe, String statut)
			throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le participant existe d�j�
			if (participant.existe(matricule))
				throw new IFT287Exception("Particpant existe d�j�: " + matricule);
			// Ajout du participant dans la table des participant
			participant.ajouter(matricule, prenom, nom, motDePasse, nomEquipe, statut);
			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Modifier nom et prenom d'un participant dans la base de donn�es. S'il existe
	 * d�j�, une exception est lev�e.
	 */
	public void modifierNomPrenom(String matricule, String prenom, String nom)
			throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le nouveau nom et prenom existe d�j� pour un autre participant
			if (participant.existePrenom(prenom))
				throw new IFT287Exception("Un participant avec le prenom�: " + prenom + "existe d�j�");
			if (participant.existeNom(prenom))
				throw new IFT287Exception("Un participant avec le nom�: " + nom + "existe d�j�");

			// modification du participant dans la table des participant
			participant.modifierNomPrenom(matricule, prenom, nom);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Modifier mot de passe d'un participant dans la base de donn�es. S'il existe
	 * d�j�, une exception est lev�e.
	 */
	public void modifierMotDePasse(String matricule, String motDePasse)
			throws SQLException, IFT287Exception, Exception {
		try {
			// modification du participant dans la table des participant
			participant.modifierMotDePasse(matricule, motDePasse);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Ajouter un participant dans une equipe du point de vu participant. S'il
	 * n'existe pas�, une exception est lev�e.
	 */
	public void ajouteParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Participant "+matricule+" n'existe pas");
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionn�e " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� postule d�j� pour une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est d�j� dans une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est d�j� dans cette equipe");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant postule d�j� pour cette equipe");

			participant.ajouteParEquipe(nomEquipe, matricule);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Accepter un participant dans une equipe du point de vu participant. S'il
	 * n'existe pas�, une exception est lev�e.
	 */
	public void accepteParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Particpant"+matricule+ "n'existe pas");
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionn�e " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� postule d�j� pour une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est d�j� dans une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est d�j� dans votre equipe");

			participant.accepteParEquipe(nomEquipe, matricule);

			// Accepte � la liste de l'�quipe

			/*
			 * Participant joueur = participant.getParticipant(matricule);
			 * ArrayList<Participant> listJoueurs =
			 * equipe.getEquipe(nomEquipe).getListParticipants(); for (Participant membre :
			 * listJoueurs) { if (membre.getMatricule().equals(joueur.getMatricule())) {
			 * membre.setStatut("ACCEPTE"); } } equipe.modifierJoueurEquipe(nomEquipe,
			 * listJoueurs);
			 */

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Refuse un participant dans une equipe du point de vu participant. S'il
	 * n'existe pas�, une exception est lev�e.
	 */
	public void refuseParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Particpant n'existe pas�: " + matricule);
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionn�e " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est d�j� dans une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("REFUSE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� a deja refuse pour cette equipe");

			participant.refuseParEquipe(nomEquipe, matricule);

			// Refuse � la liste de l'�quipe

			/*
			 * Participant joueur = participant.getParticipant(matricule);
			 * ArrayList<Participant> listJoueurs =
			 * equipe.getEquipe(nomEquipe).getListParticipants();
			 * listJoueurs.remove(joueur); equipe.modifierJoueurEquipe(nomEquipe,
			 * listJoueurs);
			 */

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Supprimer un participant dans une equipe du point de vu participant. S'il
	 * n'existe pas�, une exception est lev�e.
	 */
	public void supprimeParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// V�rifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Participant n'existe pas�: " + matricule);
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionn�e " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� postule d�j� pour une autre �quipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception(
						"Le Participant selectionn� postule pour cette equipe, il ne peut pas �tre supprim� mais peut �tre refus�");
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionn� est dans une autre equipe");
			
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if(tupleEquipe.getMatriculeCap().equals(matricule)) {
				equipe.changerCapitaine(nomEquipe, null);
			}
			participant.supprimeParEquipe(nomEquipe, matricule);
			// Refuse � la liste de l'�quipe

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Supprime Participant.
	 */
	public void supprime(String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// Validation
			Participant tupleParticipant = participant.getParticipant(matricule);
			if (tupleParticipant == null)
				throw new IFT287Exception("Particpant inexistant: " + matricule);
			if (tupleParticipant.isActive())
				throw new IFT287Exception("Particpant " + matricule + " est dans equipe "
						+ tupleParticipant.getNomEquipe() + "et ne peut pas �tre supprimer");

			// Suppression du participant.
			int nb = participant.supprimer(matricule);
			if (nb == 0)
				throw new IFT287Exception("Particpant " + matricule + " inexistant");

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Lecture des participants d'une �quipe
	 */
	public ArrayList<Participant> lectureParticipants(String nomEquipe)
			throws SQLException, IFT287Exception, Exception {
		// Validation
		try {
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if (tupleEquipe == null)
				throw new IFT287Exception("Equipe inexistant: " + nomEquipe);
			if (!tupleEquipe.isActive())
				throw new IFT287Exception("Equipe " + nomEquipe + "a encore des participants actifs");

			ArrayList<Participant> listeParticipant = participant.lectureParticipants(nomEquipe);

			// Commit
			cx.commit();
			return listeParticipant;
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * Lecture des participants d'une �quipe
	 */
	public void affichageParticipants(String nomEquipe) throws SQLException, IFT287Exception, Exception {
		// Validation
		try {
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if (tupleEquipe == null)
				throw new IFT287Exception("Equipe inexistant: " + nomEquipe);
			if (!tupleEquipe.isActive())
				throw new IFT287Exception("Equipe " + nomEquipe + "a encore des participants actifs");

			ArrayList<Participant> listeParticipant = participant.lectureParticipants(nomEquipe);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	 * affichage de l'ensemble des participants de la table.
	 */
	public void affichageParticipants() throws SQLException, IFT287Exception, Exception {
		try {
			participant.afficherParticipant();

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

}
