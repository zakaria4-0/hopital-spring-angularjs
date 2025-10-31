package org.kerouad.web;

import lombok.AllArgsConstructor;
import org.kerouad.dto.PatientArchiveDto;
import org.kerouad.dto.PatientDto;
import org.kerouad.entities.Patient;
import org.kerouad.repositories.PatientRepository;
import org.kerouad.service.PatientServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> exportExcel(@RequestBody List<PatientDto> patientDtos) throws IOException {

        ByteArrayInputStream in = patientServcie.exportToExcel(patientDtos);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=patients.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));

    }

    @RequestMapping(value = "/exportPDF", method = RequestMethod.POST)
    public ResponseEntity<byte[]> exportPdf(@RequestBody List<PatientDto> persons) throws Exception {

        byte[] pdfBytes = patientServcie.exportToPdf(persons);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=patients.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
