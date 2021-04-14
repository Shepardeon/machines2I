package io;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import instance.Request;
import network.Client;
import network.Depot;
import network.Tech;

import java.io.File;

import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;

/** This class allows you to export a solution into a txt file **/
public class Export {
	public Solution solution;
	
	public Export(Solution solution){
		this.solution = solution;
	}
	
	public boolean ExporterSolution(){
		File monFichier = new File("solution.txt");
		try{
			if(monFichier.createNewFile())
				System.out.println("création du fichier solution.txt");
			else
				System.out.println("modification du fichier solution.txt");
			PrintWriter writer = new PrintWriter(monFichier);
			writer.println("DATASET = ");
			writer.println("NAME = ");
			writer.println();
			writer.println("TRUCK_DISTANCE = " + solution.getTruckDistance());
			writer.println("NUMBER_OF_TRUCK_DAYS = " + solution.getNumberTruckDays());
			writer.println("NUMBER_OF_TRUCKS_USED = " + solution.getNumberTrucksUsed());
			writer.println("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance());
			writer.println("NUMBER_OF_TECHNICIAN_DAYS = " + solution.getNumberTechnicianDays());
			writer.println("NUMBER_OF_TECHNICIAN_USED = " + solution.getNumberTechniciansUsed());
			writer.println("IDLE_MACHINE_COSTS = "+solution.getMachineCost());
			writer.println();
			Map<Integer,LinkedList<Tournee>> liste = solution.getListeTournees();
			for (int i = 1; i <= liste.size(); i++) {
				writer.println("DAY = "+i);
				LinkedList<Tournee> tournees = liste.get(i);
				LinkedList<Tournee> truck = new LinkedList<>();
				int nbTruck = 0;
				LinkedList<Tournee> tech = new LinkedList<>();
				int nbTech = 0;
				for(Tournee t : tournees){
					if(t instanceof TourneeTech){
						tech.add(t);
						nbTech ++;
					}else{
						truck.add(t);
						nbTruck ++;
					}
				}
				writer.println("NUMBER_OF_TRUCKS = "+nbTruck);
				int idTruck = 0;
				for(Tournee tournee : tech){
					String chaine = ""+idTruck++ ;
					LinkedList<Request> requests = tournee.getListRequest();
					for(Request request : requests){
						chaine+= " "+request.getId();
					}
					writer.println(chaine);
				}
				writer.println("NUMBER_OF_TECHNICIANS = " + nbTech);
				for(Tournee tournee : tech){
					Tech technician = (Tech) tournee.getDepot();
					LinkedList<Request> requests = tournee.getListRequest();
					String chaine = ""+technician.getId();
					for(Request request : requests){
						chaine += " "+request.getId();
					}
					writer.println(chaine);
				}
			}
		}catch(Exception e){
			System.err.println(e);
			return false;
		}
		return true;
	}
}
