package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;

public class CarDiagnosticEngine {
	private boolean valid = true;

	/**
	 * Check car for missing data, missing parts and damaged parts
	 * Process will not check further condition once a validation error occurs
	 * @param car The car xml data to be diagnosed
	 */
	public void executeDiagnostics(Car car) {

		System.out.println("Beginning diagnostics: " + car);

		validateCarData(car);
		if(!valid) {
			System.out.println("Ending diagnostics."); return;
		}

		validateMissingParts(car);
		if(!valid) {
			System.out.println("Ending diagnostics."); return;
		}

		validateDamagedParts(car);
		if(!valid) {
			System.out.println("Ending diagnostics."); return;
		}

		System.out.println("Diagnostics complete, no defects detected.");

	}

	private void validateCarData(Car car) {
		if(car.getMake() == null || car.getMake().isEmpty()) {
			valid = false;
			printMissingData("Make");
		}
		if(car.getModel() == null || car.getModel().isEmpty()) {
			valid = false;
			printMissingData("Model");
		}
		if(car.getYear() == null || car.getYear().isEmpty()) {
			valid = false;
			printMissingData("Year");
		}
	}

	private void validateMissingParts(Car car) {
		Map<PartType, Integer> missingParts = car.getMissingPartsMap();

		if (!missingParts.isEmpty()) {
			for (PartType part : missingParts.keySet()) {
				printMissingPart(part, missingParts.get(part));
				valid = false;
			}
		}
	}

	private void validateDamagedParts(Car car) {
		for(Part part: car.getParts()) {
			if(!part.isInWorkingCondition()) {
				printDamagedPart(part.getType(), part.getCondition());
				valid = false;
			}
		}
	}

	private void printMissingData(String field) {
		if (field == null) throw new IllegalArgumentException("Field name must not be null");

		System.out.println(String.format("Missing car data: %s", field));
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
