package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;
//import java.util.LinkedList;
//import java.util.List;
import java.util.ArrayList;

import CentreSportif.Connexion;

public class TableParticipants {
	

	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteNom;
	private PreparedStatement stmtExistePrenom;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtUpdateNomPrenom;
    private PreparedStatement stmtUpdateMotDePasse;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtDispParticipant;
    private PreparedStatement stmtDispParticipants;
    private Connexion cx;

    /**
     * Creation d'une instance. Des annones SQL pour chaque requ�te sont
     * pr�compil�s.
     */
    public TableParticipants(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement(
                "select matricule, prenom, nom, motDePasse, nomEquipe, statut from Participant where matricule = ?");
        stmtExisteNom = cx.getConnection().prepareStatement(
                "select matricule, prenom, nom, motDePasse, nomEquipe, statut from Participant where nom = ?");
        stmtExistePrenom = cx.getConnection().prepareStatement(
                "select matricule, prenom, nom, motDePasse, nomEquipe, statut from Participant where prenom = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into Participant (matricule, prenom, nom, motDePasse, nomEquipe, statut) "
                        + "values (?,?,?,?,null,null)");
        stmtUpdate = cx.getConnection()
                .prepareStatement("update Participant set nomEquipe = ?, statut = ? " + "where matricule = ?");
        stmtUpdateNomPrenom = cx.getConnection().prepareStatement("update Participant set nom = ?, prenom = ? " + "where matricule = ?");
        stmtUpdateMotDePasse = cx.getConnection().prepareStatement("update Participant set motDePasse = ? " + "where matricule = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from Participant where matricule = ?");
        stmtDispParticipant = cx.getConnection().prepareStatement("select matricule, prenom, nom, motDePasse, nomEquipe, statut from Participant");
        stmtDispParticipants = cx.getConnection().prepareStatement("select * from Participant where nomEquipe = ?");
    }

    /**
     * Retourner la connexion associ�e.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * V�rifie si un participant existe.
     */
    public boolean existe(String matricule) throws SQLException
    {
        stmtExiste.setString(1, matricule);
        ResultSet rset = stmtExiste.executeQuery();
        boolean participantExiste = rset.next();
        rset.close();
        return participantExiste;
    }
    
    /**
     * V�rifie si un prenom de participant existe.
     */
    public boolean existePrenom(String prenom) throws SQLException
    {
        stmtExistePrenom.setString(1, prenom);
        ResultSet rset = stmtExistePrenom.executeQuery();
        boolean participantExiste = rset.next();
        rset.close();
        return participantExiste;
    }
    
    /**
     * V�rifie si un nom de participant existe.
     */
    public boolean existeNom(String nom) throws SQLException
    {
        stmtExisteNom.setString(1, nom);
        ResultSet rset = stmtExisteNom.executeQuery();
        boolean participantExiste = rset.next();
        rset.close();
        return participantExiste;
    }

    /**
     * Lecture d'un participant.
     */
    public Participant getParticipant(String matricule) throws SQLException
    {
        stmtExiste.setString(1, matricule);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
        	Participant tupleParticipant = new Participant();
        	tupleParticipant.setMatricule(matricule);
        	tupleParticipant.setPrenom(rset.getString(2));
        	tupleParticipant.setNom(rset.getString(3));
        	tupleParticipant.setMotDePasse(rset.getString(4));
        	tupleParticipant.setNomEquipe(rset.getString(5));
        	tupleParticipant.setStatut(rset.getString(6));
            rset.close();
            return tupleParticipant;
        }
        else
            return null;
    }

    /**
     * Ajout d'un nouveau participant dans la base de donn�es.
     * @throws SQLException 
     */
    public void ajouter(String matricule, String prenom, String nom, String motDePasse, String nomEquipe,
			String statut) throws SQLException {
    	
        stmtInsert.setString(1, matricule);
        stmtInsert.setString(2, prenom);
        stmtInsert.setString(3, nom);
        stmtInsert.setString(4, motDePasse);
        stmtInsert.setString(5, nomEquipe);
        stmtInsert.setString(6, statut);
        stmtInsert.executeUpdate();
    }
    
    /**
     * Modifier nom et prenom d'un participant dans la base de donn�es.
     * @throws SQLException 
     */
    public void modifierNomPrenom(String matricule, String prenom, String nom) throws SQLException {
    	
    	stmtUpdateNomPrenom.setString(3, matricule);
    	stmtUpdateNomPrenom.setString(2, prenom);
    	stmtUpdateNomPrenom.setString(1, nom);
    	stmtUpdateNomPrenom.executeUpdate();
    }
    
    /**
     * Modifier motDePasse d'un participant dans la base de donn�es.
     * @throws SQLException 
     */
    public void modifierMotDePasse(String matricule, String motDePasse) throws SQLException {
    	
    	stmtUpdateMotDePasse.setString(1, motDePasse);
    	stmtUpdateMotDePasse.setString(2, matricule);
    	stmtUpdateMotDePasse.executeUpdate();
    }
    
    /**
     * Accepter le participant dans une equipe.
     */
    
    public int accepteParEquipe(String nomEquipe, String matricule) throws SQLException
    {
        stmtUpdate.setString(1, nomEquipe);
        stmtUpdate.setString(2, "ACCEPTE");
        stmtUpdate.setString(3, matricule);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Ajouter Participant disponible dans une equipe
     */
    public int ajouteParEquipe(String nomEquipe, String matricule) throws SQLException
    {
    	stmtUpdate.setString(1, nomEquipe);
        stmtUpdate.setString(2, "EN ATTENTE");
        stmtUpdate.setString(3, matricule);
        return stmtUpdate.executeUpdate();
    }
    
    /**
     * Refuser Participant disponible dans une equipe
     */
    public int refuseParEquipe(String nomEquipe, String matricule) throws SQLException
    {
    	stmtUpdate.setString(1, nomEquipe);
        stmtUpdate.setString(2, "REFUSE");
        stmtUpdate.setString(3, matricule);
        return stmtUpdate.executeUpdate();
    }
    
    /**
     * Supprimer Participant dans une equipe
     */
    public int supprimeParEquipe(String nomEquipe, String matricule) throws SQLException
    {
    	stmtUpdate.setString(1, nomEquipe);
        stmtUpdate.setString(2, "SUPPRIME");
        stmtUpdate.setString(3, matricule);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Suppression d'un participant du complexe.
     * @throws SQLException 
     */
    public int supprimer(String matricule) throws SQLException {
        stmtDelete.setString(1, matricule);
        return stmtDelete.executeUpdate();
    }

    /**
     * afficher les participants.
     */ 
    public void afficherParticipant() throws SQLException
    {
    	ResultSet rset = stmtDispParticipant.executeQuery();
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
            listeParticipants.add(tupleParticipant);
		}
		rset.close();
		return listeParticipants;		
	}
 }
