package com.shekhovtsov.bonus.controller;

import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.exception.ResourceNotFoundException;
import com.shekhovtsov.bonus.service.BonusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bonuses")
@Slf4j
@Tag(name = "Контроллер бонусов", description = "API работы с бонусами")
public class BonusController {

    private final BonusService bonusService;


    @GetMapping()
    @Operation(
            summary = "Запрос на получение списка бонусов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден",
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    public Page<BonusDto> getByClientId(@RequestHeader Long clientId, Pageable pageable) {
        Page<BonusDto> bonuses = bonusService.getBonusesByClientId(clientId, pageable);
        return bonuses;
    }


    @PutMapping("/spend")
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
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    public void spend(@RequestBody BonusDto bonusDto) {
        bonusService.spendBonuses(bonusDto);
    }


    @PostMapping("/add")
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
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    public void add(@RequestBody BonusDto bonusDto) throws Exception {
        bonusService.addBonus(bonusDto);
    }
}