// Travail fait par :
//   Charlet Thomas - 18-146-249
//   Hilleriteau R�mi - 18-146-347

package CentreSportif;

import java.io.*;
import java.util.StringTokenizer;

import modele.GestionEquipe;
import modele.GestionLigue;
import modele.GestionParticipant;
import modele.GestionResultat;
import modele.TableEquipes;
import modele.TableLigues;
import modele.TableParticipants;
import modele.TableResultats;

import java.sql.*;
//Les param�tres pour ex�cuter notre programme dinf ift287_23db ift287_23 paique
/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 16 août 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class CentreSportif
{
    private static Connexion cx;
    private static GestionEquipe gestionEquipe;
    private static GestionLigue gestionLigue;
    private static GestionParticipant gestionParticipant;
    private static GestionResultat gestionResultat;
    private static TableEquipes tableEquipes;
    private static TableLigues tableLigues;
    private static TableParticipants tableParticipants;
    private static TableResultats tableResultats;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
    	if (args.length < 4)
        {
            System.out.println("Usage: java CentreSportif.CentreSportif <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }
        
        cx = null;
        
        try
        {
        	
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!
            cx = new Connexion(args[0], args[1], args[2], args[3]);
            BufferedReader reader = ouvrirFichier(args);  
            String transaction = lireTransaction(reader);
            Init();
            while (!finTransaction(transaction))
            {
                executerTransaction(transaction);
                transaction = lireTransaction(reader);
            }
        }
        finally
        {
            if (cx != null)
                cx.fermer();
        }
    }
    
    static void Init() throws SQLException, IFT287Exception
    {
    	tableLigues = new TableLigues(cx);
    	tableEquipes = new TableEquipes(cx);
    	tableParticipants = new TableParticipants(cx);
    	tableResultats = new TableResultats(cx);
        gestionLigue = new GestionLigue(tableLigues);
        gestionEquipe = new GestionEquipe(tableEquipes);
        gestionParticipant = new GestionParticipant(tableParticipants);
        gestionResultat = new GestionResultat(tableResultats);
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try
        {
            System.out.print(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".
                if (command.equals("commande1"))
                {
                    // Lecture des parametres
                    String param1 = readString(tokenizer);
                    Date param2 = readDate(tokenizer);
                    int param3 = readInt(tokenizer);
                    // Appel de la methode des gestionnaires qui traite la transaction specifique
                }
                else if (command.equals("commande2"))
                {
                    // Lire les parametres ici et appeler la bonne methode
                    // de traitement pour la transaction
                }
                else if(command.equals("ajouterLigue"))
                {
                	//int j = Integer.parseInt(tokenizer.nextToken());
                	String dateEmprunt = readString(tokenizer);
                	int idMembre = readInt(tokenizer);
                   /* System.out.println("\n1-"+dateEmprunt+"2-"+idMembre+"-");
                    dateEmprunt="llllll";
                    idMembre=5;*/
                	gestionLigue.ajouterLigue(dateEmprunt, idMembre);
                }
                else
                {
                    System.out.println(" : Transaction non reconnue");
                }
                
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
            cx.rollback();
        }
    }

    
    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Integer.valueOf(token).intValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Date.valueOf(token);
            }
            catch (IllegalArgumentException e)
            {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

}
