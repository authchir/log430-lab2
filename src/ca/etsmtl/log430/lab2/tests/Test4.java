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
import ca.etsmtl.log430.lab2.management.ConflictingDeliveryException;
import ca.etsmtl.log430.lab2.management.DeliveriesManagement;
import ca.etsmtl.log430.lab2.management.DriverManagement;
import ca.etsmtl.log430.lab2.management.DriverScheduleFullException;

public class Test4 extends TestCase {

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
	public void testJuniorLessThanMax() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery = DeliveriesManagement.getDeliveryById("D150");

		try {
			DeliveriesManagement.assignDriver(delivery, junior);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testJuniorEqualsMax() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D150"); // 2h
		Delivery delivery2 = DeliveriesManagement.getDeliveryById("D155"); // 5h
		Delivery delivery3 = DeliveriesManagement.getDeliveryById("D156"); // 5h
		

		try {
			DeliveriesManagement.assignDriver(delivery1, junior);
			DeliveriesManagement.assignDriver(delivery2, junior);
			DeliveriesManagement.assignDriver(delivery3, junior);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testJuniorMoreThanMaxWithMultipleDeliveries() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D150"); // 2h
		Delivery delivery2 = DeliveriesManagement.getDeliveryById("D155"); // 5h
		Delivery delivery3 = DeliveriesManagement.getDeliveryById("D156"); // 5h
		
		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D157"); // 2h
		

		try {
			DeliveriesManagement.assignDriver(delivery1, junior);
			DeliveriesManagement.assignDriver(delivery2, junior);
			DeliveriesManagement.assignDriver(delivery3, junior);
		} catch (Exception e) {
			fail();
		}

		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, junior);
		} catch (DriverScheduleFullException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}

	@Test
	public void testJuniorMoreThanMaxWithOneDelivery() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D177"); // 13h

		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, junior);
		} catch (DriverScheduleFullException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}
	
	
	// SNR
	@Test
	public void testSeniorLessThanMax() {
		Driver senior = DriverData.findDriverByID("DRV300");

		Delivery delivery = DeliveriesManagement.getDeliveryById("D150");

		try {
			DeliveriesManagement.assignDriver(delivery, senior);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSeniorEqualsMax() {
		Driver senior = DriverData.findDriverByID("DRV300");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D153"); // 3h
		Delivery delivery2 = DeliveriesManagement.getDeliveryById("D156"); // 5h
		

		try {
			DeliveriesManagement.assignDriver(delivery1, senior);
			DeliveriesManagement.assignDriver(delivery2, senior);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSeniorMoreThanMaxWithMultipleDeliveries() {
		Driver senior = DriverData.findDriverByID("DRV300");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D155"); // 5h
		
		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D156"); // 5h
		

		try {
			DeliveriesManagement.assignDriver(delivery1, senior);
		} catch (Exception e) {
			fail();
		}

		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, senior);
		} catch (DriverScheduleFullException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}

	@Test
	public void testSeniorMoreThanMaxWithOneDelivery() {
		Driver senior = DriverData.findDriverByID("DRV300");

		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D177"); // 13h

		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, senior);
		} catch (DriverScheduleFullException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}

	
	
	
	//Conflicts
	@Test
	public void testDriverScheduleConflictSecondStartsBeforeFirstEnds() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D153"); 		//08:00 - 11:00
		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D155");	//10:00 - 15:00

		try {
			DeliveriesManagement.assignDriver(delivery1, junior);
		} catch (Exception e) {
			fail();
		}
		
		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, junior);
		} catch (ConflictingDeliveryException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}
	
	@Test
	public void testDriverScheduleConflictSecondIsContainedWithinFirst() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D155"); 		//10:00 - 15:00
		Delivery deliveryFail = DeliveriesManagement.getDeliveryById("D154");	//13:00 - 15:00

		try {
			DeliveriesManagement.assignDriver(delivery1, junior);
		} catch (Exception e) {
			fail();
		}
		
		boolean b = false;

		try {
			DeliveriesManagement.assignDriver(deliveryFail, junior);
		} catch (ConflictingDeliveryException e) {
			b = true;
		} catch (Exception e) {
			fail();
		}

		assertTrue(b);
	}
	
	@Test
	public void testDriverScheduleConflictOneRightAfterOther() {
		Driver junior = DriverData.findDriverByID("DRV200");

		Delivery delivery1 = DeliveriesManagement.getDeliveryById("D155"); 	//10:00 - 15:00
		Delivery delivery2 = DeliveriesManagement.getDeliveryById("D156");	//15:00 - 20:00

		try {
			DeliveriesManagement.assignDriver(delivery1, junior);
			DeliveriesManagement.assignDriver(delivery2, junior);
		} catch (Exception e) {
			fail();
		}
	}
	
}
