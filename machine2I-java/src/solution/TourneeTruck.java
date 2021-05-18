package solution;

import instance.Instance;
import instance.Machine;
import instance.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class TourneeTruck extends Tournee {
    private int capacity;
    private int maxCapacity;
    private Map<Integer, Machine> mapMachines;

    public TourneeTruck(Instance instance, int jour) {
        super(instance, jour);
        this.depot = instance.getDepot();
        this.capacity = instance.getTruckCapacity();
        this.mapMachines = instance.getMapMachines();
        this.maxCapacity = instance.getTruckCapacity();
    }

    public TourneeTruck(TourneeTruck tourneeTruck) {
        super(tourneeTruck);
        this.depot = tourneeTruck.getDepot();
        this.capacity = tourneeTruck.getCapacity();
        this.mapMachines = tourneeTruck.getMapMachines();
        this.maxCapacity = tourneeTruck.getMaxCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Map<Integer, Machine> getMapMachines() {
        return new LinkedHashMap<Integer, Machine>(mapMachines);
    }

    @Override
    public boolean ajouterRequest(Request request) {
        if(canInsererRequest(request)){
            request.setJourLivraison(jour);

            coutTotal += this.calculCoutAjoutRequest(request);
            this.listRequest.add(request);
            //System.out.println(coutTotal); // TODO : OOF
            capacity += this.instance.getMapMachines().get(request.getIdMachine()).getSize();

            return true;
        }
        return false;
    }

    @Override
    public boolean canInsererRequest(Request request) {
        if (request == null) return false;

        return capacity + mapMachines.get(request.getIdMachine()).getSize() * request.getNbMachine() > maxCapacity;
    }

    @Override
    public boolean check() {
        return false;
    }
}
