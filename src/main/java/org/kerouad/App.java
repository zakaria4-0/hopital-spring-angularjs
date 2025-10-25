package org.kerouad;

import org.kerouad.entities.Patient;
import org.kerouad.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    PatientRepository patientRepository;
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Patient patient = new Patient(null, "ahmed", new Date(), false, 20);
        Patient patient1 = new Patient(null, "halima", new Date(), true, 10);
        Patient patient2 = new Patient(null, "hanane", new Date(), false, 17);

        //patientRepository.saveAll(List.of(patient,patient1, patient2));

    }
}
