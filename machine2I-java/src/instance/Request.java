package instance;

import network.Client;

public class Request {

    private int id;
    private Client client;
    private int firstDay;
    private int lastDay;
    private int idMachine;
    private int nbMachine;

    public Request(int id, Client client, int firstDay, int lastDay, int idMachine, int nbMachine) {
        this.id = id;
        this.client = client;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.idMachine = idMachine;
        this.nbMachine = nbMachine;
    }

    public int getNbMachine(){
        return this.nbMachine;
    }

    public Client getClient() { return this.client; }

    public int getId() { return this.id; }

    public int getIdMachine() { return this.idMachine; }
}
