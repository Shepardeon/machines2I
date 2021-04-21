package solution;

import instance.Instance;
import network.Client;

public class TourneeTruck extends Tournee {
    private int capacity;
    private int maxCapacity;
    private Map<Integer, Machine> mapMachines;

    public TourneeTruck(Instance instance) {
        super(instance);
        this.depot = instance.getDepot();
        this.capacity = instance.getTruckCapacity();
        this.mapMachines = instance.getMapMachines();
    }

    public TourneeTruck(Tournee tournee) {
        super(tournee);
    }

    @Override
    public boolean ajouterRequest(Request request) {
        if(canInsererRequest(request)){
            this.listRequest.add(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCalculerDemandeTotale() {
        int dTotale = 0;
        for (Client c : this.getListClient()) {
            dTotale += c.getRequest().getNbMachine();
        }

        boolean test = dTotale == this.getDemandeTotale();
        if (!test)
            System.out.println("Erreur Test checkCalculerDemandeTotale:\n\tdemande totale théorique: " + dTotale +
                    "\n\tdemande effective: " + this.getDemandeTotale());
        return test;
    }

    @Override
    public boolean canInsererRequest(Request request) {
        if( capacity + mapMachines.get(request.getIdMachine()).getSize() * request.getNbMachine() > maxCapacity){

        }
        return false;
    }

    @Override
    public boolean check() {
        return false;
    }
}
