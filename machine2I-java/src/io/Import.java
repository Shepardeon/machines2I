package io;

// TO CHECK : import des classes Instance, Client, Depot et Point
import instance.Instance;
import network.Client;
import network.Depot;
import network.Point;
import io.exception.FileExistException;
import io.exception.FormatFileException;
import io.exception.OpenFileException;
import io.exception.ReaderException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de lire une instance pour les TPs du cours de LE4-SI
 * POO pour l'optimisation.
 * 
 * Les instances sont fournies sur moodle au format ".vrp".
 * 
 * Pour que le lecteur d'instance fonctionne correctement, il faut que les 
 * signatures des constructeurs des classes Depot, Client, et Instances, ainsi 
 * que la methode ajouterClient de la classe Instance soient bien conformes a 
 * la description dans le sujet du TP.
 * Des commentaires annotes avec 'TO CHECK' vous permettent de facilement reperer
 * dans cette classe les lignes que vous devez verifier et modifier si besoin. 
 * 
 * @author Maxime Ogier
 */
public class Import {
    /**
     * Le fichier contenant l'instance.
     */
    private File instanceFile;
    
    /**
     * Constructeur par donnee du chemin du fichier d'instance.
     * @param inputPath le chemin du fichier d'instance, qui doit se terminer 
     * par l'extension du fichier (.xml).
     * @throws ReaderException lorsque le fichier n'est pas au bon format ou 
     * ne peut pas etre ouvert.
     */
    public Import(String inputPath) throws ReaderException {
        if (inputPath == null) {
            throw new OpenFileException();
        }
        if (!inputPath.endsWith(".vrp")) {
            throw new FormatFileException("vrp", "vrp");
        }
        this.instanceFile = new File(inputPath);
    }
    
    /**
     * Methode principale pour lire le fichier d'instance.
     * @return l'instance lue
     * @throws ReaderException lorsque les donnees dans le fichier d'instance 
     * sont manquantes ou au mauvais format.
     */
    public Instance readInstance() throws ReaderException {
        try{
            FileReader f = new FileReader(this.instanceFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(f);
            String nom = lireNom(br);
            int capacite = lireCapacite(br);
            Map<Integer, Point> points = lirePoints(br);
            List<Client> clients = lireDemandes(br, points);
            Depot depot = lireDepot(br, points);
            // TO CHECK : constructeur de la classe Instance
            Instance instance = new Instance(nom, capacite, depot);
            for(Client c : clients) {
                // TO CHECK : ajout d'un client dans la classe Instance
                instance.ajouterClient(c);
            }
            br.close();
            f.close();
            return instance;
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        } catch (IOException ex) {
            throw new ReaderException("IO exception", ex.getMessage());
        }
    }
    
    /**
     * Lecture du nom de l'instance.
     * La ligne dans le fichier doit commencer par "NAME :"
     * @param br lecteur courant du fichier d'instance
     * @return le nom de l'instance
     * @throws IOException une erreur fichier
     */
    private String lireNom(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("NAME :")) {
            ligne = br.readLine();
        }
        ligne = ligne.replace(" ", "");
        ligne = ligne.replace("NAME:", "");
        return ligne;
    }
    
    /**
     * Lecture des coordonees des points.
     * La section dans le fichier doit commencer par "NODE_COORD_SECTION"
     * puis chaque ligne contient : id, abscisse, ordonnee.
     * La section se termine par "DEMAND_SECTION"
     * @param br lecteur courant du fichier d'instance
     * @return tous les points de l'instance, avec des ids uniques
     * @throws IOException une erreur fichier
     */
    private Map<Integer,Point> lirePoints(BufferedReader br) throws IOException {
        Map<Integer, Point> points = new LinkedHashMap<>();
        String ligne = br.readLine();
        while(!ligne.contains("NODE_COORD_SECTION")) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        while(!ligne.contains("DEMAND_SECTION")) {
            Point p = lireUnPoint(ligne);
            points.put(p.getId(), p);
            ligne = br.readLine();
        }
        return points;
    }
    
