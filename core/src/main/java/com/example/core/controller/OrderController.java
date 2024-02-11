package com.example.core.controller;

import com.example.core.converter.OrderConverter;
import com.example.core.dto.AppError;
import com.example.core.dto.OrderDto;
import com.example.core.service.OrderService;
import com.example.core.service.OrderServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер заказов", description = "API работы с заказами")
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
    public List<OrderDto> getUserOrders(
            @Parameter(description = "Имя текущего пользователя", required = false)
            @RequestHeader String username,
            HttpServletRequest request
    ) {
        // Log the incoming request with headers
        System.out.println("Incoming Request: " + request.getMethod() + " " + request.getRequestURI());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        return orderService.findByUsername(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
