package com.bushemi.service;

public interface LLMService {

    String getTextFromLlm(String message);

    String getTextFromLlmByImage(String message, String imageBase64);

}
