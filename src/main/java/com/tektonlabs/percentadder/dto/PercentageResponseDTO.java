package com.tektonlabs.percentadder.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
public record PercentageResponseDTO(
        @JsonProperty("percentage") Double percentage
) {
}