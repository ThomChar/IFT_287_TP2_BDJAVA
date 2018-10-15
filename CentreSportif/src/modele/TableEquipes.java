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
import CentreSportif.IFT287Exception;

public class TableEquipes {

	private PreparedStatement stmtExiste;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtUpdateCompoEquipe;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtDispEquipes;
	private PreparedStatement stmtDispEquipesLigue;
	private PreparedStatement stmtDispParticipants;
<<<<<<< HEAD
	private PreparedStatement stmtDispEquipesParLigue;
=======
	private PreparedStatement stmtNombreMembresEquipe;
	private PreparedStatement stmtDeleteEquipesLigue;
>>>>>>> remi
	private Connexion cx;

	/**
	 * Creation d'une instance. Precompilation d'annonces SQL.
	 */
	public TableEquipes(Connexion cx) throws SQLException {
		this.cx = cx;
		stmtExiste = cx.getConnection()
				.prepareStatement("select nomEquipe, matriculeCapitaine, nomLigue from Equipe where nomEquipe = ?");
		stmtInsert = cx.getConnection()
				.prepareStatement("insert into Equipe (nomEquipe, matriculeCapitaine, nomLigue) " + "values (?,?,?)");
		stmtUpdate = cx.getConnection()
				.prepareStatement("update Equipe set nomEquipe = ?,matriculeCapitaine = ? where nomEquipe = ?");
		stmtDelete = cx.getConnection().prepareStatement("delete from Equipe where nomEquipe = ?");
<<<<<<< HEAD
		stmtDispEquipes = cx.getConnection().prepareStatement("select nomEquipe, matriculeCapitaine, nomLigue from Equipe");
		stmtDispEquipesLigue = cx.getConnection().prepareStatement("select * from Equipe where nomLigue = ?");
		stmtDispEquipesParLigue = cx.getConnection().prepareStatement("select * from Equipe order by nomLigue");
=======
		stmtDeleteEquipesLigue = cx.getConnection().prepareStatement("delete from Equipe where nomLigue = ?");
		stmtDispEquipes = cx.getConnection().prepareStatement("select nomEquipe, matriculeCap, nomLigue from Equipe");
		stmtDispEquipesLigue = cx.getConnection().prepareStatement("select * from Equipe where nomLigue = ?");
		stmtNombreMembresEquipe = cx.getConnection().prepareStatement("select COUNT(*) AS nb FROM Participant WHERE nomEquipe = ?");
>>>>>>> remi
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
			tupleEquipe.setMatriculeCap(rset.getString(2));
			tupleEquipe.setNomLigue(rset.getString(3));

			// A regarder pour recuperer arraylist
			/*
			 * tupleEquipe.setListParticipants( new ArrayList<Participant>((Collection<?
			 * extends Participant>) Arrays.asList(rset.getArray(3))));
			 * tupleEquipe.setListResultats( new ArrayList<Resultat>((Collection<? extends
			 * Resultat>) Arrays.asList(rset.getArray(4))));
			 */

			rset.close();
			return tupleEquipe;
		} else {
			return null;
		}
	}

	/**
	 * Ajout d'une nouvelle equipe non vide.
	 */
	public void creer(String nomEquipe, String matriculeCap, String nomLigue) throws SQLException {
		/* Ajout de l'equipe. */
		stmtInsert.setString(1, nomEquipe);
		stmtInsert.setString(2, matriculeCap);
		stmtInsert.setString(3, nomLigue);
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
	 * Suppression des �quipes d'une ligue.
	 */
	public int supprimerEquipesLigue(String nomLigue) throws SQLException {
		stmtDeleteEquipesLigue.setString(1, nomLigue);
		return stmtDeleteEquipesLigue.executeUpdate();
	}

	/**
	 * lecture des equipes de l'�quipe
	 * 
	 * @throws SQLException
	 */
	public ArrayList<Equipe> lectureEquipes(String nomLigue) throws SQLException {
		stmtDispEquipesLigue.setString(1, nomLigue);
		ResultSet rset = stmtDispEquipesLigue.executeQuery();

		ArrayList<Equipe> listEquipes = new ArrayList<Equipe>();

		while (rset.next()) {
			Equipe tupleEquipe = new Equipe();
			tupleEquipe.setNomEquipe(rset.getString("nomEquipe"));
			tupleEquipe.setMatriculeCap("matriculeCap");
			tupleEquipe.setNomLigue(rset.getString("nomLigue"));
			rset.close();
			listEquipes.add(tupleEquipe);
		}
		rset.close();
		return listEquipes;
	}
	
	/**
	 * lecture des equipes de l'�quipe
	 * 
	 * @throws SQLException
	 */
	public ArrayList<Equipe> lectureEquipes() throws SQLException {
		ResultSet rset = stmtDispEquipesParLigue.executeQuery();

		ArrayList<Equipe> listEquipes = new ArrayList<Equipe>();

		while (rset.next()) {
			Equipe tupleEquipe = new Equipe();
			tupleEquipe.setNomEquipe(rset.getString("nomEquipe"));
			tupleEquipe.setMatriculeCap("matriculeCap");
			tupleEquipe.setNomLigue(rset.getString("nomLigue"));
			//rset.close();
			listEquipes.add(tupleEquipe);
		}
		rset.close();
		return listEquipes;
	}
}
