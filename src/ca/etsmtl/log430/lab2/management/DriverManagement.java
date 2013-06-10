package ca.etsmtl.log430.lab2.management;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.data.DriverData;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;

import ca.etsmtl.log430.lab2.entities.DeliveryList;

public class DriverManagement {
	
	public static void loadData(String path) throws Exception {
		DriverData.loadDataFromFile(path);
	}
	
	public static ArrayList<Delivery> getDeliveriesMadeByDriver(String driverID) {
		return DriverData.getDeliveriesMadeForDriver(driverID);
	}

	public static ArrayList<Driver> getDriversList() {
		return DriverData.getDriverList();
	}

}
