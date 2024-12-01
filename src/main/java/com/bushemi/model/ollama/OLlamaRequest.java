package com.bushemi.model.ollama;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OLlamaRequest {
//https://github.com/ollama/ollama/blob/main/docs/api.md
    /**
     * model: (required) the model name
     * prompt: the prompt to generate a response for
     * images: (optional) a list of base64-encoded images (for multimodal models such as llava)
     * Advanced parameters (optional):
     *
     * format: the format to return a response in. Currently, the only accepted value is json
     * options: additional model parameters listed in the documentation for the Modelfile such as temperature
     * system: system message to (overrides what is defined in the Modelfile)
     * template: the prompt template to use (overrides what is defined in the Modelfile)
     * context: the context parameter returned from a previous request to /generate, this can be used to keep a short conversational memory
     * stream: if false the response will be returned as a single response object, rather than a stream of objects
     * raw: if true no formatting will be applied to the prompt. You may choose to use the raw parameter if you are specifying a full templated prompt in your request to the API
     * keep_alive: controls how long the model will stay loaded into memory following the request (default: 5m)
     */

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
