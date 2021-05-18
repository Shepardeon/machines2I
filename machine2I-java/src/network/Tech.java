package network;

import instance.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tech {

    private class Etat {
        private int demande;
        private int distance;

        public Etat(int demande,int distance){
            this.demande = demande;
            this.distance = distance;

        }

        public void ajouterDemande(int valeur){
            this.demande += valeur;
        }

        public void ajouterDistance(int valeur){
            this.distance += valeur;
        }
    }

    private int idTechnician;
    private int maxDistance;
    private int maxDemande;
    private List<Integer> machines;
    private Point depot;
    private Map<Integer,Boolean> Disponibilite;

    /**
     * CONSTRUCTEUR
     */
    public Tech(int id, int x, int y, int idTechnician, int maxDistance, int maxDemande, List<Integer> machines) {
        depot = new Depot(id, x, y);
        this.idTechnician = idTechnician;
        this.maxDistance = maxDistance;
        this.maxDemande = maxDemande;
        this.machines = machines;
        this.Disponibilite = new HashMap<>();
    }

    public boolean isDisponible(Request request, int jour){
        if(this.machines.get(request.getIdMachine()-1) != 1) return false;
        if(this.Disponibilite.get(jour) != null && !this.Disponibilite.get(jour)){
            //System.out.println("non");
            return false;
        }
        if(idTechnician == 18 && request.getId() == 20){
            System.out.println("demande = "+request.getNbMachine() +", "+maxDemande);
            System.out.println("cout = "+this.depot.getCoutVers(request.getClient())*2+", "+maxDistance);
            System.out.println(request.getNbMachine() <= maxDemande && this.depot.getCoutVers(request.getClient())*2 <= maxDistance);
        }


        return this.Disponibilite.get(jour).demande + request.getNbMachine() <= maxDemande
                && this.Disponibilite.get(jour).distance + this.depot.getCoutVers(request.getClient()) <= maxDistance;
    }

    public void ajouterRequest(Request request,int jour){
        if(isDisponible(request, jour)){
            Disponibilite.computeIfAbsent(jour,k -> new Etat(0,0));
            Disponibilite.get(jour).ajouterDemande(request.getNbMachine());
            Disponibilite.get(jour).ajouterDistance(this.depot.getCoutVers(request.getClient()));
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
