package operateur;

import solution.Tournee;

public abstract class OperateurInterTournee extends OperateurLocal {
    /*
     * PARAMETRES
     */
    protected Tournee autreTournee;
    protected int deltaCoutTournee, deltaCoutAutreTournee, deltaIDLE, deltaDistance;

    /*
     * CONSTRUCTEURS
     */
    public OperateurInterTournee() {
        super();
        autreTournee = null;
        deltaCoutTournee = deltaCoutAutreTournee = 0;
    }

    public OperateurInterTournee(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        requestJ = autreTournee.getRequestAt(positionJ);
        deltaCout = evalDeltaCout();
    }

    /*
     * METHODES
     */
    public Tournee getAutreTournee() {
        return autreTournee;
    }

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
    }

    public int getDeltaDistance(){ return deltaDistance;}

    /**
     * Calcul le surcout lié à l'application de l'opérateur sur la tournée
     * @return le surcout
     */
    protected abstract int evalDeltaCoutTournee();

    /**
     * Calcul le surcout lié à l'application de l'opérateur sur l'autre tournée
     * @return le surcout
     */
    protected abstract int evalDeltaCoutAutreTournee();


    public abstract int evalDeltaDistanceTournee();

    public abstract int evalDeltaDistanceAutreTournee();


    /**
     * Calcul le surcout lié à l'application de l'opérateur sur l'autre tournée
     * @return le surcout
     */
    protected abstract int evalDeltaIDLE();


    protected abstract int evalDeltaCoutDistance();

    @Override
    protected int evalDeltaCout() {
        if (tournee == null || autreTournee == null)
            return Integer.MAX_VALUE;

        deltaDistance = evalDeltaCoutDistance();
        deltaCoutTournee = evalDeltaCoutTournee();
        deltaCoutAutreTournee = evalDeltaCoutAutreTournee();

        deltaIDLE = evalDeltaIDLE();

        if (deltaCoutTournee == Integer.MAX_VALUE || deltaCoutAutreTournee == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        deltaCout = deltaCoutTournee + deltaCoutAutreTournee + deltaIDLE;

        return deltaCout;
    }

    @Override
    public String toString() {
        return "OperateurInterTournee{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", autreTournee=" + autreTournee +
                ", deltaCoutTournee=" + deltaCoutTournee +
                ", deltaCoutAutreTournee=" + deltaCoutAutreTournee +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", requestI=" + requestI +
                ", requestJ=" + requestJ +
                '}';
    }
}
