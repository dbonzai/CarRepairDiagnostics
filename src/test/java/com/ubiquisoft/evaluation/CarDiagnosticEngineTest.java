package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import org.junit.jupiter.api.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

class CarDiagnosticEngineTest {

    @Test
    @DisplayName("Run Diagnostics - see console output")
    void runDiagnostics() throws JAXBException {
        System.out.println("Running test 1:");
        testDiagnosticOnCar("test/TestCar1.xml");
        System.out.println();
        System.out.println("Running test 2:");
        testDiagnosticOnCar("test/TestCar2.xml");
        System.out.println();
        System.out.println("Running test 3:");
        testDiagnosticOnCar("test/TestCar3.xml");
        System.out.println();
        System.out.println("Running test 4:");
        testDiagnosticOnCar("test/TestCar4.xml");
        System.out.println();
        System.out.println("Running test 5:");
        testDiagnosticOnCar("no_file.xml");
    }

    private void testDiagnosticOnCar( String carXMLFile ) throws JAXBException {
        InputStream xml = ClassLoader.getSystemResourceAsStream(carXMLFile);

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load " + carXMLFile);

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