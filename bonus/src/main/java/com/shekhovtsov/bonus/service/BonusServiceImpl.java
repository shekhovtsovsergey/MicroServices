package com.shekhovtsov.bonus.service;

import com.shekhovtsov.bonus.converter.BonusConverter;
import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;
    private final BonusConverter bonusConverter;
    private final int BONUS_EXP = 1;


    @Override
    public List<BonusDto> getBonusesByClientId(Long clientId) {
        return bonusRepository.findByClientId(clientId).stream().map(bonusConverter::entityToDto).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalBonusesByClientId(Long clientId) {
        return bonusRepository.getTotalBonusesByClientId(clientId);
    }

    @Override
    @Transactional
    public void spendBonuses(Long clientId, BigDecimal amount) {
        List<Bonus> bonuses = bonusRepository.findByClientIdOrderByExpirationDate(clientId);
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


    @Transactional
    public void spendBonuses2(Long clientId, BigDecimal spendAmount) {
        List<Bonus> bonuses = bonusRepository.findByClientIdOrderByExpirationDate(clientId);
        for (Bonus bonus : bonuses) {
            if (bonus.getAmount().compareTo(spendAmount) >= 0) {
                bonus.spend(spendAmount);
                bonusRepository.save(bonus);
                return;
            }
            spendAmount = spendAmount.subtract(bonus.getAmount());
            bonusRepository.delete(bonus);
        }
    }



    @Override
    @Scheduled(cron = "${scheduler.deleteExpiredBonuses}")//это не здесь потому что потом не найдешь отдельный конфиг класс
    public void deleteExpiredBonuses() throws Exception {
        LocalDate now = LocalDate.now();
        try {
            bonusRepository.deleteByExpireDateBefore(now);//не переменная величина
        } catch (Exception e) {
            throw new Exception("Error deleting expired bonuses", e);
        }
    }

    @Override
    public void addBonus(Long clientId, BigDecimal bonusAmount) throws Exception {
        LocalDate expireDate = LocalDate.from(LocalDateTime.now().plusMonths(BONUS_EXP));
        Bonus bonus = new Bonus(null, clientId, bonusAmount, expireDate);
        try {
            bonusRepository.save(bonus);
        } catch (Exception e) {
            throw new Exception("Error adding bonus", e);//перехватывать обычный и кидать свой BUSSINESSCRITICALLEXAPTION //нет смысла использовать чекед исключение
        }
    }
}
//подумать над восстановлением бонусов если покупка отменилась как реализовать