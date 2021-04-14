package solution;

import instance.Instance;
import network.Client;

public class TourneeTruck extends Tournee {

    public TourneeTruck() {
        super();
    }

    public TourneeTruck(Instance instance) {
        super(instance);
    }

    public TourneeTruck(Tournee tournee) {
        super(tournee);
    }



    @Override
    public boolean ajouterClient(Client client) {
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
    public boolean canInsererClient(Client client) {
        return false;
    }

    @Override
    public boolean check() {
        return false;
    }
}
