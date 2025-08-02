package com.mindforge.textservice.controller;

import com.mindforge.textservice.dto.TextGenerationRequest;
import com.mindforge.textservice.dto.TextGenerationResponse;
import com.mindforge.textservice.service.TextGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/text")
@RequiredArgsConstructor
public class TextGenerationController {

    private final TextGenerationService service;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TextGenerationResponse> generate(@RequestBody TextGenerationRequest request) {
        return service.generate(request);
    }
}