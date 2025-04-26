package com.tektonlabs.percentadder.service;

import com.tektonlabs.percentadder.dto.CalculationHistoryDTO;
import com.tektonlabs.percentadder.model.CalculationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalculationService {
    double sumAndApplyPercentage(CalculationRequest request);
    Page<CalculationHistoryDTO> getCalculationHistory(Pageable pageable);
}
