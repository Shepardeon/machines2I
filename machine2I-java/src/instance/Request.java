package instance;

public class Request {

    private int id;
    private int idClient;
    private int firstDay;
    private int lastDay;
    private int idMachine;
    private int nbMachine;

    public Request(int id, int idClient, int firstDay, int lastDay, int idMachine, int nbMachine) {
        this.id = id;
        this.idClient = idClient;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.idMachine = idMachine;
        this.nbMachine = nbMachine;
    }

    public int getNbMachine(){
        return this.nbMachine;
    }
}
