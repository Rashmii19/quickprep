package com.example.quickprep.service;

import com.example.quickprep.exceptions.DocumentNotFoundException;
import com.example.quickprep.readers.Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final GptService gptService;

    @Autowired
    public ApplicationServiceImpl(GptService gptService) {
        this.gptService = gptService;
    }

    @Override
    public String getSummary(String path) throws DocumentNotFoundException {
        String document = Reader.readTxt(path);
        if (Objects.isNull(document)) {
            throw new DocumentNotFoundException("Cannot read document");
        }
        String summary = gptService.getSummaryFromGpt(document);
        return summary;
    }

    @Override
    public String getQuestions() {
        return gptService.getMcqFromGpt();
    }

    @Override
    public String getAnswersAndSuggestions(String answers) {
        List<String> answerList = new ArrayList<>();
        String[] split = answers.split(",");
        for (String s : split) {
            answerList.add("(" + s + ")");
        }
        return gptService.generateAnswersAndProvideScore(answerList);
    }

    @Override
    public void clear() {
        gptService.clear();
    }
}
