package com.example.quickprep.service;

import com.example.quickprep.exceptions.DocumentNotFoundException;

public interface ApplicationService {
    String getSummary(String path) throws DocumentNotFoundException;

    String getQuestions();

    String getAnswersAndSuggestions(String answers);

    void clear();
}
