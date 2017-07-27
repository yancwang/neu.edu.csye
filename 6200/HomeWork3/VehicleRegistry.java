import java.util.ArrayList;
import java.util.HashMap;

/**CSYE 6200 Assignment2
 * 
 * @author Yanchen Wang
 * @NUID 001790449
 *
 */


public class VehicleRegistry {
	
	private ArrayList<Vehicle> VehicleList=new ArrayList<Vehicle>();
	private HashMap<String, Vehicle> vehicleMap=new HashMap<String, Vehicle>();
	
	
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
	public void storeinmap(){
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
