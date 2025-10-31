package org.kerouad.service;

import org.kerouad.dto.PatientArchiveDto;
import org.kerouad.dto.PatientDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PatientServcie {
    PatientArchiveDto getAllPatients(String keyWord, int page, int size);
    ByteArrayInputStream exportToExcel(List<PatientDto> patientDtos) throws IOException;
}
