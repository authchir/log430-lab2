package ca.etsmtl.log430.lab2.management;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.data.DeliveriesData;
import ca.etsmtl.log430.lab2.entities.Delivery;

public class DeliveriesManagement {
	
	public static void loadData(String path) throws Exception {
		DeliveriesData.loadDataFromFile(path);
	}
	
	public static Delivery getDeliveryById(String deliveryID) {
		return DeliveriesData.getDeliveryByID(deliveryID);
	}
	
	public static ArrayList<Delivery> getListOfDeliveries() {
		return DeliveriesData.getListOfDeliveries();
	}

}
