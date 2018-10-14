package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;

public class TableResultats {

	private PreparedStatement stmtExiste;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtDispResultat;
	private PreparedStatement stmtNbMGagne;
	private PreparedStatement stmtNbMPerdu;
	private PreparedStatement stmtNbMNul;
	private PreparedStatement stmtDispResultatsEquipe;
	private Connexion cx;

	/**
	 * Creation d'une instance. Des annones SQL pour chaque requête sont
	 * précompilés.
	 */
	public TableResultats(Connexion cx) throws SQLException {
		this.cx = cx;
		stmtExiste = cx.getConnection().prepareStatement(
				"select nomEquipeA, nomEquipeB from Resultat where nomEquipeA = ? and nomEquipeB = ?");
		stmtInsert = cx.getConnection().prepareStatement(
				"insert into Resultat (nomEquipeA, nomEquipeB, scoreEquipeA, scoreEquipeB) " + "values (?,?,?,?)");
		stmtUpdate = cx.getConnection().prepareStatement(
				"update Resultat set scoreEquipeA = ?, scoreEquipeB = ? where nomEquipeA = ? and nomEquipeA = ?");
		stmtDelete = cx.getConnection()
				.prepareStatement("delete from Resultat where nomEquipeA = ? and nomEquipeB = ?");
		stmtDispResultat = cx.getConnection()
				.prepareStatement("select nomEquipeA, nomEquipeB, scoreEquipeA, scoreEquipeB from Resultat");
		stmtNbMGagne = cx.getConnection()
				.prepareStatement("select count(*) from Resultat where (nomEquipeA = ? and scoreEquipeA > scoreEquipeB) || (nomEquipeB = ? and scoreEquipeA < scoreEquipeB)");
		stmtNbMPerdu = cx.getConnection()
				.prepareStatement("select count(*) from Resultat where (nomEquipeA = ? and scoreEquipeA < scoreEquipeB) || (nomEquipeB = ? and scoreEquipeA > scoreEquipeB)");
		stmtNbMNul = cx.getConnection()
				.prepareStatement("select count(*) from Resultat where (nomEquipeA = ? || nomEquipeB = ?) and scoreEquipeA = scoreEquipeB");
		stmtDispResultatsEquipe = cx.getConnection().prepareStatement("select * from Resultat where nomEquipe = ?");

	}

	/**
	 * Retourner la connexion associée.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	/**
	 * Vérifie si un resultat existe.
	 */
	public boolean existe(String nomEquipeA, String nomEquipeB) throws SQLException {
		stmtExiste.setString(1, nomEquipeA);
		stmtExiste.setString(2, nomEquipeB);
		ResultSet rset = stmtExiste.executeQuery();
		boolean equipeExiste = rset.next();
		rset.close();
		return equipeExiste;
	}

	/**
	 * Lecture d'un resultat.
	 */
	public Resultat getResultat(String nomEquipeA, String nomEquipeB) throws SQLException {
		stmtExiste.setString(1, nomEquipeA);
		stmtExiste.setString(1, nomEquipeB);
		ResultSet rset = stmtExiste.executeQuery();
		if (rset.next()) {
			Resultat tupleResultat = new Resultat();
			tupleResultat.setNomEquipeA(nomEquipeA);
			tupleResultat.setNomEquipeB(nomEquipeB);
			tupleResultat.setScoreEquipeA(rset.getInt(3));
			tupleResultat.setScoreEquipeB(rset.getInt(4));
			rset.close();
			return tupleResultat;
		} else
			return null;
	}

	/**
	 * Ajout d'un nouveau resultat dans la base de données.
	 * 
	 * @throws SQLException
	 */
	public void ajouter(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB) throws SQLException {

		stmtInsert.setString(1, nomEquipeA);
		stmtInsert.setString(2, nomEquipeB);
		stmtInsert.setInt(3, scoreEquipeA);
		stmtInsert.setInt(4, scoreEquipeB);
		stmtInsert.executeUpdate();
	}

	/**
	 * modifier le resultat pour un match.
	 */

	public int modifier(String nomEquipeA, String nomEquipeB, int scoreEquipeA, int scoreEquipeB) throws SQLException {
		stmtUpdate.setString(3, nomEquipeA);
		stmtUpdate.setString(4, nomEquipeB);
		stmtUpdate.setInt(1, scoreEquipeA);
		stmtUpdate.setInt(2, scoreEquipeB);
		return stmtUpdate.executeUpdate();
	}

	/**
	 * Supprimer Resultat dans la base de données
	 */
	public int supprimer(String nomEquipeA, String nomEquipeB) throws SQLException {
		stmtDelete.setString(1, nomEquipeA);
		stmtDelete.setString(2, nomEquipeB);
		return stmtUpdate.executeUpdate();
	}

	/**
	 * Obtenir nombre match gagné d'une équipe.
	 * 
	 * @throws SQLException
	 */
	public int ObtenirNbMGagne(String nomEquipe) throws SQLException {
		stmtNbMGagne.setString(1, nomEquipe);
		stmtNbMGagne.setString(2, nomEquipe);
		return stmtNbMGagne.executeUpdate();
	}

	/**
	 * Obtenir nombre match perdu d'une équipe.
	 * 
	 * @throws SQLException
	 */
	public int ObtenirNbMPerdu(String nomEquipe) throws SQLException {
		stmtNbMPerdu.setString(1, nomEquipe);
		stmtNbMPerdu.setString(2, nomEquipe);
		return stmtNbMPerdu.executeUpdate();
	}

	/**
	 * Obtenir nombre match nul d'une équipe.
	 * 
	 * @throws SQLException
	 */
	public int ObtenirNbMNul(String nomEquipe) throws SQLException {
		stmtNbMNul.setString(1, nomEquipe);
		stmtNbMNul.setString(2, nomEquipe);
		return stmtNbMNul.executeUpdate();
	}

	/**
	 * afficher les resultats pour un match.
	 */
	public void afficher() throws SQLException {
		ResultSet rset = stmtDispResultat.executeQuery();
		rset.close();
	}
	
	/**
	 * lecture des resultats de l'équipe
	 * 
	 * @throws SQLException
	 */
	public ArrayList<Resultat> lectureResultats(String nomEquipe) throws SQLException {
		stmtDispResultatsEquipe.setString(1, nomEquipe);
		ResultSet rset = stmtDispResultatsEquipe.executeQuery();

		ArrayList<Resultat> listResultats = new ArrayList<Resultat>();

		while (rset.next()) {
			Resultat tupleResultat = new Resultat();
			tupleResultat.setNomEquipeA(rset.getString("nomEquipeA"));
			tupleResultat.setNomEquipeB(rset.getString("nomEquipeB"));
			tupleResultat.setScoreEquipeA(rset.getInt("scoreEquipeA"));
			tupleResultat.setScoreEquipeB(rset.getInt("scoreEquipeB"));
			rset.close();
			listResultats.add(tupleResultat);
		}
		rset.close();
		return listResultats;
	}

}
