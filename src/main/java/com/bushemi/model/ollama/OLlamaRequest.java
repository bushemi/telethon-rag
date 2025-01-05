package com.bushemi.model.ollama;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OLlamaRequest {

    @Builder.Default
    private String model = "llama3";
//    @Builder.Default
//    private String format = "json";
    @Builder.Default
    private boolean stream = false;

    private String prompt;
    private List<String> images;
    private String options;
    private String system;
    private List<Long> context;
    private Boolean raw;
    private String keep_alive;

}
