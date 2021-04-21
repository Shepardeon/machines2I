package solution;

import instance.Instance;
import instance.Request;
import network.Client;
import network.Depot;
import network.Point;

import java.util.Collections;
import java.util.LinkedList;


public abstract class Tournee {
    /*
     * PARAMETRE
     */
    protected int demandeTotale;
    protected int coutTotal;
    protected Depot depot;
    protected final LinkedList<Request> listRequest;

    /*
     * CONSTRUCTEUR
     */

    public Tournee(Tournee t) {
        demandeTotale = t.demandeTotale;
        coutTotal = t.coutTotal;
        depot = t.depot;
        listRequest = new LinkedList<>(t.listRequest);
    }

    public Tournee() {
        this.demandeTotale = 0;
        this.coutTotal = 0;
        this.listRequest = new LinkedList<Request>();
    }

    public Tournee(Instance instance) {
        this.demandeTotale = 0;
        this.coutTotal = 0;
        this.listRequest = new LinkedList<Request>();
    }
    /*
     * METHODES
     */

    public int getDemandeTotale() {
        return demandeTotale;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public LinkedList<Request> getListRequest() {
        return listRequest;
    }

    public boolean isEmpty() {
        return listRequest.isEmpty();
    }

    public Depot getDepot(){
        return this.depot;
    };

    /**
     * Fonction qui renvoie le client à la position pos de la liste de clients
     * @param pos la position du client à récupérer
     * @return le client à la position pos ou null
     */
    public Request getRequestAt(int pos) {
        if (pos < 0 || pos >= listRequest.size()) return null;
        return listRequest.get(pos);
    }

    /**
     * Fonction qui ajoute un client à la tournée
     * @param request le client à ajouter à la tournée
     * @return true si l'ajout a pu se faire et false sinon
     */
    public abstract boolean ajouterRequest(Request request);

    /**
     * Fonction testant la possibilité d'insérer un client
     * @param request le client à insérer
     * @return true si le client peut être inséré et false sinon
    */
    public abstract boolean canInsererRequest(Request request);

    public abstract boolean check();

    public boolean isPositionvalide(int pos) {
        return pos >= 0 && pos < listRequest.size();
    }

    public abstract boolean checkCalculerDemandeTotale();

    /**
     * Fonction qui calcule le coût total en itérant sur tous les clients et vérifie s'il correspond au deltaCout total de la
     * tournée
     * @return true si le coût total théorique correspond au coût total effectif
     */
    private boolean checkCalculerCoutTotal() {
        int cTotal = 0;
        for (int i = 0; i < listRequest.size() - 1; i++) {
            // On calcul les liens entre tous les clients
            cTotal += listRequest.get(i).getClient().getCoutVers(listRequest.get(i+1).getClient());
        }
        // On ajoute les liens entre le premier et dernier points au dépôt
        cTotal += depot.getCoutVers(listRequest.getFirst().getClient()) + listRequest.getLast().getClient().getCoutVers(depot);

        boolean test = cTotal == coutTotal;
        if (!test)
            System.out.println("Erreur Test checkCalculerCoutTotal:\n\tcoût total théorique: " + cTotal +
                    "\n\tcoût effectif: " + coutTotal);

        return test;
    }
    /**
     * Fonction textant la possibilité de fusionner deux tournées
     * @param tournee la tournée à fusionner
     * @return true si la fusion est possible et false sinon

    private boolean canFusionnerTournee(Tournee tournee) {
        if (tournee == null || tournee.isEmpty() || this.isEmpty()) return false;
        return tournee != this && this.demandeTotale + tournee.demandeTotale < capacite;
    }*/

    /**
     * Fonction qui calcule en O(1) le deltaCout total de la tournée à partir du dernier client ajouté
     * @param c le dernier client ajouté à la liste

    private void calculerCoutTotal(Client c) {
        coutTotal += deltaCoutInsertion(listClient.size(), c);
    }*/

    /**
     * Fonction qui renvoie le point de la tournée qui précède la position pos
     * @param pos la position à interroger
     * @return le point en position pos-1, si pos <= 0 il s'agit du dépot

    private Point getPrec(int pos) {
        if (pos < 0 || pos > listClient.size()) return null;
        if (pos == 0) return depot;
        return listClient.get(pos-1);
    }

    /**
     * Fonction qui renvoie le point de la tournée qui est en position pos
     * @param pos la position à interroger
     * @return le point en position pos, si pos >= taille listeClients ils s'agit du dépot

    private Point getCurrent(int pos) {
        if (pos < 0 || pos > listClient.size()) return null;
        if (pos == listClient.size()) return depot;
        return listClient.get(pos);
    }*/

    /**
     * Fonction qui renvoie le point suivant la position pos
     * @param pos la position actuelle
     * @return le point en position pos+1 si pos est la position du dernier client alors pos+1 est le dépôt

    private Point getNext(int pos) {
        if (pos+1 <= 0 || pos+1 > listClient.size()) return null;
        if (pos+1 == listClient.size()) return depot;
        return listClient.get(pos+1);
    }*/

    /**
     * Fonction qui vérifie si une position est valide
     * @param pos la position à tester
     * @return true si la position est valide et false sinon
     */


    /**
     * Fonction qui teste si une position est valide pour une insertion
     * @param pos la position où insérer
     * @return true si la position est possible et false sinon

    private boolean isPositionInsertionValide(int pos) {
        return getPrec(pos) != null && getCurrent(pos) != null;
    }

    /**
     * Fonction qui calcule en O(1) le deltaCout total de la tournée à partir du dernier client ajouté en position pos
     * @param pos la position d'insertion du client
     * @param client le client à insérer
     * @return le deltaCout de l'insertion du client à la position donnée (= infini si la position est invalide)

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

    public int deltaCoutDeplacement(int posI, int posJ) {
        if (!doublePositionValide(posI, posJ)) return Integer.MAX_VALUE;
        return deltaCoutSupression(posI) + deltaCoutInsertion(posJ, getClientAt(posI));
    }

    private boolean doublePositionValide(int pI, int pJ) {
        return isPositionvalide(pI) && isPositionvalide(pJ) && pI != pJ && Math.abs(pI - pJ) != 1;
    }*/

    /**
     * Fonction qui calcule le deltaCout pour échanger les clients en position I et J
     * @param posI la position du client I
     * @param posJ la position du client J
     * @return le deltaCout engendré par l'échange

    public int deltaCoutEchange(int posI, int posJ) {
        if (!isPositionvalide(posI) || !isPositionvalide(posJ)) return Integer.MAX_VALUE;
        if (posI == posJ) return 0;
        if (posJ < posI) return deltaCoutEchange(posJ, posI);
        if (posI+1 == posJ) return deltaCoutEchangeConsecutif(posI);
        return deltaCoutRemplacement(posI, posJ);
    }*/

    /**
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
    }*/

    /**
     * Fonction qui calcule le cout de remplacement du client à la position pos par le client c
     * @param pos la position du client à remplacer
     * @param c le client qui remplace
     * @return le delatCout engendré
     
    public int deltaCoutRemplacement(int pos, Client c) {
        if (!isPositionvalide(pos) || c == null) return Integer.MAX_VALUE;

        Client i = listClient.get(pos);
        Point prec = getPrec(pos);
        Point next = getNext(pos);

        if (prec == null || next == null) return Integer.MAX_VALUE;
        if (demandeTotale + c.getDemande() - i.getDemande() > capacite) return Integer.MAX_VALUE;

        return prec.getCoutVers(c) + c.getCoutVers(next) - (prec.getCoutVers(i) + i.getCoutVers(next));
    }*/


    /**
     * Fonction qui permet de tester si une tournée est réalisable ou non
     * @return true si la tournée est réalisable et false sinon
    */


    /**
     * Fonction qui vérifie que la demande totale et le coût total sont corrects
     * @return true si tout est correct et false sinon

    private boolean checkCalculsDemandeCoutTotal() {
        return checkCalculerDemandeTotale() && checkCalculerCoutTotal();
    }*/

    /**
     * Fonction qui calcule la demande totale en itérant sur tous les clients et vérifie si elle est conorme à la
     * demande totale de la tournée
     * @return true si la demande totale théorique correspond à la demande totale effective
     */



    @Override
    public String toString() {
        return "Tournee{" +
                ", demandeTotale=" + demandeTotale +
                ", coutTotal=" + coutTotal +
                '}';
    }
}
