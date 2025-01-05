package com.bushemi.service.impl;

import com.bushemi.model.ollama.OLlamaRequest;
import com.bushemi.model.ollama.OLlamaResponse;
import com.bushemi.service.LLMService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class OLlamaLLMService implements LLMService {

    private static final String URL = "http://192.168.88.222:11434/api/generate";
    private static final String ONLY_UKRAINIAN = ". Відповідай українською";

    private final Gson gson;

    @Override
    public String getTextFromLlm(String message) {
        String stringResponse = getStringResponse(message.concat(ONLY_UKRAINIAN), "llama3", null);
        if (isNull(stringResponse)) {
            return null;
        }
        OLlamaResponse oLlamaResponse = null;
        try {
            oLlamaResponse = gson.fromJson(stringResponse, OLlamaResponse.class);
        }
        catch (Exception e) {
            log.error("error during parsing", e);
            return null;
        }
        log.info("The result is parsed");
        return oLlamaResponse.getResponse();
    }

    private String getStringResponse(String message,
                                     String model,
                                     List<String> images) {
        try {
            return sendHttpRequest(URL, message, model, images);
        }
        catch (Exception e) {
            log.error("failed to parse http response", e);
            return null;
        }
    }

    private String sendHttpRequest(String url,
                                   String message,
                                   String model,
                                   List<String> images) {
        log.info("Send message [{}] to [{}]", message, url);

        OLlamaRequest oLlamaRequest = OLlamaRequest.builder()
                                                   .model(model)
                                                   .prompt(message)
                                                   .images(images)
                                                   .build();
        // building http client
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .build();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                               .setDefaultRequestConfig(requestConfig)
                                                               .build()) {

            HttpPost request = new HttpPost(url);

            String json = gson.toJson(oLlamaRequest);
            // adding the form data
            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity);
            System.out.println("string = " + string);
            log.info("Got the result");
            return string;
        }
        catch (Exception e) {
            log.error("error from llm", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTextFromLlmByImage(String message,
                                        String imageBase64) {
        if (isNull(message)) {
            message = "Describe what you see on this image";
        }
        String stringResponse = getStringResponse(message, "llava", Collections.singletonList(imageBase64));
        if (isNull(stringResponse)) {
            return null;
        }
        OLlamaResponse oLlamaResponse = null;
        try {
            oLlamaResponse = gson.fromJson(stringResponse, OLlamaResponse.class);
        }
        catch (Exception e) {
            log.error("error during parsing", e);
            return null;
        }
        log.info("The result is parsed");
        return oLlamaResponse.getResponse();
    }

}
