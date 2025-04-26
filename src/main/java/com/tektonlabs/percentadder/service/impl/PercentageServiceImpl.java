package com.tektonlabs.percentadder.service.impl;

import com.tektonlabs.percentadder.dto.PercentageResponseDTO;
import com.tektonlabs.percentadder.exception.ExternalServiceUnavailableException;
import com.tektonlabs.percentadder.exception.PercentageRetrievalException;
import com.tektonlabs.percentadder.service.PercentageService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Setter
public class PercentageServiceImpl implements PercentageService {
    private static final int MINUTES_IN_HOUR = 60;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MILLISECONDS_IN_SECOND = 1000;

    private static final long THIRTY_MINUTES_IN_MILLISECONDS = 30 * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND;


    @Value("${external.percentage.url}")
    private String percentageServiceUrl;

    @Autowired
    private final RestTemplate restTemplate;

    private Double lastRetrievedPercentage = null;

    public PercentageServiceImpl(RestTemplate restTemplate) {
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

    @Scheduled(fixedRate = THIRTY_MINUTES_IN_MILLISECONDS)
    @CacheEvict
    private void clearPercentageCache() {
        log.info("Clearing percentage cache.");
    }

}
