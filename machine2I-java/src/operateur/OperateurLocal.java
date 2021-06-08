package operateur;

import instance.Request;
import solution.Tournee;

public abstract class OperateurLocal extends Operateur {
    /*
     * PARAMETRES
     */
    protected int positionI, positionJ;
    protected Request requestI, requestJ;

    /*
     * CONSTRUCTEURS
     */
    public OperateurLocal() {
        tournee = null;
        positionI = positionJ = 0;
        requestI = requestJ = null;
    }

    public OperateurLocal(Tournee tournee, int positionI, int positionJ) {
        this.tournee = tournee;
        this.positionI = positionI;
        this.positionJ = positionJ;
        this.requestI = tournee.getRequestAt(positionI);
        this.requestJ = tournee.getRequestAt(positionJ);
    }

    /*
     * METHODES
     */
    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public Request getRequestI() {
        return requestI;
    }

    public Request getRequestJ() {
        return requestJ;
    }

    public static OperateurLocal getOperateur(TypeOperateurLocal type) {
        return switch (type) {
            /*case INTER_ECHANGE -> new InterEchange();
            case INTRA_ECHANGE -> new IntraEchange();
            case INTER_DEPLACEMENT -> new InterDeplacement();
            case INTRA_DEPLACEMENT -> new IntraDeplacement();*/
            default -> null;
        };
    }

    public static OperateurLocal getOperateurIntra(TypeOperateurLocal type, Tournee tournee,
                                                   int positionI, int positionJ) {
        return switch (type) {
            /*case INTRA_DEPLACEMENT -> new IntraDeplacement(tournee, positionI, positionJ);
            case INTRA_ECHANGE -> new IntraEchange(tournee, positionI, positionJ);*/
            default -> null;
        };
    }

    public static OperateurLocal getOperateurInter(TypeOperateurLocal type, Tournee tournee, Tournee autreTournee,
                                                   int positionI, int positionJ) {
        return switch (type) {
            /*case INTER_DEPLACEMENT -> new InterDeplacement(tournee, autreTournee, positionI, positionJ);
            case INTER_ECHANGE -> new InterEchange(tournee, autreTournee, positionI, positionJ);*/
            default -> null;
        };
    }

    //public abstract boolean isTabou(OperateurLocal op);

    @Override
    public String toString() {
        return "OperateurLocal{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", clientI=" + requestI +
                ", clientJ=" + requestJ +
                '}';
    }
}
