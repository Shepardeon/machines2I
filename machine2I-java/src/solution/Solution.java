package solution;

import instance.Instance;
import network.Client;

import java.io.ObjectInputStream.GetField;
import java.util.*;

public class Solution {
    /*
     * PARAMETRES
     */
    private int coutTotal;
    private final Instance instance;
    private final Map<Integer,LinkedList<Tournee>> listeTournees;
    private int truckDistance;
    private int numberTruckDays;
    private int numberTrucksUsed;
    private int truckCost;
    private int technicianDistance;
    private int numberTechnicianDays;
    private int numberTechniciansUsed;
    private int technicianCost;
    private int machineCost;
    

    /*
     * CONSTRUCTEUR
     */
    public Solution(Instance instance) {
        this.coutTotal = 0;
        this.truckCost = 0;
        this.technicianCost = 0;
        this.truckDistance = 0;
        this.numberTruckDays = 0;
        this.numberTrucksUsed = 0;
        this.technicianDistance = 0;
        this.numberTechnicianDays = 0;
        this.numberTechniciansUsed = 0;
        this.machineCost = 0;
        this.instance = instance;
        this.listeTournees = new HashMap<Integer, LinkedList<Tournee>>();
    }

    public Solution(Solution sol) {
        coutTotal = sol.coutTotal;
        instance = sol.instance;
        this.coutTotal = sol.coutTotal;
        this.truckCost = sol.truckCost;
        this.technicianCost = sol.technicianCost;
        this.truckDistance = sol.truckDistance;
        this.numberTruckDays = sol.numberTruckDays;
        this.numberTrucksUsed = sol.numberTrucksUsed;
        this.technicianDistance = sol.technicianDistance;
        this.numberTechnicianDays = sol.numberTechnicianDays;
        this.numberTechniciansUsed = sol.numberTechniciansUsed;
        this.machineCost = sol.machineCost;
        
        listeTournees= new HashMap<Integer, LinkedList<Tournee>>();

        for (int i = 0; i < sol.listeTournees.size(); i++){
        	LinkedList<Tournee> liste = new LinkedList<Tournee>();
        	LinkedList<Tournee> copie = new LinkedList<Tournee>();
        	liste = sol.listeTournees.get(i);
        	for(Tournee tournee : liste){
        		copie.add(new Tournee(tournee));
        	}
        	listeTournees.put(i, copie);
        }
    }

    /*
     * METHODES
     */
    public int getCoutTotal() {
        return coutTotal;
    }

    public Instance getInstance(){
    	return instance;
    }
    
    public int getTruckDistance() {
		return truckDistance;
	}

	public int getNumberTruckDays() {
		return numberTruckDays;
	}

	public int getNumberTrucksUsed() {
		return numberTrucksUsed;
	}

	public int getTruckCost() {
		return truckCost;
	}

	public int getTechnicianDistance() {
		return technicianDistance;
	}

	public int getNumberTechnicianDays() {
		return numberTechnicianDays;
	}

	public int getNumberTechniciansUsed() {
		return numberTechniciansUsed;
	}

	public int getTechnicianCost() {
		return technicianCost;
	}
	
	public int getMachineCost() {
		return machineCost;
	}

	public Map<Integer, LinkedList<Tournee>> getListeTournees() {
		return listeTournees;
	}

	/**
     * Fonction qui créer une nouvelle tournée et y ajoute un client
     * @param c le client à ajouter à la tournée
     */
    public void ajouterClientNouvelleTournee(Client c,int jour) {
        if (c == null) return;
        Tournee t = new Tournee(instance);
        t.ajouterClient(c);
        listeTournees.get(jour).add(t);
        coutTotal += t.getCoutTotal();
    }*/

    /**
     * Fonction qui ajoute un client à une tournée existante dans la liste des tournées
     * @param c le client à ajouter
     * @return true si l'ajout a été fait et false sinon
     */
    public boolean ajouterClient(Client c,int jour) {
        if (c == null) return false;
        for (Tournee t : listeTournees.get(jour)) {
            if (addClient(c, t)) return true;
        }
        return false;
    }

    /**
     * Fonction qui ajoute un client à la dernière tournée de la liste des tournées
     * @param c le client à ajouter
     * @return true si l'ajout a été fait et false sinon
     */
    public boolean ajouterClientDerniereTournee(Client c, int jour) {
        if (c == null || listeTournees.isEmpty()) return false;
        Tournee t = listeTournees.get(jour).getLast();
        return addClient(c, t);
    }

    /**
     * Fonction qui ajoute un client à une tournée donnée
     * @param c le client à ajouter
     * @param t la tournée à laquelle ajouter un client
     * @return true si l'ajout a eu lieux et false sinon
     */
    private boolean addClient(Client c, Tournee t) {
        int oldCout = t.getCoutTotal();
        if (t.ajouterClient(c)) {
            coutTotal += t.getCoutTotal() - oldCout;
            return true;
        }
        return false;
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
        return checkTourneesRealisables() && checkCoutTotal() && checkClientUnique();
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

    private boolean checkClientUnique() {
        LinkedList<Client> listClient = new LinkedList<>();


        for(int i = 1; i <= listeTournees.size(); i++){
        	LinkedList<Tournee> liste = listeTournees.get(i);
	        for (Tournee t : liste) {
	            listClient.addAll(t.getListClient());
	        }
        }

        for (Client c : instance.getClients()) {
            if (Collections.frequency(listClient, c) != 1) {
                System.out.println("Erreur Test checkClientUnique:\n\tClient ingoré ou présent dans plus d'une tournée!");
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
