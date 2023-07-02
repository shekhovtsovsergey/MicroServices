package com.shekhovtsov.bonus.service;

import com.shekhovtsov.bonus.model.Bonus;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.List;

public interface BonusService {
    List<Bonus> getBonusesByClientId(Long clientId);

    Integer getTotalBonusesByClientId(Long clientId);

    void spendBonuses(Long clientId, BigDecimal amount);

    @Scheduled(cron = "0 0 0 * * *") // Каждый день в полночь
    void deleteExpiredBonuses();
}
