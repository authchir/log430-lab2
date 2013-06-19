package ca.etsmtl.log430.lab2.management;

import java.util.ArrayList;

import ca.etsmtl.log430.lab2.data.DriverData;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;

public class DriverManagement {
	
	public static void loadData(String path) throws Exception {
		DriverData.loadDataFromFile(path);
	}
	
	public static boolean assignDelivery(Driver driver, Delivery newDelivery) throws DriverDoesNotExistException, ConflictingDeliveryException, DriverScheduleFullException {
		Driver d = getDriver(driver);
		
		if (d == null)
			throw new DriverDoesNotExistException();
		
		if (driver.getType().equals("JNR")) {
			if (driver.getAssignedDeliveryTimeCount() + newDelivery.getEstimatedDeliveryDuration() >= 1200)
				throw new DriverScheduleFullException();
		} else if (driver.getType().equals("SNR"))
			if (driver.getAssignedDeliveryTimeCount() + newDelivery.getEstimatedDeliveryDuration() >= 800)
				throw new DriverScheduleFullException();
				
		for (Delivery delivery : d.getDeliveriesAssigned()) {
			if (newDelivery.getDesiredDeliveryTime() <= delivery.getDesiredDeliveryTime())
				if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) >= (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
					throw new ConflictingDeliveryException();
			
			if (newDelivery.getDesiredDeliveryTime() >= delivery.getDesiredDeliveryTime())
				if (newDelivery.getDesiredDeliveryTime() < (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
					throw new ConflictingDeliveryException();

			if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) > delivery.getDesiredDeliveryTime())
				if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) <= (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
					throw new ConflictingDeliveryException();
		}

		return DriverData.assignDelivery(driver, newDelivery);
	}
	
	public static Driver findDriverByID(String driverID) {
		return DriverData.findDriverByID(driverID);
	}

	public static ArrayList<Delivery> getDeliveriesMadeByDriver(String driverID) {
		return DriverData.getDeliveriesMadeForDriver(driverID);
	}

	public static ArrayList<Driver> getDriversList() {
		return DriverData.getDriverList();
	}

	public static Driver getDriver(Driver driver) {
		return DriverData.getDriver(driver);
	}

}
