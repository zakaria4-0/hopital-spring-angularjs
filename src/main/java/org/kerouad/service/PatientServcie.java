package org.kerouad.service;

import org.kerouad.dto.PatientArchiveDto;

public interface PatientServcie {
    PatientArchiveDto getAllPatients(int page, int size);
}
