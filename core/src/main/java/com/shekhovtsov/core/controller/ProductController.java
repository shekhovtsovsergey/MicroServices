package com.shekhovtsov.core.controller;

import com.shekhovtsov.core.converter.ProductConverter;
import com.shekhovtsov.core.dto.AppError;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.exception.GlobalExceptionHandler;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер продуктов", description = "API работы с продуктами")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private static final int PAGE_SIZE = 5;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )
    @GetMapping("/api/v1/products")
    public PageDto<ProductDto> findProducts(
            @RequestParam(required = false, name = "min_price") Integer minPrice,
            @RequestParam(required = false, name = "max_price") Integer maxPrice,
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(defaultValue = "1", name = "p") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        //   Specification<Product> spec = productService.createSpecByFilters(minPrice, maxPrice, title);
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(1);
        out.setItems(productDtos);
        out.setTotalPages(1);
        return out;
//        return null;
    }
    //сделать отдельный метод сеарч в который передается JSON с параметрами и ищется нужный продукт по параметрам JSON (POST) findProducts

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/api/v1/products/{id}")
    public ProductDto findProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        return productService.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно удален", responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/api/v1/products/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }


    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping("/api/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestParam("title") String title,
                                 @RequestParam("price") BigDecimal price,
                                 @RequestParam("category") String category,
                                 @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        productService.createNewProduct(title,price,category,image);
    }

}
