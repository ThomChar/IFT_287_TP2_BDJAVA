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
					"Les instances de participant et de equipe n'utilisent pas la même connexion au serveur");

		this.participant = participant;
		this.equipe = equipe;
	}

	/**
	 * Ajout d'un nouveau particpant dans la base de données. S'il existe déjà , une
	 * exception est levée.
	 */
	public void ajouter(String matricule, String prenom, String nom, String motDePasse, String nomEquipe, String statut)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le participant existe déjà
			if (participant.existe(matricule))
				throw new IFT287Exception("Particpant existe déjà : " + matricule);
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
	 * Modifier nom et prenom d'un participant dans la base de données. S'il existe
	 * déjà , une exception est levée.
	 */
	public void modifierNomPrenom(String matricule, String prenom, String nom)
			throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le nouveau nom et prenom existe déjà pour un autre participant
			if (participant.existePrenom(prenom))
				throw new IFT287Exception("Un participant avec le prenom : " + prenom + "existe déjà");
			if (participant.existeNom(prenom))
				throw new IFT287Exception("Un participant avec le nom : " + nom + "existe déjà");

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
	 * Modifier mot de passe d'un participant dans la base de données. S'il existe
	 * déjà , une exception est levée.
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
	 * n'existe pas , une exception est levée.
	 */
	public void ajouteParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Participant "+matricule+" n'existe pas");
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionnée " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné postule déjà pour une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est déjà dans une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est déjà dans cette equipe");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant postule déjà pour cette equipe");

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
	 * n'existe pas , une exception est levée.
	 */
	public void accepteParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Particpant"+matricule+ "n'existe pas");
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionnée " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné postule déjà pour une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est déjà dans une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est déjà dans votre equipe");

			participant.accepteParEquipe(nomEquipe, matricule);

			// Accepte à la liste de l'équipe

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
	 * n'existe pas , une exception est levée.
	 */
	public void refuseParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Particpant n'existe pas : " + matricule);
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionnée " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est déjà dans une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("REFUSE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné a deja refuse pour cette equipe");

			participant.refuseParEquipe(nomEquipe, matricule);

			// Refuse à la liste de l'équipe

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
	 * n'existe pas , une exception est levée.
	 */
	public void supprimeParEquipe(String nomEquipe, String matricule) throws SQLException, IFT287Exception, Exception {
		try {
			// Vérifie si le participant existe
			if (!participant.existe(matricule))
				throw new IFT287Exception("Participant n'existe pas : " + matricule);
			if (!equipe.existe(nomEquipe))
				throw new IFT287Exception("L'equipe selectionnée " + nomEquipe + " est introuvable");
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné postule déjà pour une autre équipe : "
						+ participant.getParticipant(matricule).getNomEquipe());
			if (participant.getParticipant(matricule).getStatut().equals("EN ATTENTE")
					&& participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception(
						"Le Participant selectionné postule pour cette equipe, il ne peut pas être supprimé mais peut être refusé");
			if (participant.getParticipant(matricule).getStatut().equals("ACCEPTE")
					&& !participant.getParticipant(matricule).getNomEquipe().equals(nomEquipe))
				throw new IFT287Exception("Le Participant selectionné est dans une autre equipe");
			
			Equipe tupleEquipe = equipe.getEquipe(nomEquipe);
			if(tupleEquipe.getMatriculeCap().equals(matricule)) {
				equipe.changerCapitaine(nomEquipe, null);
			}
			participant.supprimeParEquipe(nomEquipe, matricule);
			// Refuse à la liste de l'équipe

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
						+ tupleParticipant.getNomEquipe() + "et ne peut pas être supprimer");

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
	 * Lecture des participants d'une équipe
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
	 * Lecture des participants d'une équipe
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
