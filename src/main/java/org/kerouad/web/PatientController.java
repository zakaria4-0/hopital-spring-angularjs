package org.kerouad.web;

import lombok.AllArgsConstructor;
import org.kerouad.dto.PatientArchiveDto;
import org.kerouad.entities.Patient;
import org.kerouad.repositories.PatientRepository;
import org.kerouad.service.PatientServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    private final PatientServcie patientServcie;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public PatientArchiveDto patientList(
            @RequestParam(name = "keyWord") String keyWord,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ){

        return patientServcie.getAllPatients(keyWord,page, size);
    }


    @RequestMapping(value = "/patientsByName/{keyWord}", method = RequestMethod.GET)
    public List<Patient> getPatientsByName(@PathVariable("keyWord") String keyWord){
        return patientRepository.findPatientByNomContains(keyWord);
    }

    @RequestMapping(value = "/newPatient", method = RequestMethod.POST)
    public Patient newPatient(@RequestBody Patient patient){
        return patientRepository.save(patient);
    }
}
