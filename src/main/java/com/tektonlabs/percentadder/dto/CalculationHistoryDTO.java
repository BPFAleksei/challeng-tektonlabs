package com.tektonlabs.percentadder.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record CalculationHistoryDTO (
        LocalDateTime timestamp,
        String endpoint,
        Map<String, String> parameters,
        String response,
        String error
){
}
