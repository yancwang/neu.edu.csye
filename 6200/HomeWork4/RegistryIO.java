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

public class RegistryIO {
	
	private static Logger log = Logger.getLogger(RegistryIO.class.getName());
	
	public void load(VehicleRegistry vr,String filename){
		log.info("Loading VehicleList");
		FileReader fr;
		try {
			fr = new FileReader("output.txt");
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
						Vehicle v=new Vehicle(r1,r2,Integer.parseInt(r5),Integer.parseInt(r6),Integer.parseInt(r7),r3,r4);
						vrnew.addingVehicle(v);
					}
				}
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
	
	@SuppressWarnings("unchecked")
	public void save(VehicleRegistry vr,String filename){
		log.info("Saving VehicleList");
		String blankspace=new String();
		blankspace=" ";
		FileWriter fw;
		try {
			fw=new FileWriter(filename);
			fw.write("Vehicle List\r\n");
			fw.write("Vehicle Information\tMake\tModel\tYear\tLicense\t   Passengers\tFuelCap\tkpl\tRange\r\n");
			ArrayList<Vehicle> nl=new ArrayList<Vehicle>();
			nl=vr.getVehicleList();
			for(Vehicle i:nl){
				fw.write("\t \t \t"+i.getMake()+blankspace+"\t"+i.getModel()+blankspace+"\t"+i.getYear()+blankspace+"\t"+i.getLicense_plate()+blankspace+"\t\t"+i.getPassengers()+blankspace+"\t"+i.getFuelCap()+blankspace+"\t"+i.getKpl()+blankspace+"\t"+i.getRange()+blankspace+"\r\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to new file");
			log.warning("Failed to new file");
			e.printStackTrace();
		}
		
	}
	
	private void save(Vehicle i,String filename){
		
		FileWriter fw;
		try {
			fw=new FileWriter(filename);
			fw.write("Vehicle Information\tMake\tModel\tYear\tLicense\t   Passengers\tFuelCap\tRange\r\n");
			fw.write("\t \t \t"+i.getMake()+"\t"+i.getModel()+"\t"+i.getYear()+"\t"+i.getLicense_plate()+"\t \t"+i.getPassengers()+"\t"+i.getFuelCap()+"\t"+i.getRange()+"\r\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
