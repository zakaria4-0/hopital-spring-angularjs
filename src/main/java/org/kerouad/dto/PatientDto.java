package org.kerouad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class PatientDto {
    private Long id;
    private String nom;
    private String dateNaissance;
    private Boolean malade;
    private int score;
}
