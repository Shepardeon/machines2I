package network;

import instance.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tech {

    private class Etat {
        private boolean disponible;
        private int fatigue;

        protected Etat(Boolean occupied, int fatigue){
            this.disponible = occupied;
            this.fatigue = fatigue;
        }

        public void setDisponible(boolean valeur){
            this.disponible = valeur;
        }

        public void setFatigue(int valeur){
            this.fatigue += valeur;
        }
    }

    private int idTechnician;
    private int maxDistance;
    private int maxDemande;
    private List<Integer> machines;
    private Point depot;
    private Map<Integer,Etat> disponibilite;

    /**
     * CONSTRUCTEUR
     */
    public Tech(int id, int x, int y, int idTechnician, int maxDistance, int maxDemande, List<Integer> machines) {
        depot = new Depot(id, x, y);
        this.idTechnician = idTechnician;
        this.maxDistance = maxDistance;
        this.maxDemande = maxDemande;
        this.machines = machines;
        this.disponibilite = new HashMap<>();
    }

    public boolean isDisponible(Request request, int jour){
        if(this.machines.get(request.getIdMachine()-1) != 1) return false;
        if(!this.disponibilite.get(jour).disponible){
            //System.out.println("non");
            return false;
        }
        if(jour >= 5){
            // Vérif si le mec a taffé pendant 5 jours de suite
            if(this.disponibilite.get(jour-1) != null && this.disponibilite.get(jour-1).fatigue == 5){
                return false;
            }
            // Vérif si le mec a taffé pendant 5 jours de suite mais n'a pas encore fait ses 2 jours de repos
            if(this.disponibilite.get(jour-1).disponible && this.disponibilite.get(jour-2).fatigue == 5
            ){
                return false;
            }
        }

        return request.getNbMachine() <= maxDemande && this.depot.getCoutVers(request.getClient())*2 <= maxDistance;
    }

    public void ajouterRequest(Request request,int jour){
        if(isDisponible(request, jour)){
            int fatigue;
            fatigue = 0;
            if(this.disponibilite.get(jour-1) != null){
                fatigue = this.disponibilite.get(jour-1).fatigue;
            }
            int finalFatigue = fatigue;
            disponibilite.computeIfAbsent(jour, k -> new Etat(false, finalFatigue +1));
            int i = jour;
            while(!disponibilite.get(i).disponible){
                int fat = 0;
                if(i != 1){
                    fat = disponibilite.get(i-1).fatigue;
                }
                disponibilite.get(i).fatigue = fat+1;
                i++;
            }
        }
    }

    public Point getDepot(){
        return this.depot;
    }

    public void setDepot(Point depot) {
        this.depot = depot;
    }

    public int getIdTechnician(){
        return this.idTechnician;
    }
}
