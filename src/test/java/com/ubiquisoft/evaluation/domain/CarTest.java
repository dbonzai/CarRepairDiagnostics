package com.ubiquisoft.evaluation.domain;

import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
	static private Car c;
	static private List<Part> parts;
	static private Part engine = new Part();
	static private Part electrical = new Part();
	static private Part fuelFilter = new Part();
	static private Part oilFilter = new Part();
	static private Part tire = new Part();
	static private Map<PartType, Integer> missingParts;

	@BeforeAll
	static void beforeAll() {
		engine.setType(PartType.ENGINE);
		electrical.setType(PartType.ELECTRICAL);
		fuelFilter.setType(PartType.FUEL_FILTER);
		oilFilter.setType(PartType.OIL_FILTER);
		tire.setType(PartType.TIRE);
	}

	@BeforeEach
	void beforeEach() {
		parts = new ArrayList<>();
		c = new Car();
		missingParts = new HashMap<>();
	}

	@AfterAll
	static void afterAll() {
		c = null;
		parts = null;
		missingParts = null;
	}

	@Test
	@DisplayName("All Parts")
	void allPartsTest() {
		parts.add(engine);
		parts.add(electrical);
		parts.add(fuelFilter);
		parts.add(oilFilter);
		parts.add(tire);
		parts.add(tire);
		parts.add(tire);
		parts.add(tire);
		c.setParts(parts);
		System.out.print("No Parts Missing Test - Missing Parts:");
		System.out.println(c.getMissingPartsMap());
		assertEquals(missingParts, c.getMissingPartsMap());
	}

	@Test
	@DisplayName("Some Parts")
	void somePartsTest() {
		parts.add(engine);
		missingParts.put(electrical.getType(), 1);
		missingParts.put(PartType.FUEL_FILTER, 1);
		parts.add(oilFilter);
		parts.add(tire);
		parts.add(tire);
		parts.add(tire);
		parts.add(tire);
		c.setParts(parts);
		System.out.print("Some Parts Missing Test - Missing Parts:");
		System.out.println(c.getMissingPartsMap());
		assertEquals(missingParts, c.getMissingPartsMap());
	}

	@Test
	@DisplayName("No Parts")
	void noPartsTest() {
		missingParts.put(engine.getType(), 1);
		missingParts.put(electrical.getType(), 1);
		missingParts.put(PartType.FUEL_FILTER, 1);
		missingParts.put(oilFilter.getType(), 1);
		missingParts.put(tire.getType(), 4);
		c.setParts(parts);
		System.out.print("All Parts Missing Test - Missing Parts:");
		System.out.println(c.getMissingPartsMap());
		assertEquals(missingParts, c.getMissingPartsMap());
	}

	@Test
	@DisplayName("Some Tires")
	void someTiresTest() {
		parts.add(engine);
		parts.add(electrical);
		parts.add(fuelFilter);
		parts.add(oilFilter);
		missingParts.put(tire.getType(),2);
		parts.add(tire);
		parts.add(tire);
		c.setParts(parts);
		System.out.print("Some Tires Missing Test - Missing Parts:");
		System.out.println(c.getMissingPartsMap());
		assertEquals(missingParts, c.getMissingPartsMap());
	}

}