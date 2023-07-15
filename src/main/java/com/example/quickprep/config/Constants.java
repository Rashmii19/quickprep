package com.example.quickprep.config;

public class Constants {
    public static String API_KEY = "OPENAI_API_KEY";
    public static String BASE_MESSAGE = "You are my assistant and summarize this text for me.\n";
    public static String MCQ_MESSAGE = "Generate 5 MCQ questions based on this. Response with only MCQ.";
    public static String ANALYZE_ANSWER_MESSAGE = "These are the answers. Analyze the results and provide me the score.  Also provide the areas that i need to improve based on my answers.";
    public static String USER_ROLE = "user";
    public static String ASSISTANT_ROLE = "assistant";
    public static double GPT_TEMPERATURE = 0.7;
    public static String GPT_MODEL = "gpt-3.5-turbo";
}
