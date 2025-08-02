package com.mindforge.textservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextGenerationRequest {
    private String prompt;
    private String model;
    private int maxTokens;
    private double temperature;
}