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
		
		VehicleRegistry vr=new VehicleRegistry();
		
		
		Vehicle suv=new Vehicle("BMW","X5",5,30,10.6,"2012","VBG984");
		suv.calculateRange();
		
		vr.addingVehicle(suv);
		
		
		Vehicle sportscar=new Vehicle("BMW","Z4",2,24,8.9,"2014","MA640");
		sportscar.calculateRange();
				
		vr.addingVehicle(sportscar);
		
		Vehicle crv=new Vehicle("Honda","r4",5,28,7.6,"2013","MA115");
		crv.calculateRange();
				
		vr.addingVehicle(crv);
		
		vr.displayVehicleList();
		
		RegistryIO rio=new RegistryIO();
		rio.save(vr,"output.txt");
		
		FileReader fr;
		try {
			fr = new FileReader("output.txt");
			BufferedReader in = new BufferedReader(fr);
			String inputLine;
			int i=0;
			try {
				while((inputLine = in.readLine())!=null){
					String rl=new String();
					inputLine=inputLine.trim();
					rl=inputLine.substring(0,inputLine.indexOf(" ")).trim();
					inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
					if(i++>1){
						System.out.println(rl);
						while(inputLine.indexOf(" ")>0){
							rl=inputLine.substring(0,inputLine.indexOf(" ")).trim();
							inputLine=inputLine.substring(inputLine.indexOf(" ")).trim();
							System.out.println(rl);
						}
					}
						
					
					
				}
				in.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}


