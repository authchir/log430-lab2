package ca.etsmtl.log430.lab2.tests;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;
import ca.etsmtl.log430.lab2.data.DriverData;
import ca.etsmtl.log430.lab2.management.ConflictingDeliveryException;
import ca.etsmtl.log430.lab2.management.DeliveriesManagement;
import ca.etsmtl.log430.lab2.management.DeliveryDoesNotExistException;
import ca.etsmtl.log430.lab2.management.DriverAlreadyAssignedException;
import ca.etsmtl.log430.lab2.management.DriverDoesNotExistException;
import ca.etsmtl.log430.lab2.management.DriverManagement;


public class Test3 extends TestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DeliveriesManagement.loadData("deliveries.txt");
		DriverManagement.loadData("drivers.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Driver driver1 = DriverData.findDriverByID("DRV001");
		Driver driver2 = DriverData.findDriverByID("DRV002");
		Delivery delivery = DeliveriesManagement.getDeliveryById("D150");
		
		try {
			DeliveriesManagement.assignDriver(delivery, driver1);
		} catch (Exception e) { fail(); }
		
		boolean b = false;
		try {
			DeliveriesManagement.assignDriver(delivery, driver2);
		} catch (DriverAlreadyAssignedException e) { b = true;
		} catch (Exception e) { }
		assertTrue(b);
	}
}
