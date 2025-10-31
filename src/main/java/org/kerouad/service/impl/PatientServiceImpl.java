package org.kerouad.service.impl;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kerouad.dto.PatientArchiveDto;
import org.kerouad.dto.PatientDto;
import org.kerouad.entities.Patient;
import org.kerouad.mapper.PatientMapper;
import org.kerouad.repositories.PatientRepository;
import org.kerouad.service.PatientServcie;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @AllArgsConstructor
public class PatientServiceImpl implements PatientServcie {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    @Override
    public PatientArchiveDto getAllPatients(String keyWord,int page, int size) {
        List<Patient> allPatients = patientRepository.findPatientByNomContains(keyWord);
        Page<Patient> patients = patientRepository.findPatientByNomContains(keyWord,PageRequest.of(page, size));
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

    @Override
    public ByteArrayInputStream exportToExcel(List<PatientDto> patientDtos) throws IOException {
        String[] columns = {"Id", "Nom", "DateNaissance", "Malade", "Score"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet();
        try {

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i<columns.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (PatientDto p : patientDtos){
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getNom());
                row.createCell(2).setCellValue(p.getDateNaissance());
                row.createCell(3).setCellValue(p.getMalade());
                row.createCell(4).setCellValue(p.getScore());
            }

            workbook.write(out);
        }catch (Exception e){
            throw new IOException();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public byte[] exportToPdf(List<PatientDto> patientDtos) throws Exception {
        var reportResource = new ClassPathResource("reports/patientsList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());

        // Data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patientDtos);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Hospital System");

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}
