package edu.neu.csye6200.vehicle;

/**CSYE 6200 Assignment2
 * 
 * @author Yanchen Wang
 * @NUID 001790449
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RegistryIO implements Registry {
	
	private ArrayList<String> VehicleNameList=new ArrayList<String>();
	
	private static Logger log = Logger.getLogger(RegistryIO.class.getName());
	
	/**
	 * Constructor
	 */
	public RegistryIO() {
		log.info("Create a RegistryIO class");
	}
	
	public void load(VehicleRegistry vr,String filename){
		log.info("Loading VehicleList");
		FileReader fr;
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String inputLine;
			int i=0;
			try {
				VehicleRegistry vrnew=VehicleRegistry.instance();
				while((inputLine = in.readLine())!=null){
					String r1,r2,r3,r4,r5,r6,r7=new String();
					inputLine=inputLine.trim();
					if(i++>1){
						r1=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r2=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r3=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r4=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r5=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r6=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
						r7=inputLine.substring(0,inputLine.indexOf(" ")).trim();
						Vehicle v=new Vehicle(r1,r2,Integer.parseInt(r5),Integer.parseInt(r6),Double.parseDouble(r7),r3,r4);
						vrnew.addingVehicle(v);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.severe("Empty file");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.severe("Failed to find file");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void save(VehicleRegistry vr,String filename){
		log.info("Saving VehicleList");
		
		String blankspace=new String();
		blankspace=" ";
		
		File file = new File(filename);
		try {
			file.createNewFile();
			FileWriter fw=new FileWriter(file);
			fw.write("Vehicle List\r\n");
			fw.write("Vehicle Information\tMake\tModel\tYear\tLicense\t   Passengers\tFuelCap\tkpl\tRange\r\n");
			ArrayList<Vehicle> nl=new ArrayList<Vehicle>();
			nl=vr.getVehicleList();
			for(Vehicle i:nl){
				fw.write("\t \t \t"+i.getMake()+blankspace+"\t"+i.getModel()+blankspace+"\t"+i.getYear()+blankspace+"\t"+i.getLicense_plate()+blankspace+"\t\t"+i.getPassengers()+blankspace+"\t"+i.getFuelCap()+blankspace+"\t"+i.getKpl()+blankspace+"\t"+i.getRange()+blankspace+"\r\n");
			}
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Failed to new file");
			log.severe("Failed to new file");
			e1.printStackTrace();
		}
				
	}
	
	private void saveinFile(Vehicle i,String filename){
		
		String folderPath=new String();
		folderPath="C:\\Users\\Yanchen\\workspace\\Assignment4b\\eachVehicle\\";
		filename=folderPath+filename+".txt";
		VehicleNameList.add(filename);
		
		String blankspace=new String();
		blankspace=" ";
		
		try {
			@SuppressWarnings("resource")
			FileWriter fw=new FileWriter(filename);
			
			fw.write("Vehicle \r\n");
			fw.write("Vehicle Information\tMake\tModel\tYear\tLicense\t   Passengers\tFuelCap\tRange\r\n");
			fw.write("\t \t \t"+i.getMake()+blankspace+"\t"+i.getModel()+blankspace+"\t"+i.getYear()+blankspace+"\t"+i.getLicense_plate()+blankspace+"\t\t"+i.getPassengers()+blankspace+"\t"+i.getFuelCap()+blankspace+"\t"+i.getKpl()+blankspace+"\t"+i.getRange()+blankspace+"\r\n");
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to new file");
			log.severe("Failed to new file");
			e.printStackTrace();
		}
		
	}


	@Override
	public void save(Vehicle vehicle) {
		
		log.info("Starting save(vehicle) method");
		
		String fileName=new String();
		
		fileName=vehicle.getMake()+"-"+vehicle.getModel()+"-"+vehicle.getLicense_plate();
		
		saveinFile(vehicle,fileName);
		
	}
	
	public void load() {
		
		log.info("Starting load method");
		
		FileReader fr;
		String inputLine;
		int i=0;
		
		for(String filename:VehicleNameList){
			try {
				fr = new FileReader(filename);
				BufferedReader in = new BufferedReader(fr);
				
				VehicleRegistry vrnew=VehicleRegistry.instance();
				
				try {
					while((inputLine = in.readLine())!=null){
						String r1,r2,r3,r4,r5,r6,r7=new String();
						inputLine=inputLine.trim();
						
						if(i++>1){
							r1=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r2=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r3=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r4=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r5=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r6=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							r7=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							Vehicle v=new Vehicle(r1,r2,Integer.parseInt(r5),Integer.parseInt(r6),Double.parseDouble(r7),r3,r4);
							vrnew.addingVehicle(v);
						}
					}
					i=0;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.warning("Failed to find file");
			}
		}
		
		
	}

	/**
	 * @return the vehicleNameList
	 */
	public ArrayList<String> getVehicleNameList() {
		return VehicleNameList;
	}

	/**
	 * @param vehicleNameList the vehicleNameList to set
	 */
	public void setVehicleNameList(ArrayList<String> vehicleNameList) {
		VehicleNameList = vehicleNameList;
	}
	
}
