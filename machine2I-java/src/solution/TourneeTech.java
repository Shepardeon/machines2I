package solution;

import instance.Instance;
import instance.Request;
import network.Tech;
import network.Client;
import network.Point;

public class TourneeTech extends Tournee {
    private Tech technician;

    public TourneeTech(Instance instance) {
        super(instance);
    }

    public TourneeTech(TourneeTech tourneetech) {
        super(tourneetech);
        this.technician = tourneetech.technician;
        this.depot = tourneetech.technician.getDepot();
    }

    public Tech getTechnician(){
        return technician;
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
        return false;
    }

    @Override
    public boolean canInsererRequest(Request request) {
        return false;
    }

    @Override
    public boolean check() {
        return false;
    }
}

