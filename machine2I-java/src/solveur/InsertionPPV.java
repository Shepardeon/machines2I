package solveur;

import instance.Instance;
import instance.Request;
import solution.Solution;

import java.util.LinkedList;

public class InsertionPPV implements Solveur {

    @Override
    public String getNom() {
        return "InsertionPPV";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        LinkedList<Request> listRequest = instance.getRequests();

        // Insertion PPV pour les tournées truck
        Request r = listRequest.getFirst();

        while (!listRequest.isEmpty()) {
            if (r == null) r = listRequest.getFirst();
            if (!sol.ajouterClientDerniereTourneeTruck(r))
                sol.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay());

            // whoop whoop faudrait amélio install les machines un jour...
            sol.ajouterClientNouvelleTourneeTech(r, r.getJourLivraison() + 1);
            listRequest.remove(r);
            r = plusProcheVoisinTruck(r, listRequest);
        }

        return sol;
    }

    private Request plusProcheVoisinTruck(Request r, LinkedList<Request> listRequest) {
        if (listRequest.isEmpty()) return null;

        Request minR = getFirstSameDay(r.getFirstDay(), listRequest);
        if (minR == null) return null;

        int min = minR.getClient().getCoutVers(r.getClient());

        for (Request req : listRequest)
            if (min > r.getClient().getCoutVers(req.getClient()) && req.getFirstDay() == r.getFirstDay()) {
                minR = req;
                min = r.getClient().getCoutVers(req.getClient());
            }

        return minR;
    }

    private Request getFirstSameDay(int day, LinkedList<Request> listRequest) {
        if (listRequest.isEmpty()) return null;

        for (Request r : listRequest)
            if (r.getFirstDay() == day)
                return r;

        return null;
    }
}
