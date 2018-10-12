package modele;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import CentreSportif.Connexion;

public class TableEquipes {

	private PreparedStatement stmtExiste;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtUpdateCompoEquipe;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtDispEquipes;
	private PreparedStatement stmtDispParticipants;
	private Connexion cx;

	/**
	 * Creation d'une instance. Precompilation d'annonces SQL.
	 */
	public TableEquipes(Connexion cx) throws SQLException {
		this.cx = cx;
		stmtExiste = cx.getConnection().prepareStatement(
				"select nomEquipe, matriculeCap, nomLigue, listParticipants, listResultats from Equipe where nomEquipe = ?");
		stmtInsert = cx.getConnection().prepareStatement(
				"insert into Equipe (nomEquipe, matriculeCap, nomLigue, listParticipants) "
						+ "values (?,?,?,?)");
		stmtUpdate = cx.getConnection().prepareStatement(
				"update Equipe set nomEquipe = ?,matriculeCap = ?,listParticipants = ?,listResultats = ? where nomEquipe = ?");
		stmtUpdateCompoEquipe = cx.getConnection().prepareStatement(
				"update Equipe set listParticipants = ?  where nomEquipe = ?");
		stmtDelete = cx.getConnection().prepareStatement("delete from Equipe where nomEquipe = ?");
		//stmtDispEquipes = cx.getConnection().prepareStatement("SELECT * FROM participant NATURAL JOIN Equipe E WHERE nomEquipe = ?");
		stmtDispParticipants = cx.getConnection().prepareStatement("SELECT * FROM Participant WHERE nomEquipe = ?");
	}

	/**
	 * Retourner la connexion associée.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	/**
	 * Vérifie si une Equipe existe.
	 */
	public boolean existe(String nomEquipe) throws SQLException {
		stmtExiste.setString(1, nomEquipe);
		ResultSet rset = stmtExiste.executeQuery();
		boolean equipeExiste = rset.next();
		rset.close();
		return equipeExiste;
	}

	/**
	 * Lecture d'une Equipe.
	 */
	public Equipe getEquipe(String nomEquipe) throws SQLException {
		stmtExiste.setString(1, nomEquipe);
		ResultSet rset = stmtExiste.executeQuery();
		if (rset.next()) {
			Equipe tupleEquipe = new Equipe();
			tupleEquipe.setNomEquipe(nomEquipe);
			tupleEquipe.setMatriculeCap(rset.getString(1));
			tupleEquipe.setNomLigue(rset.getString(2));

			// A regarder pour recuperer arraylist
			tupleEquipe.setListParticipants(
					new ArrayList<Participant>((Collection<? extends Participant>) Arrays.asList(rset.getArray(3))));
			tupleEquipe.setListResultats(
					new ArrayList<Resultat>((Collection<? extends Resultat>) Arrays.asList(rset.getArray(4))));

			rset.close();
			return tupleEquipe;
		} else {
			return null;
		}
	}

	/**
	 * Ajout d'une nouvelle equipe non vide.
	 */
	public void creer(String nomEquipe, String matriculeCap, String nomLigue, ArrayList<Participant> listParticipants)
			throws SQLException {
		/* Ajout de l'equipe. */
		stmtInsert.setString(1, nomEquipe);
		stmtInsert.setString(2, matriculeCap);
		stmtInsert.setString(3, nomLigue);
		stmtInsert.setArray(4, (Array) listParticipants);
		stmtInsert.executeUpdate();
	}

	/**
	 * Suppression d'une equipe.
	 */
	public int supprimer(String nomEquipe) throws SQLException {
		stmtDelete.setString(1, nomEquipe);
		return stmtDelete.executeUpdate();
	}

	/**
	 * modifie la liste des joueurs d'une equipe.
	 * @throws SQLException 
	 */
	public void modifierJoueurEquipe(String nomEquipe, ArrayList<Participant> listParticipants) throws SQLException {
		stmtUpdateCompoEquipe.setArray(2, (Array) listParticipants);
		stmtUpdateCompoEquipe.setString(2, nomEquipe);
		stmtUpdateCompoEquipe.executeUpdate();		
	}
	
	
	/**
	 * affiche une equipe precise.
	 * @throws SQLException 
	 */
	public void afficherEquipe(String nomEquipe) throws SQLException {
		
		stmtExiste.setString(1, nomEquipe);
		ResultSet rset = stmtExiste.executeQuery();
		rset.close();
	}
	
	/**
	 * affiche la liste des equipes.
	 * @throws SQLException 
	 */
	public void afficherListEquipes() throws SQLException {
		ResultSet rset = stmtDispEquipes.executeQuery();
		rset.close();
	}
	
	/**
	 * lecture des participants de l'�quipe
	 * @throws SQLException
	 */
	public ArrayList<Participant> lectureParticipants(String nomEquipe) throws SQLException {
		stmtDispParticipants.setString(1, nomEquipe);
		ResultSet rset = stmtDispParticipants.executeQuery();
		
		ArrayList<Participant> listeParticipants = new ArrayList<Participant>();
		
		while(rset.next()) {
			Participant tupleParticipant = new Participant();
        	tupleParticipant.setMatricule(rset.getString("matricule"));
        	tupleParticipant.setPrenom(rset.getString("prenom"));
        	tupleParticipant.setNom(rset.getString("nom"));
        	tupleParticipant.setMotDePasse(rset.getString("motDePasse"));
        	tupleParticipant.setNomEquipe(rset.getString("nomEquipe"));
        	tupleParticipant.setStatut(rset.getString("statut"));
            rset.close();
            listeParticipants.add(tupleParticipant);
		}
		rset.close();
		return listeParticipants;		
	}
}
