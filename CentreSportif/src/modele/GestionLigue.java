package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionLigue {

  	//private TableParticipants participant;
  	private TableLigues ligue;
  	private TableEquipes equipe;
  	private TableParticipants participant;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionLigue(TableLigues ligue, TableEquipes equipe, TableParticipants participant) throws IFT287Exception
    {
        this.cx = ligue.getConnexion();
        /*if (participant.getConnexion() != resultat.getConnexion())
            throw new IFT287Exception("Les instances de participant et de resultat n'utilisent pas la m�me connexion au serveur");*/
        this.ligue = ligue;
        this.equipe = equipe;
        this.participant = participant;
    }

    /**
     * Ajout d'une nouvelle ligue vide dans la base de donn�es. S'il existe d�j�, une
     * exception est lev�e.
     */		
    public void ajouterLigueEmpty(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // V�rifie si la ligue existe d�j�
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe d�j�: ");

            // Ajout du participant dans la table des participant
            ligue.creationEmptyLigue(nomLigue, nbJoueurMaxParEquipe);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    
    /**
     * Ajout d'une nouvelle ligue dans la base de donn�es. S'il existe d�j�, une
     * exception est lev�e.
     */		
    public void ajouterLigue(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
    	try
        {
        	Ligue tupleLigue = new Ligue(nomLigue, nbJoueurMaxParEquipe);
            // V�rifie si la ligue existe d�j�
        	
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe d�j�: ");
            if (!tupleLigue.testNewEquipes(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" comprend une �quipe d�j� dans une autre ligue ");

            // Ajout de la ligue dans la table des ligures
            ligue.creationEmptyLigue(nomLigue, nbJoueurMaxParEquipe);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    
    /**
     * modifier le nombre de joueur max par equipe pour une ligue dans la base de donn�es. 
     */		
    public void modifierNombreJoueurMax(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // V�rifie si la ligue existe d�j�
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe d�j�: ");
            
            // Ajout de la ligue dans la table des ligures
            ligue.modifierNbJoueursMaxParEquipe(nomLigue, nbJoueurMaxParEquipe);;
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
        
    
    /**
     * Supprime Ligue de la base de donn�es.
     */
    public void supprime(String nomLigue) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            Ligue tupleLigue = ligue.getLigue(nomLigue);
            if (tupleLigue == null)
                throw new IFT287Exception("Ligue inexistant: " + nomLigue);
            if (participant.nombreMembresLigue(nomLigue) > 0)
                throw new IFT287Exception("Ligue " + nomLigue + "a encore des participants actifs");
            
            // Suppression des equipes de la ligue.
            int nbEquipe = equipe.supprimerEquipesLigue(nomLigue);
            // Suppression de la ligue.
            int nbLique = ligue.supprimer(nomLigue);
            if (nbLique == 0)
                throw new IFT287Exception("Ligue " + nomLigue + " inexistante");
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
