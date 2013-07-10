package ca.etsmtl.log430.lab2.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.etsmtl.log430.lab2.data.DriverData;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;

public class DriverManagement {
	
	public static void loadData(String path) throws Exception {
		DriverData.loadDataFromFile(path);
	}
	
	public static boolean assignDelivery(Driver driver, Delivery newDelivery) throws DriverDoesNotExistException, ConflictingDeliveryException, DriverScheduleFullException, DeliveryDoesNotExistException {
		Driver d = getDriver(driver);
		
		if (d == null)
			throw new DriverDoesNotExistException();
		

		if (newDelivery == null)
			throw new DeliveryDoesNotExistException();

		if (!noConflictsWillHappenUponAssignation(driver, newDelivery))
			throw new ConflictingDeliveryException();

		if (!driverCanDoSomething(driver, newDelivery))
			throw new DriverScheduleFullException();
		
//		if (driver.getType().equals("JNR")) {
//			if (driver.getAssignedDeliveryTimeCount() + newDelivery.getEstimatedDeliveryDuration() > 1200)
//				throw new DriverScheduleFullException();
//		} else if (driver.getType().equals("SNR"))
//			if (driver.getAssignedDeliveryTimeCount() + newDelivery.getEstimatedDeliveryDuration() > 800)
//				throw new DriverScheduleFullException();
//				
//		for (Delivery delivery : d.getDeliveriesAssigned()) {
//			if (newDelivery.getDesiredDeliveryTime() <= delivery.getDesiredDeliveryTime())
//				if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) >= (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
//					throw new ConflictingDeliveryException();
//			
//			if (newDelivery.getDesiredDeliveryTime() >= delivery.getDesiredDeliveryTime())
//				if (newDelivery.getDesiredDeliveryTime() < (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
//					throw new ConflictingDeliveryException();
//
//			if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) > delivery.getDesiredDeliveryTime())
//				if ((newDelivery.getDesiredDeliveryTime() + newDelivery.getEstimatedDeliveryDuration()) <= (delivery.getDesiredDeliveryTime() + delivery.getEstimatedDeliveryDuration()))
//					throw new ConflictingDeliveryException();
//		}

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
	
	
	
	
	
	
	
	


	private static boolean noConflictsWillHappenUponAssignation(Driver driver, Delivery delivery) {
		Date deliveryMinTime = getDeliveryMinTime(delivery);
		Date deliveryMaxTime = getDeliveryMaxTime(delivery);

		for (Delivery d : driver.getDeliveriesAssigned()) {
			Date minTime = getDeliveryMinTime(d);
			Date maxTime = getDeliveryMaxTime(d);
			if (isBetweenTwoDates(deliveryMinTime, minTime, maxTime) || isBetweenTwoDates(deliveryMaxTime, minTime, maxTime)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isBetweenTwoDates(Date date, Date dateMin, Date dateMax) {
		return dateMin.before(date) && dateMax.after(date);
	}

	private static Date getDeliveryMinTime(Delivery d) {
		return addDurationFactorToDeliveryTime(d, -1);
	}

	private static Date getDeliveryMaxTime(Delivery d) {
		return addDurationFactorToDeliveryTime(d, 1);
	}

	private static Date addDurationFactorToDeliveryTime(Delivery d, int factor) {
		Date deliveryTime = parseFourCharFormatDateString(d.getDesiredDeliveryTime());
		Date deliveryDuration = parseFourCharFormatDateString(d.getEstimatedDeliveryDuration());

		Calendar calDuration = Calendar.getInstance();
		calDuration.clear();
		calDuration.setTime(deliveryDuration);

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(deliveryTime);

		cal.add(Calendar.HOUR_OF_DAY, factor * calDuration.get(Calendar.HOUR_OF_DAY));
		cal.add(Calendar.MINUTE, factor * calDuration.get(Calendar.MINUTE));

		return cal.getTime();
	}
	
	private static boolean driverCanDoSomething(Driver driver, Delivery delivery) {
		Calendar calDuration = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();

		if (driver == null || delivery == null)
			return false;
		
		calDuration.clear();
		
		cal.clear();
		cal.setTime(parseFourCharFormatDateString(delivery.getEstimatedDeliveryDuration()));
		calDuration.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) * 2);
		calDuration.add(Calendar.MINUTE, cal.get(Calendar.MINUTE) * 2);

		for (Delivery d : driver.getDeliveriesAssigned()) {
			cal.clear();
			cal.setTime(parseFourCharFormatDateString(d.getEstimatedDeliveryDuration()));
			calDuration.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) * 2);
			calDuration.add(Calendar.MINUTE, cal.get(Calendar.MINUTE) * 2);
		}
		
		int days = calDuration.get(Calendar.DAY_OF_YEAR);
		int hours = calDuration.get(Calendar.HOUR_OF_DAY);
		int minutes = calDuration.get(Calendar.MINUTE);
		double totalHours = (days - 1) * 24 + hours + minutes / 100.0;
		
		double maxHours = 0;
		if("JNR".equals(driver.getType())){
			maxHours = 12;
		} else if("SNR".equals(driver.getType())){
			maxHours = 8;
		}
		
		if(totalHours <= maxHours){
			return true;
		} else {
			return false;
		}
	}

	private static Date parseFourCharFormatDateString(String s) {
		SimpleDateFormat sd = new SimpleDateFormat("HHmm");
		Date d = null;
		
		try {
			d = sd.parse(s);
		} catch (ParseException ex) {
		}

		return d;
	}

}
