package instance;

import network.Client;
import network.Depot;
import network.Tech;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Instance {
    /**
     * PARAMETRE
     */
    private final String dataset;
    private final String nom;
    private final int days;
    private final int truckCapacity;
    private final int truckDistanceCost;
    private final int truckMaxDistance;
    private final int truckDayCost;
    private final int truckCost;
    private final int technicianDistanceCost;
    private final int technicianDayCost;
    private final int technicianCost;
    private final List<Tech> technicians;
    private final List<Machine> machines;
    private final Depot depot;
    private final Map<Integer, Client> mapClients;

    /**
     * CONSTRUCTEUR
     */
    public Instance(String dataset,
                    String nom,
                    int truckMaxDistance,
                    int technicianCost,
                    Depot depot,
                    int days,
                    int truckCapacity,
                    int truckDistanceCost,
                    int truckDayCost,
                    int truckCost,
                    int technicianDistanceCost,
                    int technicianDayCost
    ) {
        this.dataset = dataset;
        this.nom = nom;
        this.depot = depot;
        this.technicianDistanceCost = technicianDistanceCost;
        this.technicianDayCost = technicianDayCost;
        this.technicianCost = technicianCost;
        this.days = days;
        this.technicians = new LinkedList<>();
        this.machines = new LinkedList<>();
        this.truckCapacity = truckCapacity;
        this.truckDistanceCost = truckDistanceCost;
        this.truckMaxDistance = truckMaxDistance;
        this.truckDayCost = truckDayCost;
        this.truckCost = truckCost;
        this.mapClients = new LinkedHashMap<>();
    }

    /**
     * METHODES
     */
    public String getNom() {
        return nom;
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
        for (Tech t : technicians)
            client.ajouterRoute(t);
        return true;
    }

    /**
     * Fonction qui ajoute un technician à la liste des technicians en créant toutes les routes nécessaires
     * @param technician le technician à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterTech(Tech technician) {
        if (technician == null || technicians.contains(technician))
            return false;
        technicians.add(technician);
        for (Client c : mapClients.values())
            technician.ajouterRoute(c);
        for (Tech t : technicians)
            technician.ajouterRoute(t);
        return true;
    }

    /**
     * Fonction qui ajoute une machine à la liste des machine
     * @param machine la machine à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterMachine(Machine machine) {
        if (machine == null || machines.contains(machine))
            return false;
        machines.add(machine);
        return true;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "nom='" + nom + '\'' +
                ", depot=" + depot +
                ", mapClients=" + mapClients +
                ", Techs=" + technicians +
                ", machines=" + machines +
                '}';
    }
}
