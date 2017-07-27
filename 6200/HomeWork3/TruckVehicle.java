
public class TruckVehicle extends Vehicle {
	private int truckheight;
	private int truckwidth;
	private int trucklength;

	public TruckVehicle(String make, String model, int passengers, int fuelCap, double kpl, String year,
			String license,int height,int width,int length) {
		super(make, model, passengers, fuelCap, kpl, year, license);
		// TODO Auto-generated constructor stub
		this.setTruckheight(height);
		this.setTrucklength(length);
		this.setTruckwidth(width);
	}
	
	
	public int calculateCargo(){
		int cargo;
		cargo=this.getTruckheight()*this.getTrucklength()*this.getTruckwidth();
		return cargo;
		
	}
	
	
	public void displayInfo(){
		super.displayInfo();
		System.out.println("Cargo Area: "+this.calculateCargo());
	}

	/**
	 * @return the truckheight
	 */
	public int getTruckheight() {
		return truckheight;
	}

	/**
	 * @param truckheight the truckheight to set
	 */
	public void setTruckheight(int truckheight) {
		this.truckheight = truckheight;
	}

	/**
	 * @return the truckwidth
	 */
	public int getTruckwidth() {
		return truckwidth;
	}

	/**
	 * @param truckwidth the truckwidth to set
	 */
	public void setTruckwidth(int truckwidth) {
		this.truckwidth = truckwidth;
	}

	/**
	 * @return the trucklength
	 */
	public int getTrucklength() {
		return trucklength;
	}

	/**
	 * @param trucklength the trucklength to set
	 */
	public void setTrucklength(int trucklength) {
		this.trucklength = trucklength;
	}

}
