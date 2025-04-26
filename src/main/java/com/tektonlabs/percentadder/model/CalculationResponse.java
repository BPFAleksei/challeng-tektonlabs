package com.tektonlabs.percentadder.model;

public class CalculationResponse {
    private Double result;
    private Double appliedPercentage;

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getAppliedPercentage() {
        return appliedPercentage;
    }

    public void setAppliedPercentage(Double appliedPercentage) {
        this.appliedPercentage = appliedPercentage;
    }
}
