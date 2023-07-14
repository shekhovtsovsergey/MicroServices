package com.shekhovtsov.core.controller;

import com.shekhovtsov.core.converter.OrderConverter;
import com.shekhovtsov.core.dto.AppError;
import com.shekhovtsov.core.dto.OrderDto;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер заказов", description = "API работы с заказами")
@Api(tags = "v1")
public class OrderController {


    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201"
                    )
            }
    )
    @PostMapping("/api/v1/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username) {
        orderService.createOrder(username);
    }



    @Operation(
            summary = "Запрос на получение списка заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(
                            description = "Заказы не найдены", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/api/v1/orders")
    public PageDto<OrderDto> getUserOrders(
            @Parameter(description = "Имя текущего пользователя", required = true)
            @RequestHeader String username
    ) {
        return orderService.findByUsername(username);
    }
}