    /**
     * Lecture de la capacite de camions.
     * La ligne doit commencer par "CAPACITY :"
     * @param br le lecteur courant du fichier d'instance
     * @return la capacite des camions
     * @throws IOException une erreur fichier
     */
    private int lireCapacite(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("CAPACITY :")) {
            ligne = br.readLine();
        }
        ligne = ligne.replace(" ", "");
        ligne = ligne.replace("CAPACITY:", "");
        ligne = ligne.trim();

        return Integer.parseInt(ligne);
    }
    
    /**
     * Lecture des demandes des clients.
     * Cette methode doit etre appelee juste apres la methode lirePoints.
     * La lecture se termine par la ligne "DEPOT_SECTION".
     * Seuls les clients avec une demande strictement positive sont renvoyes.
     * @param br le lecteur courant du fichier d'instance
     * @param points l'ensemble des points (lus avec la methode lirePoints)
     * @return l'ensemble des clients avec une demande strictement positive
     * @throws IOException une erreur fichier
     */
    private List<Client> lireDemandes(BufferedReader br, Map<Integer, Point> points) 
            throws IOException {
        List<Client> clients = new ArrayList<>();
        String ligne = br.readLine();
        while(!ligne.contains("DEPOT_SECTION")) {
            Client c = lireUneDemande(ligne, points);
            if(c != null) {
                clients.add(c);
            }
            ligne = br.readLine();
        }
        return clients;
    }

    /**
     * Lecture d'un point sur une ligne.
     * @param ligne ligne du fichier d'instance contenant un point avec : id, 
     * abscisse, ordonnee
     * @return un point (client avec demande de 0)
     * @throws NumberFormatException une erreur de format
     */
    private Point lireUnPoint(String ligne) throws NumberFormatException {
        ligne = ligne.strip();
        String[] values = ligne.split(" |\t");
        int id = Integer.parseInt(values[0]);
        int x = Integer.parseInt(values[1]);
        int y = Integer.parseInt(values[2]);
        // TO CHECK : constructeur de la classe Client
        // ordre des paramètres : quantite, identifiant, abscisse, ordonnee
        return new Client(0,id,x,y);
    }
    
    /**
     * Lecture d'un client avec sa demande.
     * A partir de l'id du client, on recupere le point correspondant, et on cree
     * un client avec les ccaracteristiques du point et sa demande.
     * @param ligne ligne du fichier de texte dans laquelle on a l'id du client 
     * et sa demande
     * @param points tous les points de l'instance
     * @return un client avec demande positive, null si la demande est negative 
     * ou nulle
     * @throws NumberFormatException une erreur format
     */
    private Client lireUneDemande(String ligne, Map<Integer, Point> points) 
            throws NumberFormatException {
        ligne = ligne.strip();
        String[] values = ligne.split(" |\t");
        int id = Integer.parseInt(values[0]);
        int q = Integer.parseInt(values[1]);
        if(q <= 0) {
            return null;
        }
        Point p = points.get(id);
        // TO CHECK : constructeur de la classe Client
        // ordre des paramètres : quantite, identifiant, abscisse, ordonnee
        return new Client(q, p.getId(), p.getX(), p.getY());
    }
    
    /**
     * Lecture du depot.
     * Parmi les point de l'instance, on choisit celui dont l'id est celui du depot.
     * Cette methode doit etre appelee juste apres la methode lireDemandes
     * @param br le lecteur courant du fichier d'instance
     * @param points les points de l'instance (lus avec la methode lirePoints)
     * @return le depot de l'instance
     * @throws IOException une erreur fichier
     */
    private Depot lireDepot(BufferedReader br, Map<Integer, Point> points) throws IOException {
        String ligne = br.readLine();
        ligne = ligne.strip();
        int id = Integer.parseInt(ligne);
        Point p = points.get(id);
        // TO CHECK : constructeur de la classe Depot
        // ordre des paramètres : identifiant, abscisse, ordonnee
        return new Depot(p.getId(), p.getX(), p.getY());
    }
    
    /**
     * Test de lecture d'une instance.
     * @param args les arguments console
     */
    public static void main(String[] args) {
        try {
            Import reader = new Import("instances/A-n32-k5.vrp");
            reader.readInstance();
            System.out.println("Instance lue avec success !");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
