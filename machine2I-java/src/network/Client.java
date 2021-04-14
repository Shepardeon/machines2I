package network;

import instance.Request;

public class Client extends Point{
    /**
     * PARAMETRES
     */
    private final Request request;

    /**
     * CONSTRUCTEUR
     */
    public Client(Request request, int id, int x, int y){
        super(id, x, y);
        this.request = request;
    }

    /**
     * METHODES
     */
    public Request getRequest() {
        return request;
    }


    @Override
    public String toString() {
        return "Client{" +
                "point=" + super.toString() +
                ", demande=" + request +
                '}';
    }
}
