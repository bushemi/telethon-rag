package com.bushemi.service.impl;

import com.bushemi.model.telethon.TelegramChannel;
import com.bushemi.model.telethon.TelegramMessages;
import com.bushemi.model.telethon.TelegramTotalChats;
import com.bushemi.service.TelethonApiService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.core.ParameterizedTypeReference;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class TelethonApiServiceImpl implements TelethonApiService {

    private final static String DATE_PATTERN = ""; //"2024-12-19 18:48:29+00:00";
    private static final Gson GSON = new Gson();
    private static final String TELETHON_HOST = "192.168.88.222";
    private static final int TELETHON_PORT = 5000;
    private static final String TELETHON_SCHEME = "http";

    public static void main(String[] args) {
        TelethonApiService telethonApiService = new TelethonApiServiceImpl();
//        List<TelegramChannel> channels = telethonApiService.getChannels(10);
//        System.out.println("channels = " + channels);

        TelegramMessages messages = telethonApiService.getMessages(-1002101121969L, 10, 0);
        System.out.println("messages = " + messages);
    }


    @Override
    public List<TelegramChannel> getChannels(int limit) {
        URIBuilder uriBuilder = new URIBuilder();
//        uriBuilder.setHost("http://192.168.88.222:5000/get_chats");
        uriBuilder.setHost(TELETHON_HOST);
        uriBuilder.setPort(TELETHON_PORT);
        uriBuilder.setScheme(TELETHON_SCHEME);
        uriBuilder.setPath("get_chats");
        uriBuilder.addParameter("limit", String.valueOf(limit));
        // building http client
        RequestConfig requestConfig = RequestConfig.custom()
//                                                   .setConnectionRequestTimeout(600_000)
                                                   .build();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                               .setDefaultRequestConfig(requestConfig)
                                                               .build()) {

            System.out.println("uriBuilder.build() = " + uriBuilder.build());
//            HttpGet request = new HttpGet(url);
            HttpGet request = new HttpGet(uriBuilder.build());
//            HttpPost request = new HttpPost(uriBuilder.build());

//            String json = GSON.toJson(oLlamaRequest);
            // adding the form data
//            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            System.out.println("string = " + string);
            String decoded = StringEscapeUtils.unescapeJava(string);

//            String s = new String(string.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8);
//            System.out.println("s = " + s);
            System.out.println("Decoded: " + decoded);
            ParameterizedTypeReference<List<TelegramChannel>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
            List<TelegramChannel> list = GSON.fromJson(string, parameterizedTypeReference.getType());
            list.stream()
//                .map(Map::entrySet)
//                .forEach(x -> x.forEach(entry -> System.out.println(" " + entry)));
//                    .map(TelegramChannel::getWhole_dialog)
                .forEach(System.out::println);
            log.info("Got the result {}", decoded);
            return list;
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public TelegramMessages getMessages(Long chatId, Integer limit, int from) {
//        http://192.168.88.222:5000/chat/-1002101121969/getMessages?limit=5&from=10000
        URIBuilder uriBuilder = new URIBuilder();
//        uriBuilder.setHost("http://192.168.88.222:5000/get_chats");
        uriBuilder.setHost(TELETHON_HOST);
        uriBuilder.setPort(TELETHON_PORT);
        uriBuilder.setScheme(TELETHON_SCHEME);
//        uriBuilder.setPath("get_chats");
        uriBuilder.addParameter("limit", String.valueOf(limit));
        uriBuilder.addParameter("from", String.valueOf(from));
        uriBuilder.setPathSegments("chat", String.valueOf(chatId), "getMessages");
        // building http client
        RequestConfig requestConfig = RequestConfig.custom()
//                                                   .setConnectionRequestTimeout(600_000)
                                                   .build();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                               .setDefaultRequestConfig(requestConfig)
                                                               .build()) {

            System.out.println("uriBuilder.build() = " + uriBuilder.build());
//            HttpGet request = new HttpGet(url);
            HttpGet request = new HttpGet(uriBuilder.build());
//            HttpPost request = new HttpPost(uriBuilder.build());

//            String json = GSON.toJson(oLlamaRequest);
            // adding the form data
//            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            System.out.println("string = " + string);
            String decoded = StringEscapeUtils.unescapeJava(string);

//            String s = new String(string.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8);
//            System.out.println("s = " + s);
            System.out.println("Decoded: " + decoded);

            TelegramMessages telegramMessages = GSON.fromJson(string, TelegramMessages.class);
            log.info("Got the result {}", decoded);
            return telegramMessages;
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer totalChats() {
        URIBuilder uriBuilder = new URIBuilder();
        http:
//192.168.88.222:5000/chats/total
        uriBuilder.setHost(TELETHON_HOST);
        uriBuilder.setPort(TELETHON_PORT);
        uriBuilder.setScheme(TELETHON_SCHEME);
        uriBuilder.setPathSegments("chats", "total");
        // building http client
        RequestConfig requestConfig = RequestConfig.custom()
//                                                   .setConnectionRequestTimeout(600_000)
                                                   .build();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                               .setDefaultRequestConfig(requestConfig)
                                                               .build()) {

            System.out.println("uriBuilder.build() = " + uriBuilder.build());
//            HttpGet request = new HttpGet(url);
            HttpGet request = new HttpGet(uriBuilder.build());
//            HttpPost request = new HttpPost(uriBuilder.build());

//            String json = GSON.toJson(oLlamaRequest);
            // adding the form data
//            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            System.out.println("string = " + string);
            String decoded = StringEscapeUtils.unescapeJava(string);

//            String s = new String(string.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8);
//            System.out.println("s = " + s);
            System.out.println("Decoded: " + decoded);

            TelegramTotalChats telegramTotalChats = GSON.fromJson(string, TelegramTotalChats.class);

            log.info("Got the result {}", decoded);
            return telegramTotalChats.getTotal_chats();
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

}
