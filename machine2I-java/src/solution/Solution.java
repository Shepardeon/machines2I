package solution;

import instance.Instance;
import instance.Request;
import network.Tech;

import java.util.*;

public class Solution {
    /*
     * PARAMETRES
     */
    private long coutTotal;
    private final Instance instance;
    private final Map<Integer,LinkedList<Tournee>> listeTournees;
    private int techCost; // Cout par technician utilisé
    private int techDayCost; // Cout par jour par technician
    private int techDistanceCost; // Cout par m pour technician
    private int truckCost; // Cout par truck utilisé
    private int truckDayCost; // Cout par jour par truck
    private int truckDistanceCost; // Cout par m pour truck
    private long truckDistance;
    private int numberTruckDays;
    private int numberTrucksUsed;
    private long technicianDistance;
    private int numberTechnicianDays;
    private int numberTechniciansUsed;
    private long totaltechCost; // cout total pour technician
    private long totaltruckCost; // cout total pour camion
    private long machineCost;

    /*
     * CONSTRUCTEUR
     */
    public Solution(Instance instance) {
        this.coutTotal = 0;
        this.truckCost = 0;
        this.techCost = instance.getTechnicianCost();
        this.techDayCost = instance.getTechnicianDayCost();
        this.techDistanceCost = instance.getTechnicianDistanceCost();
        this.truckCost = instance.getTruckCost();
        this.truckDayCost = instance.getTruckDayCost();
        this.truckDistanceCost = instance.getTruckDistanceCost();
        this.totaltechCost = 0;
        this.truckDistance = 0;
        this.numberTruckDays = 0;
        this.numberTrucksUsed = 0;
        this.technicianDistance = 0;
        this.numberTechnicianDays = 0;
        this.numberTechniciansUsed = 0;
        this.machineCost = 0;
        this.instance = instance;
        this.listeTournees = new HashMap<Integer, LinkedList<Tournee>>();

        for (int i = 0; i < instance.getDays(); i++)
            listeTournees.put(i, new LinkedList<>());

        for ( Tech t : instance.getTechs())
            t.initDays(Solution.this.instance.getDays());
    }

    public Solution(Solution sol) {
        coutTotal = sol.coutTotal;
        instance = sol.instance;
        this.techCost = sol.techCost;
        this.truckCost = sol.truckCost;
        this.totaltechCost = sol.totaltechCost;
        this.truckDistance = sol.truckDistance;
        this.numberTruckDays = sol.numberTruckDays;
        this.numberTrucksUsed = sol.numberTrucksUsed;
        this.technicianDistance = sol.technicianDistance;
        this.numberTechnicianDays = sol.numberTechnicianDays;
        this.numberTechniciansUsed = sol.numberTechniciansUsed;
        this.machineCost = sol.machineCost;
        this.listeTournees = new HashMap<Integer, LinkedList<Tournee>>();

        for (int i = 0; i < sol.listeTournees.size(); i++){
            LinkedList<Tournee> liste = new LinkedList<Tournee>();
            LinkedList<Tournee> copie = new LinkedList<Tournee>();
            liste = sol.listeTournees.get(i);
            for(Tournee tournee : liste){
                //copie.add(new Tournee(tournee));
            }
            listeTournees.put(i, copie);
        }
        /*for (int i = 0; i < sol.listeTournees.size(); i++)
            listeTournees.add(new Tournee(sol.listeTournees.get(i)));*/
    }

    /*
     * METHODES
     */
    public long getCoutTotal() {
        return coutTotal;
    }

    public long getTruckDistance(){
        return this.truckDistance;
    }

    public int getNumberTruckDays(){
        return this.numberTruckDays;
    }

    public int getNumberTrucksUsed(){
        return this.numberTrucksUsed;
    }

    public long getTechnicianDistance(){
        return this.technicianDistance;
    }

    public int getNumberTechnicianDays(){
        return this.numberTechnicianDays;
    }

    public int getNumberTechniciansUsed(){
        return this.numberTechniciansUsed;
    }

    public long getMachineCost(){
        return this.machineCost;
    }

    public Instance getInstance(){
        return this.instance;
    }

    public Map<Integer,LinkedList<Tournee>> getListeTournees(){
        return this.listeTournees;
    }

