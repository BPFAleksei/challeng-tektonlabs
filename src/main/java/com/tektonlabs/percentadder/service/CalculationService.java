package com.tektonlabs.percentadder.service;

import com.tektonlabs.percentadder.dto.CalculationHistoryDTO;
import com.tektonlabs.percentadder.model.CalculationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CalculationService {
    double sumAndApplyPercentage(CalculationRequest request);
    Page<CalculationHistoryDTO> getCalculationHistory(Pageable pageable);
    void recordCalculation(String endpoint, Map<String, String> parameters, String response, String error);
}
