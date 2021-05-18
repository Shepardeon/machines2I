package solution;

import instance.Instance;
import instance.Request;
import network.Depot;
import network.Tech;
import network.Client;
import network.Point;

public class TourneeTech extends Tournee {
    private Tech technician;

    public TourneeTech(Instance instance, int jour, Tech technician) {
        super(instance, jour);
        this.technician = technician;
        this.depot = technician.getDepot();
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

            coutTotal += this.calculCoutAjoutRequest(request);
            technician.ajouterRequest(request, jour);
            //System.out.println(coutTotal); // TODO : OOF
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
        return technician.isDisponible(request, jour) && request.getJourLivraison() < jour;
    }

    @Override
    public boolean check() {
        return false;
    }
}

