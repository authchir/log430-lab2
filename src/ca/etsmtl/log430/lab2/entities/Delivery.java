package ca.etsmtl.log430.lab2.entities;

import java.util.ArrayList;

/**
 * This class defines the Delivery object for the system.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.5, 2012-May-31
 */

/*
 * Modification Log **********************************************************
 * 
 * v1.5, R. Champagne, 2012-May-31 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2011-Feb-02 - Various refactorings, conversion of
 * comments to javadoc format.
 * 
 * v1.2, R. Champagne, 2002-May-21 - Adapted for use at ETS.
 * 
 * v1.1, G.A.Lewis, 01/25/2001 - Bug in second constructor. Removed null
 * assignment to deliveryID after being assigned a value.
 * 
 * v1.0, A.J. Lattanze, 12/29/99 - Original version.
 * ***************************************************************************
 */

public class Delivery {

	/**
	 * Delivery ID
	 */
	private String deliveryID;

	/**
	 * Delivery address
	 */
	private String address;

	/**
	 * Time at which the delivery should be planned
	 */
	private int desiredDeliveryTime;

	/**
	 * Start time of the course
	 */
	private int estimatedDeliveryDuration;

	/**
	 * List of teachers assigned to the course
	 */
	private Driver driverAssigned = null;

	public Delivery(String deliveryID) {
		this(deliveryID, 0);
	}

	public Delivery(String deliveryID, int estimatedDuration) {
		this.setDeliveryID(deliveryID);
		this.setEstimatedDeliveryDuration(estimatedDuration);
	}

	public Delivery() {

	}

	/**
	 * Assign a teacher to a class.
	 * 
	 * @param driver
	 */
	public void assignDriver(Driver driver) {
		this.driverAssigned = driver;
	}

	public void setDeliveryID(String deliveryID) {
		this.deliveryID = deliveryID;
	}

	public String getDeliveryID() {
		return deliveryID;
	}

	public void setDesiredDeliveryTime(String time) {
		this.desiredDeliveryTime = Integer.parseInt(time);
	}

	public int getDesiredDeliveryTime() {
		return desiredDeliveryTime;
	}

	public void setEstimatedDeliveryDuration(int duration) {
		this.estimatedDeliveryDuration = duration;
	}

	public int getEstimatedDeliveryDuration() {
		return estimatedDeliveryDuration;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Driver getDriverAssigned() {
		return driverAssigned;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.getDeliveryID() + " ");
		
		if (this.getDesiredDeliveryTime() != 0)
			sb.append(this.getDesiredDeliveryTime() + " ");
		
		if (this.getEstimatedDeliveryDuration() != 0)
			sb.append(this.getEstimatedDeliveryDuration() + " ");
		
		if (this.getAddress() != null)
			sb.append("- " + this.getAddress());
		
		return sb.toString();
	}
} // Delivery class