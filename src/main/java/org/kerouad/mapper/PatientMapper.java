package org.kerouad.mapper;

import org.kerouad.dto.PatientDto;
import org.kerouad.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "dateNaissance", source = "dateNaissance", dateFormat = "yyyy-MM-dd")
    PatientDto patientsToPatientsDtos(Patient patient);
}
