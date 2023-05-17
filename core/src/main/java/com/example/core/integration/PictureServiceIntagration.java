package com.example.core.integration;

import com.example.core.dto.PictureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.context.ContextView;

@Component
@RequiredArgsConstructor
public class PictureServiceIntagration {

    private final WebClient pictureServiceWebClient;

    public PictureDto getPicture(String id) {
        return pictureServiceWebClient.get()
                .uri("/api/v1/picture/"+id)
                //.header("username", username)
                .retrieve()
                .bodyToMono(PictureDto.class)
                .block();
    }
}
