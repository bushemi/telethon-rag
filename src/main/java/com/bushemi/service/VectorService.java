package com.bushemi.service;

import java.util.Map;

public interface VectorService {

    String find(String text);

    void add(String text, Map<String, Object> metadata);

}
