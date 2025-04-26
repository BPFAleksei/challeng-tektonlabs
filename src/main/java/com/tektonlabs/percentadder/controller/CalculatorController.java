package com.tektonlabs.percentadder.controller;

import com.tektonlabs.percentadder.dto.CalculationHistoryDTO;
import com.tektonlabs.percentadder.model.CalculationRequest;
import com.tektonlabs.percentadder.service.CalculationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
@RestController
@RequestMapping("v1/api/percentage")
public class CalculatorController {
    private final CalculationService calculationService;

    public CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Operation(summary = "Suma dos números y aplica un porcentaje dinámico",
            description = "Este endpoint recibe dos números, los suma y aplica un porcentaje obtenido de un servicio externo (o caché).")
    @PostMapping("/calculate")
        public ResponseEntity<Double> calculate(@RequestBody CalculationRequest request) {
        double result = calculationService.sumAndApplyPercentage(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el historial de llamadas de cálculo",
            description = "Este endpoint permite obtener el historial de las llamadas al endpoint de suma, con soporte para paginación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de historial de cálculos"),
            @ApiResponse(responseCode = "503", description = "Servicio externo y cache no disponible")
    })
    @GetMapping("/history")
    public ResponseEntity<Page<CalculationHistoryDTO>> getCalculationHistory(
            @Parameter(description = "Información de paginación (page number, page size, sort)") Pageable pageable) {
        Page<CalculationHistoryDTO> historyPage = calculationService.getCalculationHistory(pageable);
        return ResponseEntity.ok(historyPage);
    }
}
