package com.shekhovtsov.bonus.controller;

import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.exception.ClientNotFoundException;
import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.service.BonusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bonus")
@Slf4j
@Tag(name = "Контроллер бонусов", description = "API работы с бонусами")
public class BonusController {

    private final BonusService bonusService;

    @GetMapping("/{clientId}")
    @Operation(
            summary = "Запрос на получение списка бонусов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BonusDto.class)))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )
    public List<BonusDto> getBonusesByClientId(@PathVariable Long clientId) {
        return bonusService.getBonusesByClientId(clientId);
    }

    @GetMapping("/{clientId}/total")
    @Operation(
            summary = "Запрос на получение общего количества бонусов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Integer.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )
    public Integer getTotalBonusesByClientId(@PathVariable Long clientId) {
        return bonusService.getTotalBonusesByClientId(clientId);
    }

    @PostMapping("/{clientId}/spend")
    @Operation(
            summary = "Запрос на списание бонусов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )
    public void spendBonuses(@PathVariable Long clientId, @RequestParam BigDecimal amount) {
        bonusService.spendBonuses(clientId, amount);
    }

    @PostMapping ("/addBonus")
    @Operation(
            summary = "Запрос на добавление бонусов пользователю",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )
    public void addBonus(@RequestParam Long clientId, @RequestParam BigDecimal bonusAmount) throws Exception {
        bonusService.addBonus(clientId, bonusAmount);
    }


    @ExceptionHandler({ClientNotFoundException.class,Exception.class})
    private ResponseEntity<String> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}