package com.shekhovtsov.bonus.controller;

import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bonus")
public class BonusController {

    private final BonusService bonusService;

    @GetMapping("/{clientId}")
    public List<Bonus> getBonusesByClientId(@PathVariable Long clientId) {
        return bonusService.getBonusesByClientId(clientId);
    }

    @GetMapping("/{clientId}/total")
    public Integer getTotalBonusesByClientId(@PathVariable Long clientId) {
        return bonusService.getTotalBonusesByClientId(clientId);
    }

    @PostMapping("/{clientId}/spend")
    public void spendBonuses(@PathVariable Long clientId, @RequestParam BigDecimal amount) {
        bonusService.spendBonuses(clientId, amount);
    }

}
