package com.bushemi.service.impl;

import com.bushemi.model.telethon.TelegramChannelDto;
import com.bushemi.model.telethon.TelegramMessagesDto;
import com.bushemi.model.telethon.TelegramTotalChats;
import com.bushemi.service.TelethonApiService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
public class TelethonApiServiceImpl implements TelethonApiService {

    private final Gson gson;
    private final String telethonHttpScheme;
    private final String telethonHost;
    private final Integer telethonPort;

    @Override
    public List<TelegramChannelDto> getChannels(int limit,
                                                String afterDate) {

        try (CloseableHttpClient httpClient =
                     HttpClientBuilder.create()
                                      .setDefaultRequestConfig(RequestConfig.custom()
                                                                            .build())
                                      .build()) {

            URIBuilder uriBuilder = getTelethonUriBuilder();

            uriBuilder.setPath("get_chats");
            uriBuilder.addParameter("limit", String.valueOf(limit));
            if (nonNull(afterDate)) {
                uriBuilder.addParameter("offset_date", afterDate);
            }

            log.info("uriBuilder.build() = " + uriBuilder.build());
            HttpGet request = new HttpGet(uriBuilder.build());

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);

            ParameterizedTypeReference<List<TelegramChannelDto>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};

            return gson.fromJson(string, parameterizedTypeReference.getType());
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

    private URIBuilder getTelethonUriBuilder() {
        return new URIBuilder().setScheme(telethonHttpScheme)
                               .setHost(telethonHost)
                               .setPort(telethonPort);
    }

    @Override
    public TelegramMessagesDto getMessages(Long chatId,
                                           Integer limit,
                                           String afterDate) {

        try (CloseableHttpClient httpClient =
                     HttpClientBuilder.create()
                                      .setDefaultRequestConfig(RequestConfig.custom()
                                                                            .build())
                                      .build()) {

            URIBuilder uriBuilder = getTelethonUriBuilder();

            uriBuilder.addParameter("limit", String.valueOf(limit));
            if (nonNull(afterDate)) {
                uriBuilder.addParameter("offset_date", afterDate);
            }
            uriBuilder.setPathSegments("chat", String.valueOf(chatId), "getMessages");

            log.info("uriBuilder.build() = " + uriBuilder.build());
            HttpGet request = new HttpGet(uriBuilder.build());

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);

            return gson.fromJson(string, TelegramMessagesDto.class);
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer totalChats() {
        try (CloseableHttpClient httpClient =
                     HttpClientBuilder.create()
                                      .setDefaultRequestConfig(RequestConfig.custom()
                                                                            .build())
                                      .build()) {

            URIBuilder uriBuilder = getTelethonUriBuilder();

            uriBuilder.setPathSegments("chats", "total");

            log.info("uriBuilder.build() = " + uriBuilder.build());
            HttpGet request = new HttpGet(uriBuilder.build());

            HttpResponse httpResponse = httpClient.execute(request);
            log.info("Status line is {}", httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();

            String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);

            TelegramTotalChats telegramTotalChats = gson.fromJson(string, TelegramTotalChats.class);

            log.info("Got the result {}", telegramTotalChats);
            return telegramTotalChats.getTotal_chats();
        }
        catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

}
