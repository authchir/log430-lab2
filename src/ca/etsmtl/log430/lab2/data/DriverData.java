package ca.etsmtl.log430.lab2.data;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.DeliveryList;
import ca.etsmtl.log430.lab2.entities.Driver;

public  class DriverData {
	
	private static ArrayList<Driver> data;
	
	public static void loadDataFromFile(String path) throws Exception {
		DriverReader reader = new DriverReader(path);
		
		data = reader.getListOfDrivers();
		
		if (data == null)
			throw new Exception("Data could not be loaded. No data.");
	}
	
	public static Driver findDriverByID(String driverID) {
		for (Driver driver : data)
			if (driver.getDriverID().compareToIgnoreCase(driverID) == 0)
				return driver;
		
		return null;
	}
	
	public static ArrayList<Delivery> getDeliveriesMadeForDriver(String driverId) {
		return new ArrayList<Delivery>();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Driver> getDriverList() {
		return (ArrayList<Driver>) data.clone();
	}

}
