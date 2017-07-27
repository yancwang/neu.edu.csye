package edu.neu.csye6200.vehicle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**CSYE 6200 Assignment2
 * 
 * @author Yanchen Wang
 * @NUID 001790449
 *
 */


public class VehicleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		run();

	}
	
	public static void run(){
		
		VehicleRegistry vr=VehicleRegistry.instance();
		
		
		Vehicle suv1=new Vehicle("BMW","X5",5,30,10.6,"2012","VBG784");
		suv1.calculateRange();
		
		vr.addingVehicle(suv1);
		
		Vehicle suv2=new Vehicle("BMW","X5",5,30,10.6,"2012","VBG984");
		suv2.calculateRange();
		
		vr.addingVehicle(suv2);
			
		Vehicle sportscar1=new Vehicle("BMW","Z4",2,24,8.9,"2014","MA640");
		sportscar1.calculateRange();
				
		vr.addingVehicle(sportscar1);
		
		Vehicle sportscar2=new Vehicle("BMW","Z4",2,24,8.9,"2012","MA340");
		sportscar2.calculateRange();
				
		vr.addingVehicle(sportscar2);
		
		Vehicle crv1=new Vehicle("Honda","r4",5,28,7.6,"2013","CA115");
		crv1.calculateRange();
				
		vr.addingVehicle(crv1);
		
		Vehicle crv2=new Vehicle("Honda","r4",5,28,7.6,"2012","MA146");
		crv2.calculateRange();
				
		vr.addingVehicle(crv2);
		
		Vehicle mini1=new Vehicle("BMW","mini",4,22,6.8,"2011","NYC110");
		mini1.calculateRange();
		
		vr.addingVehicle(mini1);
		
		Vehicle mini2=new Vehicle("BMW","mini",4,22,6.8,"2011","MA087");
		mini2.calculateRange();
		
		vr.addingVehicle(mini2);
		
		Vehicle van1=new Vehicle("Fort","C1",5,25,7.8,"2012","MA708");
		van1.calculateRange();
		
		vr.addingVehicle(van1);
		
		Vehicle van2=new Vehicle("Fort","C2",5,25,7.8,"2012","MA208");
		van2.calculateRange();
		
		vr.addingVehicle(van2);
		
		vr.displayVehicleList();
		
		vr.sortVehicleByLicense();
		
		vr.savaEachVehicle();
		
		vr.savaAllVehicle(vr);
	
	}
	
}


