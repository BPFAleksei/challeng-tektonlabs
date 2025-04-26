package com.tektonlabs.percentadder.service;

import com.tektonlabs.percentadder.dto.PercentageResponseDTO;
import com.tektonlabs.percentadder.exception.ExternalServiceUnavailableException;
import com.tektonlabs.percentadder.exception.PercentageRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@CacheConfig(cacheNames = "percentage")
public class PercentageService {

    @Value("${external.percentage.url}")
    private String percentageServiceUrl;

    private final RestTemplate restTemplate;
    private Double lastRetrievedPercentage = null;

    public PercentageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable
    public Double getPercentage() {
        try {
            PercentageResponseDTO response = restTemplate.getForObject(percentageServiceUrl, PercentageResponseDTO.class);
            if (response.percentage() != null) {
                lastRetrievedPercentage = response.percentage();
                return lastRetrievedPercentage;
            } else {
                log.info("Failed to retrieve percentage from external service: Response was null or percentage missing.");
                if (lastRetrievedPercentage != null) {
                    return lastRetrievedPercentage;
                } else {
                    throw new PercentageRetrievalException("Could not retrieve percentage from external service.");
                }
            }
        } catch (RestClientException e) {
            log.error("Error calling external percentage service: {}", e.getMessage());
            if (lastRetrievedPercentage != null) {
                return lastRetrievedPercentage;
            } else {
                throw new ExternalServiceUnavailableException("External percentage service is unavailable.");
            }
        }
    }

    @Scheduled(fixedRate = 30 * 60 * 1000) // Evict cache every 30 minutes
    @CacheEvict
    public void clearPercentageCache() {
        log.info("Clearing percentage cache.");
    }
}
