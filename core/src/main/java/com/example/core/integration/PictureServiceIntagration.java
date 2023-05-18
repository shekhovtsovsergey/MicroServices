package com.example.core.integration;

import com.example.core.dto.PictureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
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


   /* public String createPicture(MultipartFile image) {
        return pictureServiceWebClient.post()
                .uri("/api/v1/picture/")
                //.header("username", username)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }*/

    public String createPicture(MultipartFile image) {
        // build the request body as multipart form data
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        if (image != null) {
            builder.part("image", image.getResource());
        }
        return pictureServiceWebClient.post()
                .uri("/api/v1/picture/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


}
