package instance;

import network.Client;
import network.Depot;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Instance {
    /**
     * PARAMETRE
     */
    private final String nom;
    private final int capacite;
    private final Depot depot;
    private final Map<Integer, Client> mapClients;

    /**
     * CONSTRUCTEUR
     */
    public Instance(String nom, int capacite, Depot depot) {
        this.nom = nom;
        this.capacite = capacite;
        this.depot = depot;
        this.mapClients = new LinkedHashMap<>();
    }

    /**
     * METHODES
     */
    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public Depot getDepot() {
        return depot;
    }

    /**
     * Fonction qui renvoie le nombre de client dans cette instance
     * @return le nombre de clients
     */
    public int getNbClients() {
        return mapClients.size();
    }

    /**
     * Fonction permettant de récupérer un client à partir de son ID
     * @param id l'id du client à rechercher
     * @return le client dont l'id correspond à la recherche ou null
     */
    public Client getClientByID(int id) {
        return mapClients.get(id);
    }

    /**
     * Fonction pour récupérer la liste de tous les clients de l'instance
     * @return une LinkedList, copie de la liste des clients
     */
    public LinkedList<Client> getClients() {
        return new LinkedList<>(mapClients.values());
    }

    /**
     * Fonction qui ajoute un client à la liste des clients en créant toutes les routes nécessaires entre ce client,
     * lee dépot et les autres clients
     * @param client le client à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterClient(Client client) {
        if (client == null || mapClients.containsValue(client))
            return false;

        mapClients.put(client.getId(), client);
        client.ajouterRoute(depot);
        for (Client c : mapClients.values())
            client.ajouterRoute(c);
        return true;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", depot=" + depot +
                ", mapClients=" + mapClients +
                '}';
    }
}
