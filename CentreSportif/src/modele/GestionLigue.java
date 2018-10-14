package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import CentreSportif.Connexion;
import CentreSportif.IFT287Exception;

public class GestionLigue {

  	//private TableParticipants participant;
  	private TableLigues ligue;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionLigue(TableLigues ligue) throws IFT287Exception
    {
        this.cx = ligue.getConnexion();
        /*if (participant.getConnexion() != resultat.getConnexion())
            throw new IFT287Exception("Les instances de participant et de resultat n'utilisent pas la même connexion au serveur");*/
        this.ligue = ligue;
    }

    /**
     * Ajout d'une nouvelle ligue vide dans la base de données. S'il existe déjà , une
     * exception est levée.
     */		
    public void ajouterLigueEmpty(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la ligue existe déjà
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe déjà : ");

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
     * Ajout d'une nouvelle ligue dans la base de données. S'il existe déjà , une
     * exception est levée.
     */		
    public void ajouterLigue(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
    	try
        {
        	Ligue tupleLigue = new Ligue(nomLigue, nbJoueurMaxParEquipe);
            // Vérifie si la ligue existe déjà
        	
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe déjà : ");
            if (!tupleLigue.testNewEquipes(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" comprend une équipe déjà dans une autre ligue ");

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
     * modifier le nombre de joueur max par equipe pour une ligue dans la base de données. 
     */		
    public void modifierNombreJoueurMax(String nomLigue, int nbJoueurMaxParEquipe) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la ligue existe déjà
            if (ligue.existe(nomLigue))
                throw new IFT287Exception("Ligue "+nomLigue+" existe déjà : ");
            
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
     * Supprime Ligue de la base de données.
     */
    public void supprime(String nomLigue) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            Ligue tupleLigue = ligue.getLigue(nomLigue);
            if (tupleLigue == null)
                throw new IFT287Exception("Ligue inexistant: " + nomLigue);
            if (tupleLigue.isActive())
                throw new IFT287Exception("Ligue " + nomLigue + "a encore des equipes et participants actifs");

            // Suppression de la ligue.
            int nb = ligue.supprimer(nomLigue);
            if (nb == 0)
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
