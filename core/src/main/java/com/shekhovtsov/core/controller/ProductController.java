package com.shekhovtsov.core.controller;

import com.shekhovtsov.core.dto.AppError;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.dto.SearchDto;
import com.shekhovtsov.core.exception.ResourceNotFoundException;
import com.shekhovtsov.core.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер продуктов", description = "API работы с продуктами")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Запрос на получение отфильтрованного списка продуктов", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = PageDto.class)))})
    @PostMapping("/api/v1/products/search")
    public PageDto<ProductDto> search(@RequestBody SearchDto searchDto) {
        return productService.search(searchDto);
    }


    @Operation(summary = "Запрос на получение продукта по id", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(description = "Продукт не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = AppError.class)))})
    @GetMapping("/api/v1/products/{id}")
    public Optional<ProductDto> findProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) throws ResourceNotFoundException {
        return productService.findById(id);
    }


    @Operation(summary = "Запрос на удаление продукта по id", responses = {
            @ApiResponse(description = "Продукт успешно удален", responseCode = "204"),
            @ApiResponse(description = "Продукт не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = AppError.class)))})
    @DeleteMapping("/api/v1/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }



    @Operation(summary = "Запрос на создание нового продукта", responses = {
            @ApiResponse(description = "Продукт успешно создан", responseCode = "201")})
    @PostMapping("/api/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody ProductDto productDto, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException, ResourceNotFoundException {
        productService.createNewProduct(productDto, image);
    }

}
