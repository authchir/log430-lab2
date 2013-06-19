package ca.etsmtl.log430.lab2.tests;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.etsmtl.log430.lab2.entities.Delivery;
import ca.etsmtl.log430.lab2.entities.Driver;
import ca.etsmtl.log430.lab2.data.DriverData;
import ca.etsmtl.log430.lab2.management.DeliveriesManagement;
import ca.etsmtl.log430.lab2.management.DriverAlreadyAssignedException;
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
		
		Driver driver = DriverData.findDriverByID("DRV200");
		Delivery delivery = DeliveriesManagement.getDeliveryById("D150");

		try {
			DeliveriesManagement.assignDriver(delivery, driver);
		} catch (Exception e) {
			fail();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Driver driver = DriverData.findDriverByID("DRV210");
		Delivery delivery = DeliveriesManagement.getDeliveryById("D150");

		boolean b = false;
		try {
			DeliveriesManagement.assignDriver(delivery, driver);
		} catch (DriverAlreadyAssignedException e) {
			b = true;
		} catch (Exception e) {
		}
		
		assertTrue(b);
	}
	
}
