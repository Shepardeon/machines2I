package solveur;

import instance.Instance;
import instance.Request;
import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;
import solution.TourneeTruck;

public class Ajout implements Solveur {

    @Override
    public String getNom() {
        return "Résolution par ajouts";
    }

    /*
    Ajoute la requete à la suite d'une tournée existante, si aucune n'est possible, crée une nouvelle tournée
     */
    @Override
    public Solution solve(Instance instance) {
        Solution solu = new Solution(instance);

        boolean ok;
        for (Request r : instance.getRequests()) { // Tournee Truck
            ok = false;
            for (int j = 0; j <= solu.getListeTournees().size(); j++) {
                for (Tournee t : solu.getListeTournees().get(j)) {
                    if (!ok && solu.ajouterClientTourneeTruck(r, t)) ok = true;
                }
            }
            if (!ok) {
                solu.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay()); // je sais pas trop pour le jour
                // truck infini encore ?
            }
        }


        for (Request r : instance.getRequests()) {
            ok = false;
            int day = 0;
            for (int j = 0; j <= solu.getListeTournees().size(); j++) {
                for (Tournee t : solu.getListeTournees().get(j)) {
                    if (t instanceof TourneeTech) {
                        if (!ok && solu.ajouterClientTourneeTech(r, (TourneeTech) t)) ok = true;
                    }
                }
            }
            while (!ok && solu.ajouterClientNouvelleTourneeTech(r, r.getFirstDay() + day + 1)) day++;
        }

        return solu;
    }
}