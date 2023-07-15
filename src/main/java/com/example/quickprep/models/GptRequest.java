package com.example.quickprep.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptRequest {
    private String model;
    private List<GptMessage> messages;
    private double temperature;
}
