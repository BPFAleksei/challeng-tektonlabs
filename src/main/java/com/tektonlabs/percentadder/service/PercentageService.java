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

public interface PercentageService {
    Double getPercentage();
}
