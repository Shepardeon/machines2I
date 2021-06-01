package network;

import instance.Request;
import solution.Tournee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tech {

    private class Etat {
        private Tournee disponible;
        private int fatigue;
        private int demande;
        private int distance;

        public Etat(Tournee disponible, int fatigue){
            this.disponible = disponible;
            this.fatigue = fatigue;
            this.demande = 0;
            this.distance = 0;
        }

        public void setDisponible(Tournee valeur){
            this.disponible = valeur;
        }

        public void setFatigue(int valeur){
            this.fatigue = valeur;
        }

        public void ajouterDistance(int valeur){ this.distance += distance; }

        public int getDistance(){ return distance; }

        public void ajouterDemande(int valeur){this.demande += demande; }

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

    public int getDistance(int jour){
        if (disponibilite.get(jour) != null)
            return disponibilite.get(jour).getDistance();
        return Integer.MAX_VALUE;
    }

    public int getMaxDistance(){
        return maxDistance;
    }

    public boolean isDisponible(Request request, int jour, Tournee t){
        if(this.machines.get(request.getIdMachine()-1) != 1) return false;
        if(this.disponibilite.get(jour).disponible != null && this.disponibilite.get(jour).disponible != t ){
            //System.out.println("non");
            return false;
        }

        /*
        if(jour > 1){
            int x = 1;
            int fatigue = 0;
            while(disponibilite.get(jour+x) != null && !disponibilite.get(jour+x).disponible){
                fatigue = disponibilite.get(jour+x).fatigue;
                x++;
            }
            if(this.disponibilite.get(jour-1).disponible &&
                   this.disponibilite.get(jour-1).fatigue + fatigue + 1 > 5
            ){
                return false;
            }
        }*/

        if(disponibilite.get(jour-2) != null && disponibilite.get(jour-2).fatigue == 5)
            return false;

        int fat = 0;
        if(disponibilite.get(jour-1) != null){
            fat = disponibilite.get(jour-1).fatigue;
        }
        int i = 1;
        while(disponibilite.get(jour+i) != null && disponibilite.get(jour+i).disponible != null){
            fat++;
        }
        if(
            fat+1 > 5 ||
            (fat+1 == 5 && disponibilite.get(jour+i+2) != null && disponibilite.get(jour+i+2).disponible != null)
        )
            return false;

        if(idTechnician == 1 && jour == 8){
            int j = 1;
            while(disponibilite.get(j) != null) {
                System.out.println("Jour =" + j);
                System.out.println("Disponibilité =" + disponibilite.get(j).disponible);
                System.out.println("Fatigue =" + disponibilite.get(j).fatigue);
                j++;
            }
        }

        return request.getNbMachine() <= maxDemande && this.depot.getCoutVers(request.getClient())*2 <= maxDistance;
    }

    public void ajouterRequest(Request request,int jour, Tournee t){

        if(isDisponible(request, jour, t)){
            disponibilite.get(jour).ajouterDistance(t.calculCoutAjoutRequest(request));
            disponibilite.get(jour).ajouterDemande(1);
            int fatigue = 0;
            if(jour != 1) {
                fatigue = this.disponibilite.get(jour - 1).fatigue;
            }
            disponibilite.get(jour).setDisponible(t);
            disponibilite.get(jour).setFatigue(fatigue+1);
            int i = jour;
            while(disponibilite.get(i) != null && disponibilite.get(i).disponible != null){
                disponibilite.get(i).fatigue = fatigue+1;
                i++;
            }
        }
    }

    public boolean isUsed(){
        int i = 1;
        while(disponibilite.get(i) != null){
            if (disponibilite.get(i).disponible != null)
                return true;
            i++;
        }
        return false;
    }

    public void initDays(int days) {
        for(int i = 1; i <=days; i++){
            disponibilite.computeIfAbsent(i, k -> new Etat(null, 0));
            disponibilite.computeIfPresent(i, (k, v) -> new Etat(null, 0));
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
