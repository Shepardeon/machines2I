package network;

import java.util.List;

public class Tech {

    private int idTechnician;
    private int maxDistance;
    private int maxDemande;
    private List<Integer> machines;
    private Depot depot;

    /**
     * CONSTRUCTEUR
     */
    public Tech(int id, int x, int y, int idTechnician, int maxDistance, int maxDemande, List<Integer> machines) {
        depot = new Depot(id, x, y);
        this.idTechnician = idTechnician;
        this.maxDistance = maxDistance;
        this.maxDemande = maxDemande;
        this.machines = machines;
    }

    public Depot getDepot(){
        return depot;
    }

    public int getIdTechnician(){
        return idTechnician;
    }
}
