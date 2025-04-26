package com.tektonlabs.percentadder.service.impl;

import com.tektonlabs.percentadder.dto.CalculationHistoryDTO;
import com.tektonlabs.percentadder.model.CalculationHistory;
import com.tektonlabs.percentadder.model.CalculationRequest;
import com.tektonlabs.percentadder.repository.CalculationHistoryRepository;
import com.tektonlabs.percentadder.service.CalculationService;
import com.tektonlabs.percentadder.service.PercentageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class CalculationServiceImpl implements CalculationService {
    private static final double BASE_PERCENTAGE = 100.0;

    private final PercentageService percentageService;
    private final CalculationHistoryRepository calculationHistoryRepository;

    public CalculationServiceImpl(PercentageService percentageService, CalculationHistoryRepository calculationHistoryRepository) {
        this.percentageService = percentageService;
        this.calculationHistoryRepository = calculationHistoryRepository;
    }

    public double sumAndApplyPercentage(CalculationRequest request) {
        double sum = request.getNumber1() + request.getNumber2();
        double percentage = percentageService.getPercentage();
        double result = sum * (1 + (percentage / BASE_PERCENTAGE));
        recordCalculation(String.format("/api/calculate/sum?number1=%s&number2=%s", request.getNumber1(), request.getNumber2()), Map.of("number1", String.valueOf(request.getNumber1()), "number2", String.valueOf(request.getNumber2())), String.valueOf(result), null);
        return result;
    }

    public Page<CalculationHistoryDTO> getCalculationHistory(Pageable pageable) {
        return calculationHistoryRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    private CalculationHistoryDTO convertToDTO(CalculationHistory history) {
        return new CalculationHistoryDTO(
                history.getTimestamp(),
                history.getEndpoint(),
                history.getParameters(),
                history.getResponse(),
                history.getError()
        );
    }
    @Async
    private void recordCalculation(String endpoint, Map<String, String> parameters, String response, String error) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        calculationHistoryRepository.save(new CalculationHistory(LocalDateTime.now(), endpoint, parameters, response, error));
    }
}
