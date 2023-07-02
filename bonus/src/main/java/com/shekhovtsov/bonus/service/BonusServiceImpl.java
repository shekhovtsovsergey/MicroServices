package com.shekhovtsov.bonus.service;

import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService{

    private final BonusRepository bonusRepository;

    @Override
    public List<Bonus> getBonusesByClientId(Long clientId) {
        return bonusRepository.findByClientId(clientId);
    }

    @Override
    public Integer getTotalBonusesByClientId(Long clientId) {
        return bonusRepository.getTotalBonusesByClientId(clientId);
    }

    @Override
    public void spendBonuses(Long clientId, BigDecimal amount) {
        List<Bonus> bonuses = bonusRepository.findByClientId(clientId);

        BigDecimal remainingAmount = amount;

        for (Bonus bonus : bonuses) {
            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal bonusAmount = bonus.getAmount();

            if (bonusAmount.compareTo(remainingAmount) >= 0) {
                bonus.setAmount(bonusAmount.subtract(remainingAmount));
                remainingAmount = BigDecimal.ZERO;
            } else {
                bonus.setAmount(BigDecimal.ZERO);
                remainingAmount = remainingAmount.subtract(bonusAmount);
            }

            bonusRepository.save(bonus);
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *") // Каждый день в полночь
    public void deleteExpiredBonuses() {
        LocalDateTime now = LocalDateTime.now();
        bonusRepository.deleteByExpireDateBefore(now);
    }

}
