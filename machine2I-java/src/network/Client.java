package network;

public class Client extends Point{
    /**
     * PARAMETRES
     */
    private final int demande;

    /**
     * CONSTRUCTEUR
     */
    public Client(int demande, int id, int x, int y){
        super(id, x, y);
        this.request = request;
    }

    /**
     * METHODES
     */
    public int getDemande() {
        return demande;
    }


    @Override
    public String toString() {
        return "Client{" +
                "point=" + super.toString() +
                ", demande=" + request +
                '}';
    }
}
