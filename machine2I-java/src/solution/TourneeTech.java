package solution;

import instance.Instance;
import network.Client;

public class TourneeTech extends Tournee {


    public TourneeTech() {
        super();
    }

    public TourneeTech(Instance instance) {
        super(instance);
    }

    public TourneeTech(Tournee tournee) {
        super(tournee);
    }

    @Override
    public boolean ajouterClient(Client client) {
        return false;
    }

    @Override
    public boolean checkCalculerDemandeTotale() {
        return false;
    }@Override
    public boolean canInsererClient(Client client) {
        return false;
    }

    @Override
    public boolean check() {
        return false;
    }
}

