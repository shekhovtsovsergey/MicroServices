package com.shekhovtsov.bonus.controller;

import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.exception.ClientNotFoundException;
import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.repository.BonusRepository;
import com.shekhovtsov.bonus.service.BonusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bonuses")
@Slf4j
@Tag(name = "Контроллер бонусов", description = "API работы с бонусами")
public class BonusController {

    private final BonusService bonusService;
    private final BonusRepository bonusRepository;


    //клиентИд перенести в хедер
    @GetMapping()
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
    //Лист не возвращать
    public List<BonusDto> getByClientId(@RequestHeader Long clientId) {
        return bonusService.getBonusesByClientId(clientId);
    }

/*    //обьединить методы
    @GetMapping("/total")
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
    //интежер заменить на DTO
    public Integer getTotalByClientId(@RequestHeader Long clientId) {
        return bonusService.getTotalBonusesByClientId(clientId);
    }*/



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
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )//BigDecimal на какую категорию тратим заменить JSON
    public void spend(@RequestHeader Long clientId, @RequestParam BigDecimal amount) {
        bonusService.spendBonuses(clientId, amount);
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
                            responseCode = "404",//500
                            content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))
                    )
            }
    )//категории + JSON c с расширяемым списоком полей
    public void add(@RequestHeader Long clientId, @RequestParam BigDecimal bonusAmount) throws Exception {
        bonusService.addBonus(clientId, bonusAmount);
    }



    /*//вынести в глобальный перехватчик
    @ExceptionHandler({ClientNotFoundException.class, Exception.class})
    private ResponseEntity<String> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<ErrorDto>("a", HttpStatus.INTERNAL_SERVER_ERROR);
        //создаем отдельный класс errorDto(время сам заполняет, сообщение, код ошибки)//все не найденное в ресурснотфауннд второй сервис интернал сервер еерор
    }*/


    @GetMapping("/demo")
    public BigDecimal getTotal() {
        return bonusRepository.getTotalBonusesByClientId(1L);
    }

}