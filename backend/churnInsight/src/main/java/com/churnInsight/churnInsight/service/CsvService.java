package com.churnInsight.churnInsight.service;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CsvService {

    public List<PredictRequest> parse(MultipartFile file) {
        try (Reader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            CsvToBean<PredictRequest> csvToBean = new CsvToBeanBuilder<PredictRequest>(reader)
                    .withType(PredictRequest.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception e) {
            throw new RuntimeException("Error procesando CSV", e);
        }
    }
}