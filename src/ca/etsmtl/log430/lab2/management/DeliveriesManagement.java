package ca.etsmtl.log430.lab2.management;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.data.DeliveriesData;
import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;

public class DeliveriesManagement {
	
	public static void loadData(String path) throws Exception {
		DeliveriesData.loadDataFromFile(path);
	}
	
	public static boolean assignDriver(Delivery delivery, Driver driver) throws DriverAlreadyAssignedException, DeliveryDoesNotExistException, DriverDoesNotExistException, ConflictingDeliveryException, DriverScheduleFullException {
		Delivery d = getDelivery(delivery);
		
		if (d == null)
			throw new DeliveryDoesNotExistException();
		
		if (d.getDriverAssigned() != null)
			throw new DriverAlreadyAssignedException();
		
		DriverManagement.assignDelivery(driver, delivery);
		
		return DeliveriesData.assignDriver(delivery, driver);
	}
	
	public static ArrayList<Delivery> getUnassignedDeliveriesList() {
		return DeliveriesData.getUnassignedDeliveriesList();
	}
	
	public static Delivery getDeliveryById(String deliveryID) {
		return DeliveriesData.getDeliveryByID(deliveryID);
	}
	
	public static Delivery getDelivery(Delivery delivery) {
		return DeliveriesData.getDelivery(delivery);
	}
	
	public static ArrayList<Delivery> getListOfDeliveries() {
		return DeliveriesData.getListOfDeliveries();
	}

}
