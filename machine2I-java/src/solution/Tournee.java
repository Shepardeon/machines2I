package solution;

import instance.Instance;
import network.Client;
import network.Depot;
import network.Point;

import java.util.Collections;
import java.util.LinkedList;


public class Tournee {
    /*
     * PARAMETRE
     */
    private final int capacite;
    private int demandeTotale;
    private int coutTotal;
    private final Depot depot;
    private final LinkedList<Client> listClient;

    /*
     * CONSTRUCTEUR
     */
    public Tournee(Instance instance) {
        this.capacite = instance.getCapacite();
        this.demandeTotale = 0;
        this.coutTotal = 0;
        this.depot = instance.getDepot();
        this.listClient = new LinkedList<>();
    }

    public Tournee(Tournee t) {
        capacite = t.capacite;
        demandeTotale = t.demandeTotale;
        coutTotal = t.coutTotal;
        depot = t.depot;
        listClient = new LinkedList<>(t.listClient);
    }

    /*
     * METHODES
     */
    public int getCapacite() {
        return capacite;
    }

    public int getDemandeTotale() {
        return demandeTotale;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public LinkedList<Client> getListClient() {
        return listClient;
    }

    public boolean isEmpty() {
        return listClient.isEmpty();
    }

    /**
     * Fonction qui renvoie le client à la position pos de la liste de clients
     * @param pos la position du client à récupérer
     * @return le client à la position pos ou null
     */
    public Client getClientAt(int pos) {
        if (pos < 0 || pos >= listClient.size()) return null;
        return listClient.get(pos);
    }

    /**
     * Fonction qui ajoute un client à la tournée
     * @param client le client à ajouter à la tournée
     * @return true si l'ajout a pu se faire et false sinon
     */
    public boolean ajouterClient(Client client) {
        if (canInsererClient(client)){
            demandeTotale += client.getDemande();
            calculerCoutTotal(client);
            return listClient.add(client);
        }
        return false;
    }

    /**
     * Fonction testant la possibilité d'insérer un client
     * @param client le client à insérer
     * @return true si le client peut être inséré et false sinon
     */
    private boolean canInsererClient(Client client) {
        if (client == null) return false;
        return demandeTotale + client.getDemande() <= capacite && !listClient.contains(client);
    }

    /**
     * Fonction textant la possibilité de fusionner deux tournées
     * @param tournee la tournée à fusionner
     * @return true si la fusion est possible et false sinon
     */
    private boolean canFusionnerTournee(Tournee tournee) {
        if (tournee == null || tournee.isEmpty() || this.isEmpty()) return false;
        return tournee != this && this.demandeTotale + tournee.demandeTotale < capacite;
    }

    /**
     * Fonction qui calcule en O(1) le deltaCout total de la tournée à partir du dernier client ajouté
     * @param c le dernier client ajouté à la liste
     */
    private void calculerCoutTotal(Client c) {
        coutTotal += deltaCoutInsertion(listClient.size(), c);
    }

    /**
     * Fonction qui renvoie le point de la tournée qui précède la position pos
     * @param pos la position à interroger
     * @return le point en position pos-1, si pos <= 0 il s'agit du dépot
     */
    private Point getPrec(int pos) {
        if (pos < 0 || pos > listClient.size()) return null;
        if (pos == 0) return depot;
        return listClient.get(pos-1);
    }

    /**
     * Fonction qui renvoie le point de la tournée qui est en position pos
     * @param pos la position à interroger
     * @return le point en position pos, si pos >= taille listeClients ils s'agit du dépot
     */
    private Point getCurrent(int pos) {
        if (pos < 0 || pos > listClient.size()) return null;
        if (pos == listClient.size()) return depot;
        return listClient.get(pos);
    }

    /**
     * Fonction qui renvoie le point suivant la position pos
     * @param pos la position actuelle
     * @return le point en position pos+1 si pos est la position du dernier client alors pos+1 est le dépôt
     */
    private Point getNext(int pos) {
        if (pos+1 <= 0 || pos+1 > listClient.size()) return null;
        if (pos+1 == listClient.size()) return depot;
        return listClient.get(pos+1);
    }

    /**
     * Fonction qui vérifie si une position est valide
     * @param pos la position à tester
     * @return true si la position est valide et false sinon
     */
    private boolean isPositionvalide(int pos) {
        return pos >= 0 && pos < listClient.size();
    }

    /**
     * Fonction qui teste si une position est valide pour une insertion
     * @param pos la position où insérer
     * @return true si la position est possible et false sinon
     */
    private boolean isPositionInsertionValide(int pos) {
        return getPrec(pos) != null && getCurrent(pos) != null;
    }

    /**
     * Fonction qui calcule en O(1) le deltaCout total de la tournée à partir du dernier client ajouté en position pos
     * @param pos la position d'insertion du client
     * @param client le client à insérer
     * @return le deltaCout de l'insertion du client à la position donnée (= infini si la position est invalide)
     */
    public int deltaCoutInsertion(int pos, Client client) {
        if (!isPositionInsertionValide(pos)) return Integer.MAX_VALUE;

        if (listClient.isEmpty())
            return 2 * client.getCoutVers(depot);
        else {
            Point prec = getPrec(pos);
            Point curr = getCurrent(pos);

            if (prec == null || curr == null) return Integer.MAX_VALUE;

            return prec.getCoutVers(client) + client.getCoutVers(curr) - prec.getCoutVers(curr);
        }
    }

    public int deltaCoutInsertionInter(int pos, Client client) {
        if (!canInsererClient(client)) return Integer.MAX_VALUE;
        return deltaCoutInsertion(pos, client);
    }

    /**
     * Fonction qui calcule le deltaCout pour fusionner une tournée avec cette tournée
     * @param aFusionner la tournée à fusionner avec cette instance
     * @return le deltaCout engendré par la fusion
     */
    public int deltaCoutFusion(Tournee aFusionner) {
        if (aFusionner == null || listClient.isEmpty() || aFusionner.listClient.isEmpty()) return Integer.MAX_VALUE;

        Client dernier = listClient.getLast();
        Client premier = aFusionner.listClient.getLast();

        return dernier.getCoutVers(premier) - dernier.getCoutVers(depot) - depot.getCoutVers(premier);
    }

    /**
     * Fonction qui calcule le deltaCout pour supprimer le client en position pos de cette tournée
     * @param pos la position du client à retirer
     * @return le deltaCout engendré par la suppression du client de la tournée
     */
    public int deltaCoutSupression(int pos) {
        if (!isPositionvalide(pos)) return Integer.MAX_VALUE;
        if (listClient.size() == 1) return -getCoutTotal();

        Point prec = getPrec(pos);
        Point curr = getCurrent(pos);
        Point next = getNext(pos);

        if (prec == null || curr == null || next == null) return Integer.MAX_VALUE;

        return prec.getCoutVers(next) - prec.getCoutVers(curr) - curr.getCoutVers(next);
    }

    /**
     * Fonction qui calcule le deltaCout pour déplacer le client d'une position I à une position J
     * @param posI la position de départ du client
     * @param posJ la position d'insertion du client
     * @return le deltaCout engendré par le déplacement du client
     */
    public int deltaCoutDeplacement(int posI, int posJ) {
        if (!doublePositionValide(posI, posJ)) return Integer.MAX_VALUE;
        return deltaCoutSupression(posI) + deltaCoutInsertion(posJ, getClientAt(posI));
    }

    private boolean doublePositionValide(int pI, int pJ) {
        return isPositionvalide(pI) && isPositionvalide(pJ) && pI != pJ && Math.abs(pI - pJ) != 1;
    }

    /**
     * Fonction qui calcule le deltaCout pour échanger les clients en position I et J
     * @param posI la position du client I
     * @param posJ la position du client J
     * @return le deltaCout engendré par l'échange
     */
    public int deltaCoutEchange(int posI, int posJ) {
        if (!isPositionvalide(posI) || !isPositionvalide(posJ)) return Integer.MAX_VALUE;
        if (posI == posJ) return 0;
        if (posJ < posI) return deltaCoutEchange(posJ, posI);
        if (posI+1 == posJ) return deltaCoutEchangeConsecutif(posI);
        return deltaCoutRemplacement(posI, posJ);
    }

    private int deltaCoutEchangeConsecutif(int pos) {
        Client i = listClient.get(pos);
        Client j = listClient.get(pos+1);
        Point prec = getPrec(pos);
        Point next = getNext(pos+1);

        if (prec == null || next == null) return Integer.MAX_VALUE;

        return prec.getCoutVers(j) + i.getCoutVers(next) - (prec.getCoutVers(i) + j.getCoutVers(next));
    }

    private int deltaCoutRemplacement(int posI, int posJ) {
        Client i = listClient.get(posI);
        Point precI = getPrec(posI);
        Point nextI = getNext(posI);

        Client j = listClient.get(posJ);
        Point precJ = getPrec(posJ);
        Point nextJ = getNext(posJ);

        if (precI == null || nextI == null || precJ == null || nextJ == null) return Integer.MAX_VALUE;

        return precI.getCoutVers(j) + j.getCoutVers(nextI) + precJ.getCoutVers(i) + i.getCoutVers(nextJ)
                - (precI.getCoutVers(i) + i.getCoutVers(nextI) + precJ.getCoutVers(j) + nextJ.getCoutVers(j));
    }

    /**
     * Fonction qui calcule le cout de remplacement du client à la position pos par le client c
     * @param pos la position du client à remplacer
     * @param c le client qui remplace
     * @return le delatCout engendré
     */
    public int deltaCoutRemplacement(int pos, Client c) {
        if (!isPositionvalide(pos) || c == null) return Integer.MAX_VALUE;

        Client i = listClient.get(pos);
        Point prec = getPrec(pos);
        Point next = getNext(pos);

        if (prec == null || next == null) return Integer.MAX_VALUE;
        if (demandeTotale + c.getDemande() - i.getDemande() > capacite) return Integer.MAX_VALUE;

        return prec.getCoutVers(c) + c.getCoutVers(next) - (prec.getCoutVers(i) + i.getCoutVers(next));
    }

    /*/**
     * Fonction qui renvoie la meilleure insertion possible du client dans cette tournée
     * @param client le client à insérer dans la tournée
     * @return le meilleur opérateur insérant le client c
     */
    /*public Operateur getMeilleureInsertion(Client client) {
        InsertionClient meilleur = new InsertionClient();

        if (canInsererClient(client))
            for (int i = 0; i < listClient.size(); i++){
                InsertionClient toTest = new InsertionClient(i, client, this);
                if (toTest.isMeilleur(meilleur))
                    meilleur = toTest;
            }

        return meilleur;
    }*/

    /*/**
     * Fonction qui implémente le mouvement lié à l'opérateur d'insertion
     * @param infos l'opérateur d'insertion
     * @return true si l'insertion a été implémentée et false sinon
     */
    /*public boolean doInsertion(InsertionClient infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;

        listClient.add(infos.getPositionInsertion(), infos.getClient());
        coutTotal += infos.getDeltaCout();
        demandeTotale += infos.getClient().getDemande();

        return true;
    }*/

    /*/**
     * Fonction qui implémente le mouvement liée à l'opérateur IntraDéplacement
     * @param infos l'opérateur de déplacement
     * @return true si le déplacement a eu lieu et false sinon
     */
    /*public boolean doDeplacement(IntraDeplacement infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        if (!doublePositionValide(i, j)) return false;

        Client c = infos.getClientI();

        listClient.remove(i);
        if (i < j)
            j--;
        listClient.add(j, c);

        coutTotal += infos.getDeltaCout();

        return true;
    }*/

    /*/**
     * Fonction qui implémente le mouvement liée à l'opérateur InterDéplacement
     * @param infos l'opérateur de déplacement
     * @return true si le déplacement a eu lieu et false sinon
     */
    /*public boolean doDeplacement(InterDeplacement infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        Tournee autre = infos.getAutreTournee();
        if (!this.isPositionvalide(i) || !autre.isPositionvalide(j)) return false;

        Client c = infos.getClientI();

        this.listClient.remove(i);
        autre.listClient.add(j, c);

        this.coutTotal += infos.getDeltaCoutTournee();
        autre.coutTotal += infos.getDeltaCoutAutreTournee();

        this.demandeTotale -= c.getDemande();
        autre.demandeTotale += c.getDemande();

        return true;
    }*/

    /*/**
     * Fonction qui implémente le mouvement liée à l'opérateur d'échange
     * @param infos l'opérateur d'échange
     * @return true si le mouvement a eu lieu et false sinon
     */
    /*public boolean doEchange(IntraEchange infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        if (!isPositionvalide(i) || !isPositionvalide(j)) return false;

        Collections.swap(listClient, i, j);
        coutTotal += infos.getDeltaCout();

        return true;
    }*/

    /*/**
     * Fonction qui implémente le mouvement liée à l'opérateur d'échange
     * @param infos l'opérateur d'échange
     * @return true si le mouvement a eu lieu et false sinon
     */
    /*public boolean doEchange(InterEchange infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        Tournee autre = infos.getAutreTournee();
        if (!this.isPositionvalide(i) || !autre.isPositionvalide(j)) return false;

        Client cI = infos.getClientI(); Client cJ = infos.getClientJ();

        this.listClient.add(i, cJ);
        this.listClient.remove(cI);
        autre.listClient.add(j, cI);
        autre.listClient.remove(cJ);

        this.coutTotal += infos.getDeltaCoutTournee();
        autre.coutTotal += infos.getDeltaCoutAutreTournee();

        this.demandeTotale += cJ.getDemande() - cI.getDemande();
        autre.demandeTotale += cI.getDemande() - cJ.getDemande();

        return true;
    }*/

    /*/**
     * Fonction qui renvoie le meilleur opérateur intra tournée
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    /*public OperateurIntraTournee getMeilleurOperateurIntra(TypeOperateurLocal type) {
        OperateurIntraTournee meilleur = (OperateurIntraTournee) OperateurLocal.getOperateur(type);

        for (int i = 0; i < listClient.size(); i++) {
            for (int j = 0; j < listClient.size(); j++) {
                if (i == j) continue;
                OperateurIntraTournee op = (OperateurIntraTournee) OperateurLocal.getOperateurIntra(type, this, i, j);
                if (op.isMeilleur(meilleur) && !ListeTabou.getInstance().isTabou(op))
                    meilleur = op;
            }
        }

        return meilleur;
    }*/

    /*/**
     * Fonction qui renvoie le meilleur opérateur inter tournée
     * @param autreTournee l'autre tournée
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    /*public OperateurInterTournee getMeilleurOperateurInter(Tournee autreTournee, TypeOperateurLocal type) {
        OperateurInterTournee meilleur = (OperateurInterTournee) OperateurLocal.getOperateur(type);

        for (int i = 0; i < listClient.size(); i++) {
            for (int j = 0; j < autreTournee.listClient.size(); j++) {
                OperateurInterTournee op = (OperateurInterTournee) OperateurLocal.getOperateurInter(type, this, autreTournee, i, j);
                if (op.isMeilleur(meilleur) && !ListeTabou.getInstance().isTabou(op))
                    meilleur = op;
            }
        }

        return meilleur;
    }*/

    /**
     * Fonction qui permet de tester si une tournée est réalisable ou non
     * @return true si la tournée est réalisable et false sinon
     */
    public boolean check() {
        return checkCalculsDemandeCoutTotal() && checkDemandeCapacite();
    }

    /**
     * Fonction qui vérifie que la demande totale et le coût total sont corrects
     * @return true si tout est correct et false sinon
     */
    private boolean checkCalculsDemandeCoutTotal() {
        return checkCalculerDemandeTotale() && checkCalculerCoutTotal();
    }

    /**
     * Fonction qui calcule la demande totale en itérant sur tous les clients et vérifie si elle est conorme à la
     * demande totale de la tournée
     * @return true si la demande totale théorique correspond à la demande totale effective
     */
    private boolean checkCalculerDemandeTotale() {
        int dTotale = 0;
        for (Client c : listClient) {
            dTotale += c.getDemande();
        }

        boolean test = dTotale == demandeTotale;
        if (!test)
            System.out.println("Erreur Test checkCalculerDemandeTotale:\n\tdemande totale théorique: " + dTotale +
                    "\n\tdemande effective: " + demandeTotale);
        return test;
    }

    /**
     * Fonction qui calcule le coût total en itérant sur tous les clients et vérifie s'il correspond au deltaCout total de la
     * tournée
     * @return true si le coût total théorique correspond au coût total effectif
     */
    private boolean checkCalculerCoutTotal() {
        int cTotal = 0;
        for (int i = 0; i < listClient.size() - 1; i++) {
            // On calcul les liens entre tous les clients
            cTotal += listClient.get(i).getCoutVers(listClient.get(i+1));
        }
        // On ajoute les liens entre le premier et dernier points au dépôt
        cTotal += depot.getCoutVers(listClient.getFirst()) + listClient.getLast().getCoutVers(depot);

        boolean test = cTotal == coutTotal;
        if (!test)
            System.out.println("Erreur Test checkCalculerCoutTotal:\n\tcoût total théorique: " + cTotal +
                    "\n\tcoût effectif: " + coutTotal);

        return test;
    }

    /**
     * Fonction qui test si la demande est inférieur ou égale à la capacité
     * @return true si la demande est bien inférieur ou égale à la capacité et false sinon
     */
    private boolean checkDemandeCapacite() {
        boolean test = demandeTotale <= capacite;
        if (!test)
            System.out.println("Erreur Test checkDemandeCapacite:\n\tdemande totale(=" + demandeTotale
                    + ") n'est pas inférieur ou égale à la capacité(=" + capacite + ")");
        return test;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "capacite=" + capacite +
                ", demandeTotale=" + demandeTotale +
                ", coutTotal=" + coutTotal +
                ", listClient=" + listClient +
                '}';
    }
}
