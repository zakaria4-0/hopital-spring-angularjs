package org.kerouad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class PatientArchiveDto {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<PatientDto> patientDtos;
}
