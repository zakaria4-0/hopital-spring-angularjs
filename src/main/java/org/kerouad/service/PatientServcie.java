package org.kerouad.service;

import org.kerouad.dto.PatientArchiveDto;

public interface PatientServcie {
    PatientArchiveDto getAllPatients(String keyWord, int page, int size);
}
