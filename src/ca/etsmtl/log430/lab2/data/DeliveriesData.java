package ca.etsmtl.log430.lab2.data;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;

public class DeliveriesData {
	
	private static ArrayList<Delivery> data;
	
	public static void loadDataFromFile(String path) throws Exception {
		DeliveryReader reader = new DeliveryReader(path);
		
		data = reader.getListOfDeliveries();
		
		if (data == null)
			throw new Exception("Deliveries data could not be loaded. No data.");
	}
	
	public static boolean assignDriver(Delivery delivery, Driver driver) {
		int index = data.indexOf(delivery);
		
		if (index == -1)
			return false;
		
		data.get(index).assignDriver(driver);
		return true;
	}
	
	public static Delivery getDeliveryByID(String deliveryID) {
		for (Delivery d : data)
			if (d.getDeliveryID().compareToIgnoreCase(deliveryID) == 0)
				return d;
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Delivery> getListOfDeliveries() {
		return (ArrayList<Delivery>) data.clone();
	}

	public static ArrayList<Delivery> getUnassignedDeliveriesList() {
		ArrayList<Delivery> list = new ArrayList<Delivery>();
		
		for (Delivery d : data)
			if (d.getDriverAssigned() == null)
				list.add(d);
		
		return list;
	}

	public static Delivery getDelivery(Delivery delivery) {
		int index = data.indexOf(delivery);
		
		if (index == -1)
			return null;
		
		return data.get(index);
	}

}