    /**
     * Fonction qui créer une nouvelle tournéeTruck et y ajoute un client
     * @param r la request à ajouter à la tournée
     */
    public boolean ajouterClientNouvelleTourneeTruck(Request r, int jour) {
        if (r == null) return false;
        Tournee t = new TourneeTruck(instance, jour);
        if(!t.ajouterRequest(r)) return false;

        if (listeTournees.get(jour) == null)
            listeTournees.put(jour, new LinkedList<>());

        listeTournees.get(jour).add(t);
        truckDistance += t.getCoutTotal();
        int cout = t.getCoutTotal()*truckDistanceCost + truckDayCost;
        totaltruckCost += cout;
        coutTotal += cout;
        int used = 0;
        for(Tournee tournee : listeTournees.get(t.jour)){
            if(tournee instanceof TourneeTruck)
                used++;
        }
        if(used > numberTrucksUsed){
            numberTrucksUsed = used;
            totaltruckCost += truckCost;
            coutTotal += truckCost;
        }
        numberTruckDays++;
        return true;
    }
    /**
     * Fonction qui créer une nouvelle tournéeTech et y ajoute un client
     * @param r la request à ajouter à la tournée
     */
    public boolean ajouterClientNouvelleTourneeTech(Request r, int jour) {
        if (r == null) return false;

        Tech current = techFetcher(r, jour);

        if(current==null) return false;

        Tournee t = new TourneeTech(instance, jour, current);
        boolean test = current.isUsed();

        if(!t.ajouterRequest(r)) return false;

        if (listeTournees.get(jour) == null)
            listeTournees.put(jour, new LinkedList<>());

        current.ajouterRequest(r,jour,t);
        listeTournees.get(jour).add(t);

        int machine = calculerCostRetard(r,jour);
        machineCost += machine;
        coutTotal += machine;
        int distance = t.getCoutTotal();
        technicianDistance += distance;
        int cout = distance*techDistanceCost + techDayCost;
        totaltechCost += cout;
        coutTotal += cout;
        numberTechnicianDays++;

        if (!test){
            numberTechniciansUsed++;
            totaltechCost += techCost;
            coutTotal += techCost;
        }
        return true;
    }

    public int calculerCostRetard(Request r, int jour){
        if(r.getJourLivraison()+1 == jour){
            return 0;
        }
        return r.getNbMachine() * instance.getMapMachines().get(r.getIdMachine()).getPenalityCost() *
                (jour - (r.getJourLivraison()+1));
    }

    public Tech techFetcher(Request r, int j){
        if (r == null) return null;
        Tech current = null;

        int i=0;
        while(current==null && i < instance.getTechs().size()){
            Tech t = instance.getTechs().get(i);
            if(t.isDisponible(r,j,null)){
                current = t;
            }
            i++;
        }
        return current;
    }

    /**
     * Fonction qui ajoute un client à une tournée
     * @param r le client à ajouter
     * @param t la tournée
     * @return true si l'ajout a été fait et false sinon
     */
    public boolean ajouterClientTourneeTruck(Request r, Tournee t) {
        if (r == null || listeTournees.isEmpty()) return false;
        if (listeTournees.get(t.jour) == null)
            listeTournees.put(t.jour, new LinkedList<>());

        int cout = t.calculCoutAjoutRequest(r);
        if(!t.ajouterRequest(r)) return false;
        truckDistance += cout;
        totaltruckCost += cout * truckDistanceCost;
        coutTotal += cout * truckDistanceCost;

        return true;
    }

    public boolean ajouterClientDerniereTourneeTruck(Request r) {
        if (r == null || listeTournees.isEmpty()) return false;

        LinkedList<Tournee> tournees = listeTournees.get(r.getFirstDay());
        if (tournees.isEmpty()) return false;

        // Normalement vu qu'on fait les trucks et les techs à part ça devrait pas poser de pb
        Tournee t = tournees.getLast();

        return ajouterClientTourneeTruck(r, t);
    }

    public boolean ajouterClientTourneeTech(Request r, TourneeTech t) {
        int distance = t.calculCoutAjoutRequest(r);
        if (r == null) return false;

        if(!t.ajouterRequest(r)) return false;

        t.getTechnician().ajouterRequest(r, t.jour,t);


        int machine = calculerCostRetard(r,t.jour);
        machineCost += machine;
        coutTotal += machine;

        technicianDistance += distance;
        t.check();
        int cout = distance*techDistanceCost;
        totaltechCost += cout;
        coutTotal += cout;

        return true;
    }

    /*/**
     * Fonction qui renvoie la meilleure insertion possible du client c dans cette solution
     * @param client le client à insérer dans la tournée
     * @return le meilleur opérateur insérant le client c
     */
    /*public Operateur getMeilleureInsertion(Client client) {
        InsertionClient meilleur = new InsertionClient();

        if (client != null)
            for (int i = 0; i < listeTournees.size(); i++){
                InsertionClient toTest = (InsertionClient) listeTournees.get(i).getMeilleureInsertion(client);
                if (toTest.isMeilleur(meilleur))
                    meilleur = toTest;
            }

        return meilleur;
    }*/

    /*/**
     * Fonction qui renvoie la meilleure fusion de tournées possible
     * @return le meilleur opérateur fusionnant deux tournées
     */
    /*public Operateur getMeilleureFusion() {
        FusionTournee meilleur = new FusionTournee();

        for (Tournee tournee : listeTournees) {
            for (Tournee aFusionner : listeTournees) {
                FusionTournee toTest = new FusionTournee(tournee, aFusionner);
                if (toTest.isMeilleur(meilleur))
                    meilleur = toTest;
            }
        }

        return meilleur;
    }*/

