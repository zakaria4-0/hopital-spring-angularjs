package org.kerouad.service.impl;

import lombok.AllArgsConstructor;
import org.kerouad.dto.PatientArchiveDto;
import org.kerouad.dto.PatientDto;
import org.kerouad.entities.Patient;
import org.kerouad.mapper.PatientMapper;
import org.kerouad.repositories.PatientRepository;
import org.kerouad.service.PatientServcie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class PatientServiceImpl implements PatientServcie {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    @Override
    public PatientArchiveDto getAllPatients(int page, int size) {
        List<Patient> allPatients = patientRepository.findAll();
        Page<Patient> patients = patientRepository.findAll(PageRequest.of(page, size));
        List<PatientDto> patientDtos = patients.getContent().stream().map(patientMapper::patientsToPatientsDtos).toList();
        PatientArchiveDto patientArchiveDto = new PatientArchiveDto();
        patientArchiveDto.setPatientDtos(patientDtos);
        patientArchiveDto.setCurrentPage(page);
        patientArchiveDto.setPageSize(size);
        if (allPatients.size() % size != 0) {
            patientArchiveDto.setTotalPages(allPatients.size() / size + 1);
        } else {
            patientArchiveDto.setTotalPages(allPatients.size() / size);
        }
        return patientArchiveDto;
    }
}
