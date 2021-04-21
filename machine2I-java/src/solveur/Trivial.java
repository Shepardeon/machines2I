package solveur;

import instance.Instance;
import instance.Request;
import solution.Solution;
import solution.TourneeTech;
import solution.TourneeTruck;

public class Trivial implements Solveur{

    @Override
    public String getNom() {
        return "Résolution trivial";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solu = new Solution(instance);

        for(Request r : instance.getRequests()) {
            solu.ajouterClientNouvelleTourneeTruck(r,1); // Pour rappel on a camion infini

        }
        for(Request r : instance.getRequests()) {
            int day = 2;
            if(!solu.ajouterClientNouvelleTourneeTech(r,day)){
                day++;
                solu.ajouterClientNouvelleTourneeTech(r,day);
            }
        }

        return solu;
    }
}
