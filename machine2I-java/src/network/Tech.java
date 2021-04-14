package network;

import instance.Machine;

import java.util.List;

public class Tech extends Depot {

    private int idTechnician;
    private int maxDistance;
    private int maxDemande;
    private List<Integer> machines;

    /**
     * CONSTRUCTEUR
     */
    public Tech(int id, int x, int y, int idTechnician, int maxDistance, int maxDemande, List<Integer> machines) {
        super(id, x, y);
        this.idTechnician = idTechnician;
        this.maxDistance = maxDistance;
        this.maxDemande = maxDemande;
        this.machines = machines;
    }
}