    /*/**
     * Fonction qui renvoie le meilleur opérateur intra solution
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    /*private OperateurIntraTournee getMeilleurOperateurIntra(TypeOperateurLocal type) {
        OperateurIntraTournee meilleur = (OperateurIntraTournee) OperateurLocal.getOperateur(type);

        for (Tournee tournee : listeTournees) {
            OperateurIntraTournee toTest = tournee.getMeilleurOperateurIntra(type);
            if (toTest.isMeilleur(meilleur))
                meilleur = toTest;
        }

        return meilleur;
    }*/

    /*/**
     * Fonction qui renvoie le meilleur opérateur inter solution
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    /*private OperateurInterTournee getMeilleurOperateurInter(TypeOperateurLocal type) {
        OperateurInterTournee meilleur = (OperateurInterTournee) OperateurLocal.getOperateur(type);

        for (int i = 0; i < listeTournees.size(); i++) {
            for (int j = i; j < listeTournees.size(); j++) {
                if (i == j) continue;
                Tournee tournee = listeTournees.get(i); Tournee autreTournee = listeTournees.get(j);
                OperateurInterTournee toTest = tournee.getMeilleurOperateurInter(autreTournee, type);
                if (toTest.isMeilleur(meilleur))
                    meilleur = toTest;
            }
        }

        return meilleur;
    }*/

    /*/**
     * Fonction qui renvoie le meilleur opérateur local pour la solution
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    /*public OperateurLocal getMeilleurOperateurLocal(TypeOperateurLocal type) {
        OperateurLocal meilleur = OperateurLocal.getOperateur(type);

        for (Tournee tournee : listeTournees) {
            if (meilleur instanceof OperateurIntraTournee) {
                meilleur = getMeilleurOperateurIntra(type);
            }
            else {
                meilleur = getMeilleurOperateurInter(type);
            }
        }

        return meilleur;
    }*/

    /*/**
     * Fonction qui effectue le mouvement associé à un opérateur local
     * @param infos l'opérateur local
     * @return true si le mouvement a été réalisé et false sinon
     */
    /*public boolean doMouvementRechercheLocale(OperateurLocal infos) {
        if (infos == null) return false;

        if (infos.isMouvementRealisable())
            coutTotal += infos.getDeltaCout();

        return infos.doMovementIfRealisable();
    }*/

    /*/**
     * Fonction qui implémente le mouvement lié à l'opérateur d'insertion
     * @param infos l'opérateur d'insertion
     * @return true si l'insertion a été implémentée et false sinon
     */
    /*public boolean doInsertion(InsertionClient infos) {
        if (infos == null || !infos.doMovementIfRealisable()) return false;

        coutTotal += infos.getDeltaCout();

        return true;
    }*/

    /**
     * Fonction qui permet de tester si la solution est réalisable, une solution est réalisable si toutes les
     * tournées la composant sont réalisables
     * @return true si la solution est réalisable et false sinon
     */
    public boolean check() {
        return checkTourneesRealisables() && checkCoutTotal() && checkRequestUnique();
    }

    /**
     * Fonction qui permet de tester si toutes les tournées de la solution sont réalisables
     * @return true si toutes les tournées sont réalisables et false sinon
     */
    private boolean checkTourneesRealisables() {
        for (int i = 0; i <= listeTournees.size(); i++){
            LinkedList<Tournee> liste = listeTournees.get(i);
            for (Tournee t : liste) {
                if (!t.check()) {
                    System.out.println("Erreur Test checkTourneesRealisables:\n\tErreur dans la tournée n°" + i);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fonction qui test si le deltaCout total théorique correspond au deltaCout total en itérant sur toutes les tournées
     * @return true si le deltaCout théorique correspond au deltaCout effectif et false sinon
     */
    private boolean checkCoutTotal() {
        int cTotal = 0;
        for(int i = 1; i <= listeTournees.size(); i++){
            LinkedList<Tournee> liste = listeTournees.get(i);
            for (Tournee t : liste) {
                cTotal += t.getCoutTotal();
            }
        }
        boolean test = cTotal == coutTotal;
        if (!test)
            System.out.println("Erreur Test checkCoutTotal:\n\tdeltaCout total théorique: " + cTotal +
                    "\n\tdeltaCout total effective: " + coutTotal);
        return test;
    }

    private boolean checkRequestUnique() {
        LinkedList<Request> listRequest = new LinkedList<Request>();

        for(int i = 1; i <= listeTournees.size(); i++){
            LinkedList<Tournee> liste = listeTournees.get(i);
            for (Tournee t : liste) {
                listRequest.addAll(t.getListRequest());
            }
        }

        for (Request r : instance.getRequests()) {
            if (Collections.frequency(listRequest, r) != 1) {
                System.out.println("Erreur Test CheckRequéteUnique :\n\t Requete ingoré ou présent dans plus d'une tournée!");
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return coutTotal == solution.coutTotal && Objects.equals(instance, solution.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coutTotal, instance);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "coutTotal=" + coutTotal +
                ", instance=" + instance +
                ", listeTournees=" + listeTournees +
                '}';
    }
}
