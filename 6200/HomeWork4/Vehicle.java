package edu.neu.csye6200.vehicle;

/**CSYE 6200 Assignment2
 * 
 * @author Yanchen Wang
 * @NUID 001790449
 *
 */

public class Vehicle {
	
	private int passengers;
	private int fuelCap;
	private double kpl;
	private String model=new String();
	private String make=new String();
	private String year=new String();
	private double range;
	private String license_plate=new String();
		
	
	public Vehicle(String make,String model,int passengers,int fuelCap,double kpl,String year,String license){
		// TODO Auto-generated constructor stub
		this.setMake(make);
		this.setModel(model);
		this.setPassengers(passengers);
		this.setFuelCap(fuelCap);
		this.setKpl(kpl);
		this.setYear(year);
		this.setLicense_plate(license);
	}
	

	/**@calculate range
	 * 
	 */
	public void calculateRange(){
		
		this.setRange(this.getKpl()*this.getFuelCap());
		
	}

	/**display info
	 * 
	 */
	public void displayInfo(){
		System.out.println("Vehicle Information:");
		System.out.println("Vehicle Make: "+this.getMake()+"\nVehicle Model: "+this.getModel()+"\nVehicle Year: "+this.getYear()+"\nVehicle License: "+this.getLicense_plate()
		+"\nVehicle Passengers: "+this.getPassengers()+"\nVehicle FuelCap: "+this.getFuelCap()+"\nVehicle Range: "+this.getRange()+"\n");
	}
	

	/**
	 * @return the passengers
	 */
	public int getPassengers() {
		return passengers;
	}


	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}


	/**
	 * @return the fuelCap
	 */
	public int getFuelCap() {
		return fuelCap;
	}


	/**
	 * @param fuelCap the fuelCap to set
	 */
	public void setFuelCap(int fuelCap) {
		this.fuelCap = fuelCap;
	}


	/**
	 * @return the kpl
	 */
	public double getKpl() {
		return kpl;
	}


	/**
	 * @param kpl the kpl to set
	 */
	public void setKpl(double kpl) {
		this.kpl = kpl;
	}


	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}


	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}


	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	

	/**
	 * @return the range
	 */
    public double getRange() {
		return range;
	}

	
     /**
	 * @param range the range to set
	 */
	public void setRange(double range) {
		this.range = range;
	}


	/**
	 * @return the license_plate
	 */
	public String getLicense_plate() {
		return license_plate;
	}


	/**
	 * @param license_plate the license_plate to set
	 */
	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}
	
}
