package edu.neu.csye6200.vehicle;

public interface Registry {
	
	public void save(VehicleRegistry vr,String filename);
	
	public void save(Vehicle vehicle);
	
	public void load();
	
}
