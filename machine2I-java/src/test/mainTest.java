package test;

import instance.Instance;
import io.Import;
import io.Export;
import io.exception.ReaderException;
import solution.Solution;
import solveur.Solveur;
import solveur.Trivial;


public class mainTest {
    public static void main(String[] args) {
        try {
            System.out.println("Zbwee zbweee, who's that pokemon?");
            Import reader = new Import("instances/ORTEC-early-easy/VSC2019_ORTEC_early_01_easy.txt");
            Instance i = reader.readInstance();
            System.out.println("Instance lue avec success !");
            System.out.println("Num request = " + i.getRequests().size());
            System.out.println("Num clients = " + i.getNbClients());
            System.out.println("Num tech = " + i.getTechs().size());

            System.out.println(i);

            Solveur solv = new Trivial();

            Solution solu = solv.solve(i);

            System.out.println(solu);

            Export exp = new Export(solu);

            exp.ExporterSolution();
        } catch (
        ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
