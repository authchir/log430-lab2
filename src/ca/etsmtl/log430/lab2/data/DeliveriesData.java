package ca.etsmtl.log430.lab2.data;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.entities.Delivery;

public class DeliveriesData {
	
	private static ArrayList<Delivery> data;
	
	public static void loadDataFromFile(String path) throws Exception {
		DeliveryReader reader = new DeliveryReader(path);
		
		data = reader.getListOfDeliveries();
		
		if (data == null)
			throw new Exception("Deliveries data could not be loaded. No data.");
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

}
