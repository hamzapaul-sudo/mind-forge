package com.mindforge.textservice.service;

import com.mindforge.textservice.dto.TextGenerationRequest;
import com.mindforge.textservice.dto.TextGenerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextGenerationService {

    private final WebClient.Builder webClientBuilder;

    @Value("${ollama.base-url:http://ollama:11434}")
    private String baseUrl;

    public Mono<TextGenerationResponse> generate(TextGenerationRequest request) {
        log.info("🟢 Received request: {}", request);

        WebClient webClient = webClientBuilder.baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(60))))
                .build();

        return webClient.post()
                .uri("/api/generate")
                .bodyValue(new OllamaPayload(request))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("🔴 Ollama returned error: {}, body: {}", clientResponse.statusCode(), errorBody);
                                    return Mono.error(new RuntimeException("Ollama error: " + errorBody));
                                }))
                .bodyToMono(OllamaResponse.class)
                .doOnNext(response -> log.info("✅ Ollama response received"))
                .map(response -> new TextGenerationResponse(response.response()))
                .onErrorResume(ex -> {
                    String message;
                    if (ex instanceof WebClientResponseException webEx) {
                        message = "Failed to reach Ollama: " + webEx.getStatusCode() + " - " + webEx.getResponseBodyAsString();
                    } else {
                        message = "Unexpected error: " + ex.getMessage();
                    }
                    log.error("🔻 Text generation failed: {}", message);
                    return Mono.just(new TextGenerationResponse(message));
                });
    }

    private record OllamaPayload(String model, String prompt, boolean stream) {
        public OllamaPayload(TextGenerationRequest req) {
            this(req.getModel(), req.getPrompt(), false);
        }
    }

    private record OllamaResponse(String response) {}
}
