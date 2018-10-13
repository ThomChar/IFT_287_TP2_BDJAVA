package modele;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class TableLigues {

	private PreparedStatement stmtExiste;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtInsertEmpty;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtUpdateListEquipes;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtDispEquipes;
	private Connexion cx;

	/**
	 * Creation d'une instance. PrÃ©compilation d'Ã©noncÃ©s SQL.
	 */
	public TableLigues(Connexion cx) throws SQLException {
		this.cx = cx;
		stmtExiste = cx.getConnection()
				.prepareStatement("select nomLigue, nbJoueurMaxParEquipe, listEquipes, from Ligue where nomLigue = ?");
		stmtInsertEmpty = cx.getConnection().prepareStatement(
				"insert into Ligue (nomLigue, nbJoueurMaxParEquipe) " + "values (?,?)");
		stmtInsert = cx.getConnection().prepareStatement(
				"insert into Ligue (nomLigue, nbJoueurMaxParEquipe, listEquipes) " + "values (?,?,?)");
		stmtUpdate = cx.getConnection()
				.prepareStatement("update Ligue set  nbJoueurMaxParEquipe = ? where nomLigue = ?");
		stmtUpdateListEquipes = cx.getConnection()
				.prepareStatement("update Ligue set  listEquipes = ? where nomLigue = ?");
		stmtDelete = cx.getConnection().prepareStatement("delete from Ligue where nomLigue = ?");
	}

	/**
	 * Retourner la connexion associee.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	/**
	 * Verifie si une ligue existe.
	 */
	public boolean existe(String nomLigue) throws SQLException {
		stmtExiste.setString(1, nomLigue);
		ResultSet rset = stmtExiste.executeQuery();
		boolean ligueExiste = rset.next();
		rset.close();
		return ligueExiste;
	}

	/**
	 * Lecture d'une Ligue.
	 */
	public Ligue getLigue(String nomLigue) throws SQLException {
		stmtExiste.setString(1, nomLigue);
		ResultSet rset = stmtExiste.executeQuery();
		if (rset.next()) {
			Ligue tupleLigue = new Ligue();
			tupleLigue.setNomLigue(nomLigue);
			tupleLigue.setNbJoueurMaxParEquipe(rset.getInt(1));
			// A regarder pour recuperer arraylist

			/*tupleLigue.setListEquipes(
					new ArrayList<Equipe>((Collection<? extends Equipe>) Arrays.asList(rset.getArray(2))));*/
			rset.close();
			return tupleLigue;
		} else {
			return null;
		}
	}

	/**
	 * Ajout d'une nouvelle ligue (vide).
	 * 
	 * @throws IFT287Exception
	 */
	public void creationEmptyLigue(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception {
		/* Ajout d'une ligue vide. */
		/*
		 * if (existe(nomLigue)) throw new IFT287Exception("Cette ligue existe déjà");
		 */

		stmtInsertEmpty.setString(1, nomLigue);
		stmtInsertEmpty.setInt(2, nbJoueurMaxParEquipe);
		stmtInsertEmpty.executeUpdate();
	}

	/**
	 * Ajout d'une nouvelle ligue (vide).
	 * 
	 * @throws IFT287Exception
	 */
	public void creation(String nomLigue, int nbJoueurMaxParEquipe, ArrayList<Equipe> listEquipes)
			throws SQLException, IFT287Exception {
		/* Ajout d'une ligue non vide. */
		/*
		 * if (existe(nomLigue)) throw new IFT287Exception("Cette ligue existe déjà");
		 */

		stmtInsert.setString(1, nomLigue);
		stmtInsert.setInt(2, nbJoueurMaxParEquipe);
		stmtInsert.setArray(3, (Array) listEquipes);
		stmtInsert.executeUpdate();
	}
	
	/**
	 * Modifier le nombre de Joueur max par equipe pour une ligue.
	 * 
	 * @throws IFT287Exception
	 */
	public void modifierNbJoueursMaxParEquipe(String nomLigue, int nbJoueurMaxParEquipe)
			throws SQLException, IFT287Exception {
		
		stmtUpdate.setInt(1, nbJoueurMaxParEquipe);
		stmtUpdate.setString(2, nomLigue);
		stmtUpdate.executeUpdate();
	}
	
	/**
	 * Modifier le contenu de la liste des equipe pour une ligue.
	 * 
	 * @throws IFT287Exception
	 */
	public void modifierListEquipes(String nomLigue, ArrayList<Equipe> listEquipes)
			throws SQLException, IFT287Exception {
		
		stmtUpdateListEquipes.setArray(1, (Array)listEquipes);
		stmtUpdateListEquipes.setString(2, nomLigue);
		stmtUpdateListEquipes.executeUpdate();
	}

	/**
	 * Suppression d'une ligue. regarder si ligue n'est pas active avant de
	 * supprimer
	 */
	public int supprimer(String nomLigue) throws SQLException {
		stmtDelete.setString(1, nomLigue);
		return stmtDelete.executeUpdate();
	}
	
	/**
	 * afficher toutes les equipes d'une ligue.
	 * 
	 * @throws IFT287Exception
	 */
	public void afficher(String nomLigue)
			throws SQLException, IFT287Exception {
		
		stmtUpdateListEquipes.setString(1, nomLigue);
		stmtUpdateListEquipes.executeUpdate();
	}

}
