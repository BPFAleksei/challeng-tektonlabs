package com.tektonlabs.percentadder.service;

import com.tektonlabs.percentadder.dto.PercentageResponseDTO;
import com.tektonlabs.percentadder.exception.PercentageRetrievalException;
import com.tektonlabs.percentadder.service.impl.PercentageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPercentageServiceImpl {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @Test
    void getPercentageSuccessful() {
        String percentageServiceUrl = "http://example.com/percentage";
        percentageService.setPercentageServiceUrl(percentageServiceUrl);
        PercentageResponseDTO responseDTO = new PercentageResponseDTO(10.0);
        when(restTemplate.getForObject(percentageServiceUrl, PercentageResponseDTO.class))
                .thenReturn(responseDTO);

        Double percentage = percentageService.getPercentage();

        assertEquals(10.0, percentage);
        verify(restTemplate, times(1)).getForObject(percentageServiceUrl, PercentageResponseDTO.class);
    }

    @Test
    void getPercentageNullPercentage() {
        String percentageServiceUrl = "http://example.com/percentage";
        percentageService.setPercentageServiceUrl(percentageServiceUrl);
        PercentageResponseDTO responseDTO = new PercentageResponseDTO(null);
        percentageService.setLastRetrievedPercentage(10.0);
        when(restTemplate.getForObject(percentageServiceUrl, PercentageResponseDTO.class))
                .thenReturn(responseDTO);

        Double percentage = percentageService.getPercentage();

        assertEquals(10.0, percentage);
        verify(restTemplate, times(1)).getForObject(percentageServiceUrl, PercentageResponseDTO.class);
    }

    @Test
    void getPercentageThrowsException() {
        String percentageServiceUrl = "http://example.com/percentage";
        percentageService.setPercentageServiceUrl(percentageServiceUrl);
        PercentageResponseDTO responseDTO = new PercentageResponseDTO(null);
        when(restTemplate.getForObject(percentageServiceUrl, PercentageResponseDTO.class))
                .thenReturn(responseDTO);

        assertThrows(PercentageRetrievalException.class, () -> percentageService.getPercentage());
        verify(restTemplate, times(1)).getForObject(percentageServiceUrl, PercentageResponseDTO.class);
    }
}
