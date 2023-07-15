package com.example.quickprep.service;

import java.util.List;

public interface GptService {
    String getSummaryFromGpt(String text);

    String getMcqFromGpt();

    String generateAnswersAndProvideScore(List<String> answers);

    void clear();
}
