package com.example.quickprep.service;

import com.example.quickprep.models.GptMessage;
import com.example.quickprep.models.GptRequest;
import com.example.quickprep.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class GptServiceImpl implements GptService {
    private final RestTemplate restTemplate;
    private static final String URI = "https://api.openai.com/v1/chat/completions";
    private List<String> gptResponses;

    @Autowired
    public GptServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.gptResponses = new ArrayList<>();
    }

    @Override
    public String getSummaryFromGpt(String text) {
        GptMessage gptMessage = new GptMessage(Constants.USER_ROLE, Constants.BASE_MESSAGE + text);
        GptRequest gptRequest = new GptRequest(Constants.GPT_MODEL, Collections.singletonList(gptMessage),
                Constants.GPT_TEMPERATURE);
        HttpEntity<GptRequest> httpEntity = new HttpEntity<>(gptRequest, getHeaders());
        String gptResponse = getResponse(HttpMethod.POST, httpEntity, 1);
        gptResponses.add(text);
        return gptResponse;
    }

    @Override
    public String getMcqFromGpt() {
        String document = gptResponses.get(0);
        GptMessage gptMessage = new GptMessage(Constants.USER_ROLE, document + "\n " + Constants.MCQ_MESSAGE);
        GptRequest gptRequest = new GptRequest(Constants.GPT_MODEL, Collections.singletonList(gptMessage),
                Constants.GPT_TEMPERATURE);
        HttpEntity<GptRequest> httpEntity = new HttpEntity<>(gptRequest, getHeaders());
        String gptResponse = getResponse(HttpMethod.POST, httpEntity, 2);
        gptResponses.add(gptResponse);
        return gptResponse;
    }

    @Override
    public String generateAnswersAndProvideScore(List<String> answers) {
        List<GptMessage> gptMessages = new ArrayList<>(3);
        StringBuilder sb = new StringBuilder();
        for (String ans : answers) {
            sb.append(ans).append("\n");
        }
        sb.append(Constants.ANALYZE_ANSWER_MESSAGE);
        GptMessage gptMessage1 = new GptMessage(Constants.USER_ROLE, gptResponses.get(0));
        GptMessage gptMessage2 = new GptMessage(Constants.ASSISTANT_ROLE, gptResponses.get(1));
        GptMessage gptMessage3 = new GptMessage(Constants.USER_ROLE, sb.toString());
        gptMessages.add(gptMessage1);
        gptMessages.add(gptMessage2);
        gptMessages.add(gptMessage3);
        GptRequest gptRequest = new GptRequest(Constants.GPT_MODEL, gptMessages, Constants.GPT_TEMPERATURE);
        HttpEntity<GptRequest> httpEntity = new HttpEntity<>(gptRequest, getHeaders());
        String response = getResponse(HttpMethod.POST, httpEntity, 3);
        return response;
    }

    @Override
    public void clear() {
        this.gptResponses.clear();
    }

    public String getResponse(HttpMethod httpMethod, HttpEntity<GptRequest> httpEntity, int cachedEntity) {
        if (gptResponses.size() >= cachedEntity) {
            return gptResponses.get(cachedEntity-1);
        }
        ResponseEntity<Map> gptResponseEntity = restTemplate.exchange(URI, httpMethod, httpEntity, Map.class);
        Map<String, Object> gptResponse = gptResponseEntity.getBody();
        Map<String, Object> choices = (Map<String, Object>) (((ArrayList) gptResponse.getOrDefault("choices", new ArrayList<>())).get(0));
        Map<String, Object> message = (Map<String, Object>) choices.get("message");
        if (Objects.isNull(message)) {
            return "Error while fetching from GPT.";
        }
        String content = (String) message.get("content");
        if (Objects.isNull(content)) {
            return "Error while fetching from GPT.";
        }
        return content;
    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + Constants.API_KEY);
        return headers;
    }
}
