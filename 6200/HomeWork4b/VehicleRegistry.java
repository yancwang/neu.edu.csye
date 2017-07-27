package edu.neu.csye6200.vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**CSYE 6200 Assignment2
 * 
 * @author Yanchen Wang
 * @NUID 001790449
 *
 */


public class VehicleRegistry {
	
	private ArrayList<Vehicle> VehicleList=new ArrayList<Vehicle>();
	private HashMap<String, Vehicle> vehicleMap=new HashMap<String, Vehicle>();
	
	RegistryIO newRIO=new RegistryIO();
	
	private static Logger log = Logger.getLogger(VehicleRegistry.class.getName());
	
	private static VehicleRegistry instance = null;
	
	private VehicleRegistry() { 
		
	} 
	
	public static VehicleRegistry instance(){
		log.info("Create a VehicleRegistry class");
		
		if (instance == null){
			instance = new VehicleRegistry();
			log.info("Constructing a VehicleRegistry instance");
		}
		
		return instance;
		
	}
	
	/**
	 * @save each vehicle to different files
	 */
	public void savaEachVehicle(){
		log.info("Starting save-each-vehicle method");
		
		for(Vehicle i:VehicleList){
			newRIO.save(i);
		}
	}
	
	/**
	 * @save all vehicle
	 */
	public void savaAllVehicle(VehicleRegistry vr){
		log.info("Starting save-all-vehicle method");
		newRIO.save(vr, "output.txt");
	}
	
	/**
	 * @sort vehicles by licenses
	 */
	public void sortVehicleByLicense(){
		log.info("Sorting Vehicle By License");
		System.out.println("Sorting VehicleList By License");
		String licenseSort[]=new String[10];
		for(int i=0;i<VehicleList.size();i++){
			licenseSort[i]=VehicleList.get(i).getLicense_plate();
			vehicleMap.put(licenseSort[i], VehicleList.get(i));
		}
		
		
		String change=new String();
		for(int k=0;k<licenseSort.length-1;k++){
			for(int i=0;i<licenseSort.length-1;i++){
				if(licenseSort[i].compareTo(licenseSort[i+1])>0){
					change=licenseSort[i];
					licenseSort[i]=licenseSort[i+1];
					licenseSort[i+1]=change;
				}
			}
		}
		
		
		for(int i=0;i<licenseSort.length;i++){
			vehicleMap.get(licenseSort[i]).displayInfo();
		}
		
	}
	
	
	/** 
	 * @add the vehicle
	 */
	public void addingVehicle(Vehicle vh){
		VehicleList.add(vh);
	}
	
	/**
	 * @get the vehicle
	 */
	public Vehicle gettingVehiclebylicense(String li){
		
		Vehicle getvh = null;
		
		for(int i=0;i<VehicleList.size();i++){
			if(VehicleList.get(i).getLicense_plate().equals(li)){
				getvh=VehicleList.get(i);
			}
		}
		return getvh;
			
	}
	
	/**
	 * @remove the vehicle
	 */
	public void removeVehiclebylicense(String li){
		for(int i=0;i<VehicleList.size();i++){
			if(VehicleList.get(i).getLicense_plate().equals(li)){
				VehicleList.remove(i);
			}
		}
	}

	/**
	 * @display the vehiclelist
	 */
	public void displayVehicleList(){
		System.out.println("Vehicle List");
		for(int i=0;i<VehicleList.size();i++){
			if(VehicleList.get(i)!=null){
				VehicleList.get(i).displayInfo();
			}
		}
	}
	
	/**
	 * @store vehicle in vehicle map
	 */
	public void storeinMap(){
		for(Vehicle v : VehicleList){
			vehicleMap.put(v.getLicense_plate(), v);
		}
	}
	
	/**
	 * @retrive vehicle in map
	 */
	public Vehicle retriveInMap(String li){
		return vehicleMap.get(li);
	}
	
	
	/**
	 * @return the vehiclelist
	 */
	public ArrayList getVehicleList() {
		return VehicleList;
	}

	/**
	 * @param vehicle the vehiclelist to set
	 */
	public void setVehicleList(ArrayList vehiclelist) {
		VehicleList = vehiclelist;
	}

	/**
	 * @return the vehicleMap
	 */
	public HashMap getVehicleMap() {
		return vehicleMap;
	}

	/**
	 * @param vehicleMap the vehicleMap to set
	 */
	public void setVehicleMap(HashMap vehicleMap) {
		this.vehicleMap = vehicleMap;
	}

	
	
}
